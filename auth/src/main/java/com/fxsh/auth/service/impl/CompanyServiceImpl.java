package com.fxsh.auth.service.impl;

import com.fxsh.auth.mappers.CompanyMapper;
import com.fxsh.auth.service.CompanyService;
import com.fxsh.auth.vo.UserVO;
import com.fxsh.auth.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    @Override
    public Optional<Company> findById(Integer id) {
        Optional<Company> result = Optional.ofNullable(companyMapper.selectById(id));
        return result;
    }

    @Override
    public List<Company> getAll() {
        return companyMapper.selectList(null);
    }

    @Override
    public void insert(Company company) {
        this.initModel(company);
        companyMapper.insert(company);
    }

    @Override
    public boolean updateById(Company company) {
        UserVO currentUser = this.getCurrentUser();
        company.setUpdatedBy(currentUser.getId());
        company.setUpdatedAt(LocalDateTime.now());
        return companyMapper.updateById(company) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        /**
         * 先记录删除的操作者和时间
         */
        Company company = new Company();
        company.setId(id);
        UserVO currentUser = this.getCurrentUser();
        company.setUpdatedBy(currentUser.getId());
        company.setUpdatedAt(LocalDateTime.now());
        companyMapper.updateById(company);

        return companyMapper.deleteById(id) > 0;
    }
}
