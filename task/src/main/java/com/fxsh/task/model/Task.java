package com.fxsh.task.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.List;

@Data
@TableName("t_b_task")
public class Task extends BaseModel{
    private String name;
    private String detail;
    /**
     * 办公地点
     */
    private Integer position;
    private Integer taskType;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    /**
     * 是否为整天事件
     */
    @TableField("is_allday")
    private boolean allDay;
    private String remark;
    /**
     * 执行者
     */
    @TableField(exist = false)
    private List<Integer> executors;
    /**
     * 客户窗口
     */
    @TableField(exist = false)
    private List<Integer> customers;
}
