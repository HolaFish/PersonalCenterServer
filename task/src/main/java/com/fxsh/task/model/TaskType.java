package com.fxsh.task.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_b_task_type")
@Data
public class TaskType extends BaseModel{

    private String name;
    /**
     * Task在日历中的颜色
     */
    private String color;
}
