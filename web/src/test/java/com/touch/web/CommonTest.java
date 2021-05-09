package com.touch.web;

import cn.hutool.core.util.RandomUtil;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class CommonTest {
    @Test
    public void testEncryption(){
        String password = "qwedsa123";
        String salt = BCrypt.gensalt(RandomUtil.randomInt(30));
        System.out.println(salt);
        String pwd = BCrypt.hashpw(password,salt);
        System.out.println(pwd);
    }
}
