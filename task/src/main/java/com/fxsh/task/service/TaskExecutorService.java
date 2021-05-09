package com.fxsh.task.service;

import com.fxsh.task.model.TaskExecutor;

public interface TaskExecutorService extends BaseService<TaskExecutor>{
    /**
     * @Author LiuRunzhi
     * @Description 感觉TaskID删除记录
     * @Date 2021/4/27 14:56
     * @Param taskId
     * @Return
     **/
    boolean deleteByTaskId(Integer taskId);
}
