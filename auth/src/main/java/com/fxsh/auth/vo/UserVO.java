package com.fxsh.auth.vo;

import lombok.Data;
import java.util.List;

@Data
public class UserVO {
    private Integer id;
    private String loginName;
    private String userName;
    private String email;
    private String phoneNumber;
    private List<String> roles;
    private List<String> permissions;
    private Integer deptId;
    private Integer companyId;
    private String avatar;
    private Integer position;
    private Integer sort;
}
