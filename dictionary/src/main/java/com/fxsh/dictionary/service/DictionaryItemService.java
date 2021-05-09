package com.fxsh.dictionary.service;

import com.fxsh.dictionary.model.DictionaryItem;
import java.util.List;

public interface DictionaryItemService extends BaseService<DictionaryItem>{

    List<DictionaryItem> getRootItems();

    List<DictionaryItem> getItemsBySupId(Integer id);

    boolean addItems(DictionaryItem item);

    boolean updateItems(DictionaryItem item);

    boolean deleteItemById(Integer id);

    boolean isCodeExist(String code);
    /**
     * @Author LiuRunzhi
     * @Description 根据字典项编码获取其子项目
     * @Date 2021/4/25 13:23
     * @Param code 字典项编码
     * @Return
     **/
    List<DictionaryItem> getItemsBySupCode(String code);
}
