package com.fxsh.task.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_b_plan_week")
public class MonthPlan extends BaseModel{
    private String summary;
    private String detail;
    private Integer yearId;
    private Integer month;
    private String remark;
}
