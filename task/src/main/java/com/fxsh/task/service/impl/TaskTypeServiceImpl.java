package com.fxsh.task.service.impl;

import com.fxsh.common.exceptions.BusinessException;
import com.fxsh.task.mappers.TaskTypeMapper;
import com.fxsh.task.model.TaskType;
import com.fxsh.task.service.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class TaskTypeServiceImpl implements TaskTypeService {
    @Autowired
    private TaskTypeMapper taskTypeMapper;
    @Override
    public Optional<TaskType> findById(Integer id) {
        return Optional.ofNullable(taskTypeMapper.selectById(id));
    }

    @Override
    public List<TaskType> getAll() {
        return taskTypeMapper.selectList(null);
    }

    @Override
    public void insert(TaskType taskType) {
        this.initModel(taskType);
        taskTypeMapper.insert(taskType);
    }

    @Override
    public boolean updateById(TaskType taskType) {
        this.preUpdateModel(taskType);
        return taskTypeMapper.updateById(taskType) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<TaskType> taskTypeOptional = this.findById(id);
        if (!taskTypeOptional.isPresent()){
            throw new BusinessException();
        }
        TaskType taskType = taskTypeOptional.get();
        this.preUpdateModel(taskType);
        taskTypeMapper.updateById(taskType);
        return taskTypeMapper.deleteById(id) > 0;
    }
}
