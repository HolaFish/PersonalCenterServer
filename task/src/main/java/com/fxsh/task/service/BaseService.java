package com.fxsh.task.service;

import com.fxsh.auth.vo.UserVO;
import com.fxsh.task.model.BaseModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseModel> {

    Optional<T> findById(Integer id);

    List<T> getAll();

    void insert(T t);

    boolean updateById(T t);

    boolean deleteById(Integer id);

    /**
     * @Author LiuRunzhi
     * @Description 获取当前的登录用户
     * @Date 2021/4/23 11:23
     * @Param
     * @Return
     **/
    default UserVO getCurrentUser(){
        if (!SecurityUtils.getSubject().isAuthenticated()){
            throw new AuthenticationException();
        }
        return (UserVO) SecurityUtils.getSubject().getPrincipal();
    }
    /**
     * @Author LiuRunzhi
     * @Description 初始化Model的基础字段
     * @Date 2021/4/23 11:23
     * @Param [t]
     * @Return void
     **/
    default void initModel(T t){
        UserVO currentUser = this.getCurrentUser();
        t.setCreatedBy(currentUser.getId());
        t.setCreatedAt(LocalDateTime.now());
        t.setUpdatedBy(currentUser.getId());
        t.setUpdatedAt(LocalDateTime.now());
    }
    /**
     * @Author LiuRunzhi
     * @Description 更新数据之前的操作——记录数据的更新时间和修改人
     * @Date 2021/4/26 11:54
     * @Param
     * @Return
     **/
    default void preUpdateModel(T t){
        UserVO currentUser = this.getCurrentUser();
        t.setUpdatedBy(currentUser.getId());
        t.setUpdatedAt(LocalDateTime.now());
    }
}
