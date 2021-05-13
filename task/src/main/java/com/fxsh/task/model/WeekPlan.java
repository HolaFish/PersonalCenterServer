package com.fxsh.task.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_b_plan_week")
public class WeekPlan extends BaseModel{
    private String summary;
    private String detail;
    private Integer monthId;
    private Integer week;
    private String remark;
}
