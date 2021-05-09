package com.fxsh.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fxsh.common.exceptions.BusinessException;
import com.fxsh.task.mappers.TaskCustomerMapper;
import com.fxsh.task.model.TaskCustomer;
import com.fxsh.task.service.TaskCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class TaskCustomerServiceImpl implements TaskCustomerService {
    @Autowired
    private TaskCustomerMapper taskCustomerMapper;
    @Override
    public Optional<TaskCustomer> findById(Integer id) {
        return Optional.ofNullable(taskCustomerMapper.selectById(id));
    }

    @Override
    public List<TaskCustomer> getAll() {
        return taskCustomerMapper.selectList(null);
    }

    @Override
    public void insert(TaskCustomer taskCustomer) {
        this.initModel(taskCustomer);
        taskCustomerMapper.insert(taskCustomer);
    }

    @Override
    public boolean updateById(TaskCustomer taskCustomer) {
        this.preUpdateModel(taskCustomer);
        return taskCustomerMapper.updateById(taskCustomer) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<TaskCustomer> taskCustomerOptional = this.findById(id);
        if (!taskCustomerOptional.isPresent()){
            throw new BusinessException();
        }
        TaskCustomer taskCustomer = taskCustomerOptional.get();
        this.preUpdateModel(taskCustomer);
        this.updateById(taskCustomer);
        return taskCustomerMapper.deleteById(id) > 0;
    }

    /**
     * @param taskId
     * @Author LiuRunzhi
     * @Description 根据TaskID删除记录
     * @Date 2021/4/27 15:01
     * @Param taskId
     * @Return
     */
    @Override
    public boolean deleteByTaskId(Integer taskId) {
        LambdaUpdateWrapper<TaskCustomer> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(TaskCustomer::getUpdatedAt, LocalDateTime.now())
                .set(TaskCustomer::getUpdatedBy, this.getCurrentUser().getId())
                .eq(TaskCustomer::getTaskId,taskId);
        taskCustomerMapper.update(null,updateWrapper);
        LambdaQueryWrapper<TaskCustomer> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TaskCustomer::getTaskId,taskId);
        return taskCustomerMapper.delete(queryWrapper) > 0;
    }
}
