package com.fxsh.auth.service;

import com.fxsh.auth.model.Department;

import java.util.List;

public interface DepartmentService extends BaseService<Department> {
    /**
     * @Author LiuRunzhi
     * @Description 获取公司的顶级部门
     * @Date 2021/4/23 11:35
     * @Param companyId 公司ID
     * @Return List<Department>
     **/
    List<Department> getAllRootDeptOfCompany(Integer companyId);
    /**
     * @Author LiuRunzhi
     * @Description 获取公司的所有部门
     * @Date 2021/4/26 17:21
     * @Param companyId 公司ID
     * @Return
     **/
    List<Department> getAllDeptOfCompany(Integer companyId);
    /**
     * @Author LiuRunzhi
     * @Description 获取部门下的子部门
     * @Date 2021/4/23 15:58
     * @Param deptId
     * @Return List<Department>
     **/
    List<Department> getAllDeptOfDept(Integer deptId);
}
