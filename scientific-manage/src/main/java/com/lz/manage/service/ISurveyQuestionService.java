package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.SurveyQuestion;
import com.lz.manage.model.vo.surveyQuestion.SurveyQuestionVo;
import com.lz.manage.model.dto.surveyQuestion.SurveyQuestionQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 调研题目Service接口
 * 
 * @author YY
 * @date 2025-05-04
 */
public interface ISurveyQuestionService extends IService<SurveyQuestion>
{
    //region mybatis代码
    /**
     * 查询调研题目
     * 
     * @param id 调研题目主键
     * @return 调研题目
     */
    public SurveyQuestion selectSurveyQuestionById(Long id);

    /**
     * 查询调研题目列表
     * 
     * @param surveyQuestion 调研题目
     * @return 调研题目集合
     */
    public List<SurveyQuestion> selectSurveyQuestionList(SurveyQuestion surveyQuestion);

    /**
     * 新增调研题目
     * 
     * @param surveyQuestion 调研题目
     * @return 结果
     */
    public int insertSurveyQuestion(SurveyQuestion surveyQuestion);

    /**
     * 修改调研题目
     * 
     * @param surveyQuestion 调研题目
     * @return 结果
     */
    public int updateSurveyQuestion(SurveyQuestion surveyQuestion);

    /**
     * 批量删除调研题目
     * 
     * @param ids 需要删除的调研题目主键集合
     * @return 结果
     */
    public int deleteSurveyQuestionByIds(Long[] ids);

    /**
     * 删除调研题目信息
     * 
     * @param id 调研题目主键
     * @return 结果
     */
    public int deleteSurveyQuestionById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param surveyQuestionQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<SurveyQuestion> getQueryWrapper(SurveyQuestionQuery surveyQuestionQuery);

    /**
     * 转换vo
     *
     * @param surveyQuestionList SurveyQuestion集合
     * @return SurveyQuestionVO集合
     */
    List<SurveyQuestionVo> convertVoList(List<SurveyQuestion> surveyQuestionList);
}
