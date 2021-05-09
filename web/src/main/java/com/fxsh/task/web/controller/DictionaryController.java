package com.fxsh.task.web.controller;

import com.fxsh.auth.vo.UserVO;
import com.fxsh.common.constant.ResponseCode;
import com.fxsh.common.exceptions.BusinessException;
import com.fxsh.common.wrapper.JsonWrapper;
import com.fxsh.dictionary.model.DictionaryItem;
import com.fxsh.dictionary.service.DictionaryItemService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryItemService dictionaryItemService;

    /**
     * @Author LiuRunzhi
     * @Description 获取根节点列表
     * @Date 2021/4/21 15:49
     * @Param []
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("root")
    public JsonWrapper getRootItems(){
        List<DictionaryItem> roots = dictionaryItemService.getRootItems();
        JsonWrapper result = new JsonWrapper(ResponseCode.OK,null,roots);
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取子节点列表
     * @Date 2021/4/21 15:49
     * @Param [supId]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("/items/{supId}")
    public JsonWrapper getItemsBySupId(@PathVariable Integer supId){
        List<DictionaryItem> items = dictionaryItemService.getItemsBySupId(supId);
        JsonWrapper result = new JsonWrapper(ResponseCode.OK,null,items);
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 根据ID获取对应字典项
     * @Date 2021/4/21 18:51
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("get/{id}")
    public JsonWrapper getItemById(@PathVariable Integer id){
        Optional<DictionaryItem> item = dictionaryItemService.findById(id);
        if (!item.isPresent()){
            throw new BusinessException();
        }
        JsonWrapper result = new JsonWrapper(ResponseCode.OK,null,item.get());
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 验证Code是否唯一
     * @Date 2021/4/22 14:19
     * @Param [code]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("/check/{code}/exist")
    public JsonWrapper checkCodeExist(@PathVariable String code){
        JsonWrapper result = new JsonWrapper();
        boolean isExist = dictionaryItemService.isCodeExist(code);
        result.setData(isExist);
        result.setCode(ResponseCode.OK);
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 新增字典项
     * @Date 2021/4/22 14:21
     * @Param [item]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("add")
    public JsonWrapper add(DictionaryItem item){
        UserVO currentUser = (UserVO)SecurityUtils.getSubject().getPrincipal();
        item.setCreatedBy(currentUser.getId());
        item.setUpdatedBy(currentUser.getId());
        dictionaryItemService.addItems(item);
        return new JsonWrapper(ResponseCode.OK,null,item);
    }
    /**
     * @Author LiuRunzhi
     * @Description 根据ID更新Item
     * @Date 2021/4/22 15:52
     * @Param [item]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("update")
    public JsonWrapper update(DictionaryItem item){
        UserVO currentUser = (UserVO)SecurityUtils.getSubject().getPrincipal();
        item.setUpdatedBy(currentUser.getId());
        JsonWrapper result = new JsonWrapper();
        if (dictionaryItemService.updateItems(item)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 停用Item
     * @Date 2021/4/22 16:08
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("item/{id}/delete")
    public JsonWrapper delete(@PathVariable Integer id){

        JsonWrapper result = new JsonWrapper();
        if (dictionaryItemService.deleteItemById(id)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 根据Code获取子字典项
     * @Date 2021/4/25 13:30
     * @Param [code]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("subItems/of/{code}")
    public JsonWrapper getSubItemsByCode(@PathVariable String code){
        return new JsonWrapper(ResponseCode.OK,null,dictionaryItemService.getItemsBySupCode(code));
    }
}
