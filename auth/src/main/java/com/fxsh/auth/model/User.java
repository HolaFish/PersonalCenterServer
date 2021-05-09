package com.fxsh.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("t_m_user")
public class User extends BaseModel{

    private String loginName;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String salt;
    private Integer deptId;
    private Integer companyId;
    /**
     * 职位 -- 字典项
     */
    private Integer position;
    private Integer sort;
}
