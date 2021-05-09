package com.fxsh.task.service.impl;

import com.fxsh.common.exceptions.BusinessException;
import com.fxsh.task.mappers.TaskMapper;
import com.fxsh.task.model.Task;
import com.fxsh.task.model.TaskCustomer;
import com.fxsh.task.model.TaskExecutor;
import com.fxsh.task.service.TaskCustomerService;
import com.fxsh.task.vo.TaskVO;
import com.fxsh.task.service.TaskExecutorService;
import com.fxsh.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskExecutorService taskExecutorService;
    @Autowired
    private TaskCustomerService taskCustomerService;
    @Override
    public Optional<Task> findById(Integer id) {
        return Optional.ofNullable(taskMapper.selectById(id));
    }

    @Override
    public List<Task> getAll() {
        return taskMapper.selectList(null);
    }

    @Override
    public void insert(Task task) {
        /**
         * 保存Task信息
         */
        this.initModel(task);
        taskMapper.insert(task);
        /**
         * 保存与任务执行者的关联关系
         */
        task.getExecutors().stream().forEach(executor -> {
            TaskExecutor taskExecutor = new TaskExecutor(task.getId(),executor);
            taskExecutorService.insert(taskExecutor);
        });
        /**
         * 保存与客户窗口的关联关系
         */
        if (null != task.getCustomers()){
            task.getCustomers().stream().forEach(customer ->{
                TaskCustomer taskCustomer = new TaskCustomer(task.getId(),customer);
                taskCustomerService.insert(taskCustomer);
            });
        }
    }

    @Override
    public boolean updateById(Task task) {
        this.preUpdateModel(task);
        taskMapper.updateById(task);
        /**
         * 更新任务执行者的关联关系
         */
        taskExecutorService.deleteByTaskId(task.getId());
        task.getExecutors().stream().forEach(executor -> {
            TaskExecutor taskExecutor = new TaskExecutor(task.getId(),executor);
            taskExecutorService.insert(taskExecutor);
        });
        /**
         * 更新与客户窗口的关联关系
         */
        taskCustomerService.deleteByTaskId(task.getId());
        if (null != task.getCustomers()){
            task.getCustomers().stream().forEach(customer ->{
                TaskCustomer taskCustomer = new TaskCustomer(task.getId(),customer);
                taskCustomerService.insert(taskCustomer);
            });
        }
        return true;
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<Task> taskOptional = this.findById(id);
        if (!taskOptional.isPresent()){
            throw new BusinessException();
        }
        Task task = taskOptional.get();
        this.preUpdateModel(task);
        taskMapper.updateById(task);
        return taskMapper.deleteById(id) > 0;
    }

    @Override
    public TaskVO getTaskVOById(Integer id) {
        return taskMapper.selectTaskVO(id);
    }

    /**
     * @param executorId
     * @Author LiuRunzhi
     * @Description 根据执行者ID获取任务简要信息列表
     * @Date 2021/4/28 11:01
     * @Param executorId 执行者ID
     * @Return
     */
    @Override
    public List<TaskVO> getTaskListByExecutorId(Integer executorId,String startDate, String endDate) {
        return taskMapper.findSummaryTaskListByExecutorId(executorId,startDate,endDate);
    }

    /**
     * @param customerId
     * @param startDate
     * @param endDate
     * @Author LiuRunzhi
     * @Description 根据客户窗口Id获取任务简要信息
     * @Date 2021/4/30 10:36
     * @Param customerId 客户窗口ID
     * @Return
     */
    @Override
    public List<TaskVO> getTaskListByCustomerId(Integer customerId, String startDate, String endDate) {
        return taskMapper.findSummaryTaskListByCustomerId(customerId,startDate,endDate);
    }
}
