package com.fxsh.dictionary.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_m_dictionary")
public class DictionaryItem extends BaseModel{
    private String code;
    private String name;
    private Integer supCodeId;
    private Integer sort;
    private Integer itemLevel;
}
