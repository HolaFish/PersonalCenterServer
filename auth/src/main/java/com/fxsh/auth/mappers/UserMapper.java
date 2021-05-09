package com.fxsh.auth.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxsh.auth.vo.UserVO;
import com.fxsh.auth.model.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("select id, user_name, company_id, dept_id, avatar from t_m_user where id = #{id} and is_deleted = 0")
    UserVO findUserVOById(Integer id);
}
