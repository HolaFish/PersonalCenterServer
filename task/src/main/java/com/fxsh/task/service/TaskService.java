package com.fxsh.task.service;

import com.fxsh.task.model.Task;
import com.fxsh.task.vo.TaskVO;

import java.util.List;

public interface TaskService extends BaseService<Task>{
    TaskVO getTaskVOById(Integer id);
    /**
     * @Author LiuRunzhi
     * @Description 根据执行者ID获取任务简要信息列表
     * @Date 2021/4/28 11:01
     * @Param executorId 执行者ID
     * @Return
     **/
    List<TaskVO> getTaskListByExecutorId(Integer executorId, String startDate, String endDate);
    /**
     * @Author LiuRunzhi
     * @Description 根据客户窗口Id获取任务简要信息
     * @Date 2021/4/30 10:36
     * @Param customerId 客户窗口ID
     * @Return
     **/
    List<TaskVO> getTaskListByCustomerId(Integer customerId, String startDate, String endDate);
}
