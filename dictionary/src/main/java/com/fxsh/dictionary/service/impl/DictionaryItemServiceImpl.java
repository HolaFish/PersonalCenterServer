package com.fxsh.dictionary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fxsh.dictionary.mappers.DictionaryItemMapper;
import com.fxsh.dictionary.model.DictionaryItem;
import com.fxsh.dictionary.service.DictionaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DictionaryItemServiceImpl implements DictionaryItemService {
    @Autowired
    private DictionaryItemMapper itemMapper;
    /**
     * @Author LiuRunzhi
     * @Description 获取根节点
     * @Date 2021/4/21 14:53
     * @Param []
     * @Return java.util.List<com.touch.dictionary.model.DictionaryItem>
     **/
    @Override
    public List<DictionaryItem> getRootItems() {
        LambdaQueryWrapper<DictionaryItem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.isNull(DictionaryItem::getSupCodeId);
        return itemMapper.selectList(queryWrapper);
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取某个项的子节点
     * @Date 2021/4/21 14:54
     * @Param [id]
     * @Return java.util.List<com.touch.dictionary.model.DictionaryItem>
     **/
    @Override
    public List<DictionaryItem> getItemsBySupId(Integer id) {
        LambdaQueryWrapper<DictionaryItem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictionaryItem::getSupCodeId, id);
        return itemMapper.selectList(queryWrapper);
    }
    /**
     * @Author LiuRunzhi
     * @Description 新增一项
     * @Date 2021/4/21 14:54
     * @Param [item]
     * @Return boolean
     **/
    @Override
    public boolean addItems(DictionaryItem item) {
        this.initModle(item);
        return itemMapper.insert(item) > 0;
    }
    /**
     * @Author LiuRunzhi
     * @Description 更新一项
     * @Date 2021/4/21 14:55
     * @Param [item]
     * @Return com.touch.dictionary.model.DictionaryItem
     **/
    @Override
    public boolean updateItems(DictionaryItem item) {
        item.setUpdatedAt(LocalDateTime.now());
        return itemMapper.updateById(item) > 0;
    }
    /**
     * @Author LiuRunzhi
     * @Description 删除一项
     * @Date 2021/4/21 14:56
     * @Param [id]
     * @Return boolean
     **/
    @Override
    public boolean deleteItemById(Integer id) {
        return itemMapper.deleteById(id) > 0;
    }
    /**
     * @Author LiuRunzhi
     * @Description 根据ID获取对应字典项
     * @Date 2021/4/21 18:50
     * @Param [id]
     * @Return com.touch.dictionary.model.DictionaryItem
     **/
    @Override
    public Optional<DictionaryItem> findById(Integer id) {
        return Optional.ofNullable(itemMapper.selectById(id));
    }
    /**
     * @Author LiuRunzhi
     * @Description 判断Code是否以存在
     * @Date 2021/4/22 10:10
     * @Param [code]
     * @Return boolean
     **/
    @Override
    public boolean isCodeExist(String code) {
        LambdaQueryWrapper<DictionaryItem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictionaryItem::getCode,code);
        return itemMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * @Author LiuRunzhi
     * @Description 初始化Item的一些字段
     * @Date 2021/4/22 14:27
     * @Param [dictionaryItem]
     * @Return void
     **/
    private void initModle(DictionaryItem dictionaryItem){
        dictionaryItem.setCreatedAt(LocalDateTime.now());
        dictionaryItem.setUpdatedAt(LocalDateTime.now());
        /**
         * 设置Level
         */
        if (null != dictionaryItem.getSupCodeId()){
            DictionaryItem supItem = itemMapper.selectById(dictionaryItem.getSupCodeId());
            dictionaryItem.setItemLevel(supItem.getItemLevel() + 1);
        }else{
            dictionaryItem.setItemLevel(1);
        }
    }

    /**
     * @Author LiuRunzhi
     * @Description 根据字典项编码获取其子项目
     * @Date 2021/4/25 13:23
     * @Param code 字典项编码
     * @Return
     */
    @Override
    public List<DictionaryItem> getItemsBySupCode(String code) {
        return itemMapper.getItemsBySupCode(code);
    }

    @Override
    public List<DictionaryItem> getAll() {
        return null;
    }

    @Override
    public void insert(DictionaryItem item) {

    }

    @Override
    public boolean updateById(DictionaryItem item) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
