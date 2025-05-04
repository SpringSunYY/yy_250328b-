package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.SurveyAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户填写的完整问卷记录Mapper接口
 * 
 * @author YY
 * @date 2025-05-04
 */
public interface SurveyAnswerMapper extends BaseMapper<SurveyAnswer>
{
    /**
     * 查询用户填写的完整问卷记录
     * 
     * @param id 用户填写的完整问卷记录主键
     * @return 用户填写的完整问卷记录
     */
    public SurveyAnswer selectSurveyAnswerById(Long id);

    /**
     * 查询用户填写的完整问卷记录列表
     * 
     * @param surveyAnswer 用户填写的完整问卷记录
     * @return 用户填写的完整问卷记录集合
     */
    public List<SurveyAnswer> selectSurveyAnswerList(SurveyAnswer surveyAnswer);

    /**
     * 新增用户填写的完整问卷记录
     * 
     * @param surveyAnswer 用户填写的完整问卷记录
     * @return 结果
     */
    public int insertSurveyAnswer(SurveyAnswer surveyAnswer);

    /**
     * 修改用户填写的完整问卷记录
     * 
     * @param surveyAnswer 用户填写的完整问卷记录
     * @return 结果
     */
    public int updateSurveyAnswer(SurveyAnswer surveyAnswer);

    /**
     * 删除用户填写的完整问卷记录
     * 
     * @param id 用户填写的完整问卷记录主键
     * @return 结果
     */
    public int deleteSurveyAnswerById(Long id);

    /**
     * 批量删除用户填写的完整问卷记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSurveyAnswerByIds(Long[] ids);
}
