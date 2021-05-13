package com.fxsh.task.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_b_plan_year")
public class YearPlan extends BaseModel{
    private String summary;
    private String detail;
    private Integer year;
    private String remark;
}
