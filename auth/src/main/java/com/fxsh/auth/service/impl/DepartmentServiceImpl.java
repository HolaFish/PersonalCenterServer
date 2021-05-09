package com.fxsh.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fxsh.auth.mappers.DepartmentMapper;
import com.fxsh.auth.service.DepartmentService;
import com.fxsh.auth.vo.UserVO;
import com.fxsh.auth.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public Optional<Department> findById(Integer id) {
        return Optional.ofNullable(departmentMapper.selectById(id));
    }

    @Override
    public List<Department> getAll() {
        return departmentMapper.selectList(null);
    }

    @Override
    public void insert(Department department) {
        this.initModel(department);
        departmentMapper.insert(department);
    }

    @Override
    public boolean updateById(Department department) {
        UserVO currentUser = this.getCurrentUser();
        department.setUpdatedAt(LocalDateTime.now());
        department.setUpdatedBy(currentUser.getId());
        return departmentMapper.updateById(department) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<Department> department = this.findById(id);
        if (!department.isPresent()){
            return false;
        }
        Department dept = department.get();
        UserVO currentUser = this.getCurrentUser();
        dept.setUpdatedAt(LocalDateTime.now());
        dept.setUpdatedBy(currentUser.getId());
        this.updateById(dept);
        return departmentMapper.deleteById(id) > 0;
    }

    /**
     * @param companyId 公司ID
     * @Author LiuRunzhi
     * @Description 获取公司的顶级部门
     * @Date 2021/4/23 11:35
     * @Return List<Department>
     */
    @Override
    public List<Department> getAllRootDeptOfCompany(Integer companyId) {
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Department::getCompanyId,companyId).isNull(Department::getSupDeptId);
        return departmentMapper.selectList(queryWrapper);
    }

    /**
     * @param companyId
     * @Author LiuRunzhi
     * @Description 获取公司的所有部门
     * @Date 2021/4/26 17:21
     * @Param companyId 公司ID
     * @Return
     */
    @Override
    public List<Department> getAllDeptOfCompany(Integer companyId) {
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Department::getCompanyId,companyId);
        return departmentMapper.selectList(queryWrapper);
    }

    /**
     * @Author LiuRunzhi
     * @Description 获取部门下的子部门
     * @Date 2021/4/23 15:58
     * @Param deptId 部门ID
     * @Return List<Department>
     */
    @Override
    public List<Department> getAllDeptOfDept(Integer deptId) {
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Department::getSupDeptId,deptId);
        return departmentMapper.selectList(queryWrapper);
    }
}
