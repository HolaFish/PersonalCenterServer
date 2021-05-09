package com.fxsh.dictionary.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxsh.dictionary.model.DictionaryItem;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface DictionaryItemMapper extends BaseMapper<DictionaryItem> {
    @Select("select a.* from t_m_dictionary a join t_m_dictionary b on a.sup_code_id = b.id where b.code = #{id} and a.is_deleted = 0 order by a.sort")
    List<DictionaryItem> getItemsBySupCode(String code);
}
