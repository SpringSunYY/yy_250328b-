package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.SurveyQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 调研题目Mapper接口
 * 
 * @author YY
 * @date 2025-05-04
 */
public interface SurveyQuestionMapper extends BaseMapper<SurveyQuestion>
{
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
     * 删除调研题目
     * 
     * @param id 调研题目主键
     * @return 结果
     */
    public int deleteSurveyQuestionById(Long id);

    /**
     * 批量删除调研题目
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSurveyQuestionByIds(Long[] ids);
}
