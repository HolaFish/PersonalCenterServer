package com.fxsh.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_m_department")
public class Department extends BaseModel{

    private String name;
    private Integer sort;
    /**
     * 所属公司ID
     */
    private Integer companyId;
    /**
     * 上级公司ID
     */
    private Integer supDeptId;
}
