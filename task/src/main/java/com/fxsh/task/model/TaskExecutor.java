package com.fxsh.task.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_b_task_executors")
public class TaskExecutor extends BaseModel{
    private Integer taskId;
    private Integer userId;
}
