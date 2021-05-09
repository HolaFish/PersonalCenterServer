package com.fxsh.task.service;

import com.fxsh.task.model.TaskCustomer;

public interface TaskCustomerService extends BaseService<TaskCustomer>{
    /**
     * @Author LiuRunzhi
     * @Description 根据TaskID删除记录
     * @Date 2021/4/27 15:01
     * @Param taskId
     * @Return
     **/
    boolean deleteByTaskId(Integer taskId);
}
