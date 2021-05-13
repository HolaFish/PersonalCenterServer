package com.fxsh.task.service;

import com.fxsh.task.model.YearPlan;

import java.util.List;

public interface PlanService {
    /**
     * @Author 风萧水寒
     * @Description 新增年计划
     * @Date 2021/5/13 9:51 下午
     * @Param 
     * @return 
     **/
    YearPlan insertYearPlan(YearPlan yearPlan);
    /**
     * @Author 风萧水寒
     * @Description 更新年计划
     * @Date 2021/5/13 9:53 下午
     * @Param 
     * @return 
     **/
    boolean updateYearPlan(YearPlan yearPlan);
    /**
     * @Author 风萧水寒
     * @Description 删除年计划
     * @Date 2021/5/13 9:54 下午
     * @Param 
     * @return 
     **/
    boolean deleteYearPlanById(Integer id);
    /**
     * @Author 风萧水寒
     * @Description 根据ID获取年计划
     * @Date 2021/5/13 9:58 下午
     * @Param 
     * @return 
     **/
    YearPlan getYearPlanById(Integer id);
    /**
     * @Author 风萧水寒
     * @Description 根据年份获取其年计划
     * @Date 2021/5/13 9:58 下午
     * @Param 
     * @return 
     **/
    List<YearPlan> getYearPlanByYear(Integer year);
}
