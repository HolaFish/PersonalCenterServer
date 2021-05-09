package com.fxsh.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fxsh.auth.mappers.UserMapper;
import com.fxsh.auth.model.User;
import com.fxsh.auth.vo.UserVO;
import com.fxsh.auth.service.UserService;
import com.fxsh.common.exceptions.BusinessException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @Author LiuRunzhi
     * @Description 通过ID查找用户信息
     * @Date 2021/4/20 16:08
     * @Param [id]
     * @Return com.touch.auth.model.User
     **/
    @Override
    public Optional<User> findById(Integer id) {
        Optional<User> result = Optional.ofNullable(userMapper.selectById(id));
        return result;
    }

    @Override
    public List<User> getAll() {
        UserVO currentUser = this.getCurrentUser();
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getCompanyId, currentUser.getCompanyId());
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public void insert(User user) {
        this.initModel(user);
        /**
         * 初始化密码
         */
        String salt = BCrypt.gensalt();
        user.setPassword(BCrypt.hashpw("zhige666",salt));
        user.setSalt(salt);
        userMapper.insert(user);
    }

    @Override
    public boolean updateById(User user) {
        UserVO currentUser = this.getCurrentUser();
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(currentUser.getId());
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<User> userOptional = this.findById(id);
        if (!userOptional.isPresent()){
            return false;
        }
        UserVO currentUser = this.getCurrentUser();
        User user = userOptional.get();
        user.setUpdatedBy(currentUser.getId());
        user.setUpdatedAt(LocalDateTime.now());
        this.updateById(user);
        return userMapper.deleteById(id) > 0;
    }

    /**
     * @Author LiuRunzhi
     * @Description 通过登录名查找用户信息
     * @Date 2021/4/20 16:08
     * @Param [loginName]
     * @Return com.touch.auth.model.User
     **/
    @Override
    public Optional<User> findByLoginName(String loginName) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getLoginName,loginName);
        Optional<User> result = Optional.ofNullable(userMapper.selectOne(queryWrapper));
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取部门下的员工信息
     * @Date 2021/4/23 17:54
     * @Param [deptId]
     * @Return java.util.List<com.touch.auth.vo.UserVO>
     **/
    @Override
    public List<UserVO> getUserByDeptId(Integer deptId) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getDeptId,deptId);
        List<User> users = userMapper.selectList(queryWrapper);
        List<UserVO> userVOs = users.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtil.copyProperties(user,userVO);
            return userVO;
        }).collect(Collectors.toList());
        return userVOs;
    }
    /**
     * @Author LiuRunzhi
     * @Description 验证密码是否正确
     * @Date 2021/4/23 18:17
     * @Param [userId, password]
     * @Return boolean
     **/
    @Override
    public boolean checkPassword(Integer userId, String password) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(User::getPassword).eq(User::getId,userId);
        User user = userMapper.selectOne(queryWrapper);
        if (null == user){
            throw new BusinessException("User id is invalid");
        }
        return BCrypt.checkpw(password,user.getPassword());
    }

    /**
     * @Author 风萧水寒
     * @Description 修改密码
     * @Date 2021/5/2 5:05 下午
     * @Param [userId, newPassword]
     * @return boolean
     **/
    @Override
    public boolean changePassword(Integer userId, String newPassword){
        UserVO currentUser = this.getCurrentUser();
        String salt = BCrypt.gensalt();
        String password = BCrypt.hashpw(newPassword,salt);
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(User::getSalt,salt)
                .set(User::getPassword,password)
                .set(User::getUpdatedAt,LocalDateTime.now())
                .set(User::getUpdatedBy,currentUser.getId())
                .eq(User::getId,userId);
        return userMapper.update(null,updateWrapper) > 0;
    }

    /**
     * @Author 风萧水寒
     * @Description 更新头像
     * @Date 2021/5/3 1:47 下午
     * @Param 
     * @return 
     **/
    @Override
    public boolean changeAvatar(Integer userId, String avatarUrl) {
        UserVO currentUser = this.getCurrentUser();
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(User::getAvatar,avatarUrl)
                .set(User::getUpdatedAt,LocalDateTime.now())
                .set(User::getUpdatedBy,currentUser.getId())
                .eq(User::getId,userId);
        return userMapper.update(null,updateWrapper) > 0;
    }
}
