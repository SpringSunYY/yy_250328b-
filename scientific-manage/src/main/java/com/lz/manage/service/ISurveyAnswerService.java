package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.SurveyAnswer;
import com.lz.manage.model.vo.surveyAnswer.SurveyAnswerVo;
import com.lz.manage.model.dto.surveyAnswer.SurveyAnswerQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 用户填写的完整问卷记录Service接口
 * 
 * @author YY
 * @date 2025-05-04
 */
public interface ISurveyAnswerService extends IService<SurveyAnswer>
{
    //region mybatis代码
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
     * 批量删除用户填写的完整问卷记录
     * 
     * @param ids 需要删除的用户填写的完整问卷记录主键集合
     * @return 结果
     */
    public int deleteSurveyAnswerByIds(Long[] ids);

    /**
     * 删除用户填写的完整问卷记录信息
     * 
     * @param id 用户填写的完整问卷记录主键
     * @return 结果
     */
    public int deleteSurveyAnswerById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param surveyAnswerQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<SurveyAnswer> getQueryWrapper(SurveyAnswerQuery surveyAnswerQuery);

    /**
     * 转换vo
     *
     * @param surveyAnswerList SurveyAnswer集合
     * @return SurveyAnswerVO集合
     */
    List<SurveyAnswerVo> convertVoList(List<SurveyAnswer> surveyAnswerList);
}
