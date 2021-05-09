package com.fxsh.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_m_company")
public class Company extends BaseModel{
    private String name;
    private Integer sort;
}
