package com.fxsh.task.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fxsh.auth.model.User;
import com.fxsh.auth.service.UserService;
import com.fxsh.auth.vo.UserVO;
import com.fxsh.common.constant.ResponseCode;
import com.fxsh.common.wrapper.JsonWrapper;
import com.fxsh.task.model.Task;
import com.fxsh.task.model.TaskType;
import com.fxsh.task.service.TaskTypeService;
import com.fxsh.task.service.TaskService;
import com.fxsh.task.vo.TaskVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    /**
     * @Author LiuRunzhi
     * @Description 获取所有的任务类型
     * @Date 2021/4/26 13:10
     * @Param []
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("type/all")
    public JsonWrapper getAllTaskType(){
        return new JsonWrapper(ResponseCode.OK,null,taskTypeService.getAll());
    }
    /**
     * @Author LiuRunzhi
     * @Description 新增任务类型
     * @Date 2021/4/26 13:13
     * @Param [taskType]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("type/add")
    public JsonWrapper addTaskType(TaskType taskType){
        taskTypeService.insert(taskType);
        return new JsonWrapper(ResponseCode.OK,null,taskType);
    }
    /**
     * @Author LiuRunzhi
     * @Description 更新任务类型
     * @Date 2021/4/26 13:15
     * @Param [taskType]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("type/update")
    public JsonWrapper updateTaskType(TaskType taskType){
        JsonWrapper result = new JsonWrapper();
        if (taskTypeService.updateById(taskType)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        result.setData(taskType);
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 删除任务类型
     * @Date 2021/4/26 13:25
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("type/{id}/delete")
    public JsonWrapper deleteTaskType(@PathVariable Integer id){
        JsonWrapper result = new JsonWrapper();
        if (taskTypeService.deleteById(id)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 新增Task
     * @Date 2021/4/27 15:09
     * @Param
     * @Return
     **/
    @PostMapping("add")
    public JsonWrapper addTask(Task task){
        taskService.insert(task);

        TaskVO taskVO = new TaskVO();
        BeanUtil.copyProperties(task,taskVO,"createdBy","executors","customers");
        Optional<TaskType> taskTypeOptional = taskTypeService.findById(task.getTaskType());
        if (taskTypeOptional.isPresent()){
            taskVO.setColor(taskTypeOptional.get().getColor());
        }
        return new JsonWrapper(ResponseCode.OK,null,taskVO);
    }
    /**
     * @Author LiuRunzhi
     * @Description
     * @Date 2021/4/27 17:58
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("get/{id}")
    public JsonWrapper getTaskVO(@PathVariable Integer id){
        return new JsonWrapper(ResponseCode.OK,null,taskService.getTaskVOById(id));
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取当前登录用户的Task列表
     * @Date 2021/4/28 11:14
     * @Param []
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("myTask")
    public JsonWrapper getMyTask(String startDate, String endDate){
        UserVO currentUser = (UserVO) SecurityUtils.getSubject().getPrincipal();
        return new JsonWrapper(ResponseCode.OK,null,taskService.getTaskListByExecutorId(currentUser.getId(),startDate,endDate));
    }
    /**
     * @Author LiuRunzhi
     * @Description 更新Task
     * @Date 2021/4/29 15:13
     * @Param [task]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("update")
    public JsonWrapper update(Task task){
        JsonWrapper result = new JsonWrapper();
        if (taskService.updateById(task)){
            result.setData(taskService.getTaskVOById(task.getId()));
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 删除Task
     * @Date 2021/4/29 15:16
     * @Param [id]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @PostMapping("{id}/delete")
    public JsonWrapper delete(@PathVariable Integer id){
        JsonWrapper result = new JsonWrapper();
        if (taskService.deleteById(id)){
            result.setCode(ResponseCode.OK);
        }else{
            result.setCode(ResponseCode.ERROR);
        }
        return result;
    }
    /**
     * @Author LiuRunzhi
     * @Description 获取公司所有人员的任务
     * @Date 2021/4/29 18:34
     * @Param [startDate, endDate]
     * @Return com.touch.common.wrapper.JsonWrapper
     **/
    @GetMapping("all")
    public JsonWrapper allTask(String startDate, String endDate){

        /**
         *  获取公司的所有成员信息
         */
        List<User> users = userService.getAll();
        /**
         * 获取公司所有员工的的任务
         */
        List<TaskVO> tasks = users.stream().map(user -> {
            List<TaskVO> taskVOS = taskService.getTaskListByExecutorId(user.getId(),startDate,endDate);
            taskVOS.forEach(taskVO -> taskVO.setCategory(user.getUserName()));
            return taskVOS;
        }).flatMap(taskVOS -> taskVOS.stream()).collect(Collectors.toList());
        return new JsonWrapper(ResponseCode.OK,null,tasks);
    }
    @GetMapping("of/{customerId}")
    public JsonWrapper getTaskByCustomerId(@PathVariable Integer customerId, String startDate, String endDate){
        return new JsonWrapper(ResponseCode.OK,null,taskService.getTaskListByCustomerId(customerId,startDate,endDate));
    }
}
