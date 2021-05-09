package com.fxsh.task.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxsh.task.model.Task;
import com.fxsh.task.vo.TaskVO;

import java.util.List;

public interface TaskMapper extends BaseMapper<Task> {
    TaskVO selectTaskVO(Integer id);
    /**
     * @Author LiuRunzhi
     * @Description 根据执行者ID获取任务简要信息列表
     * @Date 2021/4/28 11:04
     * @Param executorId 执行者ID
     * @Return
     **/
    List<TaskVO> findSummaryTaskListByExecutorId(Integer executorId,String startDate, String endDate);
    /**
     * @Author LiuRunzhi
     * @Description 根据客户窗口ID获取任务简要信息列表
     * @Date 2021/4/30 10:37
     * @Param customerId 客户窗口ID
     * @Return
     **/
    List<TaskVO> findSummaryTaskListByCustomerId(Integer customerId,String startDate, String endDate);
}
