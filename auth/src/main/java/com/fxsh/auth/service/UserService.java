package com.fxsh.auth.service;

import com.fxsh.auth.model.User;
import com.fxsh.auth.vo.UserVO;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User>{

    Optional<User> findById(Integer id);

    Optional<User> findByLoginName(String loginName);

    List<UserVO> getUserByDeptId(Integer deptId);

    boolean checkPassword(Integer userId, String password);
    /**
     * @Author 风萧水寒
     * @Description 修改密码
     * @Date 2021/5/2 5:06 下午
     * @Param 
     * @return 
     **/
    boolean changePassword(Integer userId, String password);
    /**
     * @Author 风萧水寒
     * @Description 更改头像
     * @Date 2021/5/3 1:45 下午
     * @Param 
     * @return 
     **/
    boolean changeAvatar(Integer userId, String avatarUrl);
}
