package com.fxsh.task.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_b_task_customers")
public class TaskCustomer extends BaseModel{
    private  Integer taskId;
    private Integer customerId;
}
