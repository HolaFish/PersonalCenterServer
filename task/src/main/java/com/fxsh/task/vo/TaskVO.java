package com.fxsh.task.vo;

import com.fxsh.auth.vo.UserVO;
import lombok.Data;

import java.util.List;
@Data
public class TaskVO {
    private Integer id;
    private String name;
    private String detail;
    /**
     * 事件在日历中的展示颜色
     */
    private String color;
    /**
     * 办公地点
     */
    private Integer position;
    private String positionName;
    private Integer taskType;
    private String taskTypeName;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private UserVO createdBy;
    /**
     * 是否为整天事件
     */
    private boolean allDay;
    private String remark;
    /**
     * 执行者
     */
    private List<UserVO> executors;
    /**
     * 客户窗口
     */
    private List<UserVO> customers;
    /**
     * 供Task分类使用
     */
    private String category;
}
