package com.fxsh.task.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fxsh.file.server.FileOperationService;
import com.fxsh.auth.model.Company;
import com.fxsh.auth.model.Department;
import com.fxsh.auth.model.User;
import com.fxsh.auth.service.CompanyService;
import com.fxsh.auth.service.DepartmentService;
import com.fxsh.auth.service.UserService;
import com.fxsh.auth.vo.UserVO;
import com.fxsh.common.constant.ResponseCode;
import com.fxsh.common.wrapper.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileOperationService fileOperationService;
    /**
     * @Author LiuRunzhi
     * @Description 获取所有的公司
     * @Date 2021/4/22 17:39
     * @Param []
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("companies")
    public JsonWrapper getAllCompany(){
        List<Company> companies = companyService.getAll();
        return new JsonWrapper(ResponseCode.OK,null,companies);
    }
    /**
     * @Author LiuRunzhi
     * @Description 新增一个公司
     * @Date 2021/4/22 17:39
     * @Param [company]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("company/add")
    public JsonWrapper addCompany(Company company){
        companyService.insert(company);
        return new JsonWrapper(ResponseCode.OK,null,company);
    }
    /**
     * @Author LiuRunzhi
     * @Description 更新Company
     * @Date 2021/4/23 10:10
     * @Param [company]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("company/update")
    public JsonWrapper updateCompany(Company company){
        JsonWrapper result = new JsonWrapper();
        if (companyService.updateById(company)){
            result.setCode(ResponseCode.OK);
        }else {
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 删除Company
     * @Date 2021/4/23 10:24
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("company/{id}/delete")
    public JsonWrapper deleteCompany(@PathVariable Integer id){
        JsonWrapper result = new JsonWrapper();
        if (companyService.deleteById(id)){
            result.setCode(ResponseCode.OK);
        }else {
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }

    /**
     * @Author LiuRunzhi
     * @Description 获取公司的顶级部门
     * @Date 2021/4/23 11:42
     * @Param [companyId]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("department/of/{companyId}")
    public JsonWrapper getAllRootDeptOfCompany(@PathVariable Integer companyId){
        List<Department> depts = departmentService.getAllRootDeptOfCompany(companyId);
        return new JsonWrapper(ResponseCode.OK,null,depts);
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取公司的所在部门
     * @Date 2021/4/26 17:24
     * @Param [companyId]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("allDepartment/of/{companyId}")
    public JsonWrapper getAllDeptOfCompanry(@PathVariable Integer companyId){
        List<Department> depts = departmentService.getAllDeptOfCompany(companyId);
        return new JsonWrapper(ResponseCode.OK,null,depts);
    }
    /**
     * @Author LiuRunzhi
     * @Description 新增部门
     * @Date 2021/4/23 11:43
     * @Param [department]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("department/add")
    public JsonWrapper addDepartment(Department department){
        departmentService.insert(department);
        return new JsonWrapper(ResponseCode.OK,null,department);
    }
    /**
     * @Author LiuRunzhi
     * @Description 更新部门
     * @Date 2021/4/23 11:47
     * @Param [department]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("department/update")
    public JsonWrapper updateDepartment(Department department){
        JsonWrapper result = new JsonWrapper();
        if (departmentService.updateById(department)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 删除部门
     * @Date 2021/4/23 11:52
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("department/{id}/delete")
    public JsonWrapper deleteDepartment(@PathVariable Integer id){
        JsonWrapper result = new JsonWrapper();
        if (departmentService.deleteById(id)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取部门的子部门
     * @Date 2021/4/23 16:06
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("department/{id}/subDepts")
    public JsonWrapper getAllSubDept(@PathVariable Integer id){
        List<Department> depts = departmentService.getAllDeptOfDept(id);
        return new JsonWrapper(ResponseCode.OK,null,depts);
    }

    /**
     * @Author LiuRunzhi
     * @Description 新增用户
     * @Date 2021/4/23 17:40
     * @Param [user]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("user/add")
    public JsonWrapper addUser(User user){
        userService.insert(user);
        return new JsonWrapper(ResponseCode.OK,null,user);
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取部门下的人员信息
     * @Date 2021/4/23 17:56
     * @Param [deptId]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("user/of/{deptId}")
    public JsonWrapper getUserOfDept(@PathVariable Integer deptId){
        return new JsonWrapper(ResponseCode.OK,null,userService.getUserByDeptId(deptId));
    }
    /**
     * @Author LiuRunzhi
     * @Description 更新用户信息
     * @Date 2021/4/23 18:08
     * @Param [user]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("user/update")
    public JsonWrapper updateUser(User user){
        JsonWrapper result = new JsonWrapper();
        if (userService.updateById(user)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        result.setData(user);
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 删除用户
     * @Date 2021/4/23 18:11
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("user/{id}/delete")
    public JsonWrapper deleteUser(@PathVariable Integer id){
        JsonWrapper result = new JsonWrapper();
        if (userService.deleteById(id)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 验证用户的密码
     * @Date 2021/4/23 18:21
     * @Param [id, password]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("user/checkPassword")
    public JsonWrapper checkUserPassword(Integer id, String password){
        JsonWrapper result = new JsonWrapper();
        result.setData(userService.checkPassword(id,password));
        result.setCode(ResponseCode.OK);
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取本公司的所有人员信息
     * @Date 2021/4/29 17:32
     * @Param []
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("user/all")
    public JsonWrapper getAllUser(){
        JsonWrapper result = new JsonWrapper();
        List<UserVO> users = userService.getAll().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtil.copyProperties(user,userVO);
            return userVO;
        }).collect(Collectors.toList());
        result.setData(users);
        result.setCode(ResponseCode.OK);
        return result;
    }
    /**
     * @Author 风萧水寒
     * @Description 上传头像
     * @Date 2021/5/3 11:20 上午
     * @Param [request]
     * @return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("/upload/avatar")
    public JsonWrapper uploadAvatar(@RequestParam("avatar") MultipartFile file, Integer userId) throws IOException {
        JsonWrapper result = new JsonWrapper();
        String url = fileOperationService.uploadAvatar(file);
        if (userService.changeAvatar(userId,url)){
            result.setCode(ResponseCode.OK);
            result.setData(url);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
}
