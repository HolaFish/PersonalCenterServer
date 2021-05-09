package com.fxsh.task.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        //要验证的明文密码
        String plaintext = new String(userToken.getPassword());

        //数据库中的加密后的密文
        String hashed = authenticationInfo.getCredentials().toString();
        return BCrypt.checkpw(plaintext, hashed);
    }
}
