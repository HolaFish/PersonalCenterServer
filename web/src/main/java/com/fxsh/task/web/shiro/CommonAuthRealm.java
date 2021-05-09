package com.fxsh.task.web.shiro;


import cn.hutool.core.bean.BeanUtil;
import com.fxsh.auth.model.User;
import com.fxsh.auth.service.DepartmentService;
import com.fxsh.auth.service.UserService;
import com.fxsh.auth.vo.UserVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import java.util.Optional;

@ComponentScan
public class CommonAuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        if (token.getUsername() == null) {
            return null;
        }
        String loginName = token.getUsername();
        Optional<User> user = userService.findByLoginName(loginName);
        if (!user.isPresent()){
            return null;
        }

        UserVO userInfo = new UserVO();
        BeanUtil.copyProperties(user.get(),userInfo);
        /**
         * 四个参数
         * principal：认证的实体信息，可以是username，也可以是数据库表对应的用户的实体对象
         * credentials：数据库中的密码（经过加密的密码）
         * credentialsSalt：盐值（使用用户名）
         * realmName：当前realm对象的name，调用父类的getName()方法即可
         */
        ByteSource slat = ByteSource.Util.bytes(user.get().getSalt());
        return new SimpleAuthenticationInfo(userInfo, user.get().getPassword(), slat, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        return info;
    }
}

