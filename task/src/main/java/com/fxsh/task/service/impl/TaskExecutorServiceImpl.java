package com.fxsh.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fxsh.common.exceptions.BusinessException;
import com.fxsh.task.mappers.TaskExecutorMapper;
import com.fxsh.task.model.TaskExecutor;
import com.fxsh.task.service.TaskExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class TaskExecutorServiceImpl implements TaskExecutorService {
    @Autowired
    private TaskExecutorMapper taskExecutorMapper;
    @Override
    public Optional<TaskExecutor> findById(Integer id) {
        return Optional.ofNullable(taskExecutorMapper.selectById(id));
    }

    @Override
    public List<TaskExecutor> getAll() {
        return taskExecutorMapper.selectList(null);
    }

    @Override
    public void insert(TaskExecutor taskExecutor) {
        this.initModel(taskExecutor);
        taskExecutorMapper.insert(taskExecutor);
    }

    @Override
    public boolean updateById(TaskExecutor taskExecutor) {
        preUpdateModel(taskExecutor);
        return taskExecutorMapper.updateById(taskExecutor) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<TaskExecutor> taskExecutorOptional = this.findById(id);
        if (!taskExecutorOptional.isPresent()){
            throw new BusinessException();
        }
        TaskExecutor taskExecutor = taskExecutorOptional.get();
        preUpdateModel(taskExecutor);
        taskExecutor.setDeleted(true);
        return updateById(taskExecutor);
    }

    /**
     * @param taskId
     * @Author LiuRunzhi
     * @Description 感觉TaskID删除记录
     * @Date 2021/4/27 14:56
     * @Param taskId
     * @Return
     */
    @Override
    public boolean deleteByTaskId(Integer taskId) {
        LambdaUpdateWrapper<TaskExecutor> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(TaskExecutor::getUpdatedAt, LocalDateTime.now())
                .set(TaskExecutor::getUpdatedBy, this.getCurrentUser().getId())
                .eq(TaskExecutor::getTaskId,taskId);
        taskExecutorMapper.update(null,updateWrapper);
        LambdaQueryWrapper<TaskExecutor> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TaskExecutor::getTaskId,taskId);
        return taskExecutorMapper.delete(queryWrapper) > 0;
    }
}
