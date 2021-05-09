package com.fxsh.task.web.controller;

import com.fxsh.auth.service.UserService;
import com.fxsh.auth.vo.UserVO;
import com.fxsh.common.constant.ResponseCode;
import com.fxsh.common.wrapper.JsonWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public JsonWrapper login(String userName, String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
        JsonWrapper result = new JsonWrapper();
        try{
            subject.login(token);
        }catch (UnknownAccountException e){
            result.setCode(401);
            result.setMsg("Unknown Account");
            return result;
        }catch (AuthenticationException e) {
            result.setCode(401);
            result.setMsg("UserName Or Password Wrong");
            return result;
        }
        result.setCode(200);
        result.setMsg("Login Success");
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 登出
     * @Date 2021/4/26 17:39
     * @Param [response]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("logout")
    public JsonWrapper logOut(HttpServletResponse response){
        SecurityUtils.getSubject().logout();
        JsonWrapper result = new JsonWrapper(401,"UnAuthentication",null);
        response.setStatus(ResponseCode.UNAUTH);
        return result;
    }
    @GetMapping("unLogin")
    public JsonWrapper unLogin(HttpServletResponse response){
        JsonWrapper result = new JsonWrapper(401,"UnAuthentication",null);
        response.setStatus(ResponseCode.UNAUTH);
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取当前登录用户的信息
     * @Date 2021/4/20 11:13
     * @Param []
     * @Return com.touch.pojo.JsonWrapper
     **/
    @GetMapping("currentUser")
    public JsonWrapper currentUser(){
        JsonWrapper result = new JsonWrapper();
        UserVO userInfo = (UserVO)SecurityUtils.getSubject().getPrincipal();
        result.setCode(200);
        result.setData(userInfo);
        return result;
    }
    /**
     * @Author 风萧水寒
     * @Description 更新密码
     * @Date 2021/5/2 5:08 下午
     * @Param [userId, password]
     * @return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("/changePassword")
    public JsonWrapper changePassword(Integer userId, String oldPassword, String newPassword){
        JsonWrapper result = new JsonWrapper();
        if (!userService.checkPassword(userId,oldPassword)){
            result.setCode(ResponseCode.ERROR);
            result.setMsg("Password is wrong");
            return result;
        }
        if (userService.changePassword(userId,newPassword)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
}
