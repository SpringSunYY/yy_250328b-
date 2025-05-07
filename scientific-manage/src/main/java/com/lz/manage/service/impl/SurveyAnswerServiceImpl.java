package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.annotation.DataScope;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.SurveyAnswerMapper;
import com.lz.manage.model.domain.SurveyAnswer;
import com.lz.manage.service.ISurveyAnswerService;
import com.lz.manage.model.dto.surveyAnswer.SurveyAnswerQuery;
import com.lz.manage.model.vo.surveyAnswer.SurveyAnswerVo;

/**
 * 用户填写的完整问卷记录Service业务层处理
 *
 * @author YY
 * @date 2025-05-04
 */
@Service
public class SurveyAnswerServiceImpl extends ServiceImpl<SurveyAnswerMapper, SurveyAnswer> implements ISurveyAnswerService {
    @Resource
    private SurveyAnswerMapper surveyAnswerMapper;

    //region mybatis代码

    /**
     * 查询用户填写的完整问卷记录
     *
     * @param id 用户填写的完整问卷记录主键
     * @return 用户填写的完整问卷记录
     */
    @Override
    public SurveyAnswer selectSurveyAnswerById(Long id) {
        return surveyAnswerMapper.selectSurveyAnswerById(id);
    }

    /**
     * 查询用户填写的完整问卷记录列表
     *
     * @param surveyAnswer 用户填写的完整问卷记录
     * @return 用户填写的完整问卷记录
     */
    @DataScope(userAlias = "tb_survey_answer", deptAlias = "tb_survey_answer")
    @Override
    public List<SurveyAnswer> selectSurveyAnswerList(SurveyAnswer surveyAnswer) {
        return surveyAnswerMapper.selectSurveyAnswerList(surveyAnswer);
    }

    /**
     * 新增用户填写的完整问卷记录
     *
     * @param surveyAnswer 用户填写的完整问卷记录
     * @return 结果
     */
    @Override
    public int insertSurveyAnswer(SurveyAnswer surveyAnswer) {
        surveyAnswer.setCreateTime(DateUtils.getNowDate());
        return surveyAnswerMapper.insertSurveyAnswer(surveyAnswer);
    }

    /**
     * 修改用户填写的完整问卷记录
     *
     * @param surveyAnswer 用户填写的完整问卷记录
     * @return 结果
     */
    @Override
    public int updateSurveyAnswer(SurveyAnswer surveyAnswer) {
        return surveyAnswerMapper.updateSurveyAnswer(surveyAnswer);
    }

    /**
     * 批量删除用户填写的完整问卷记录
     *
     * @param ids 需要删除的用户填写的完整问卷记录主键
     * @return 结果
     */
    @Override
    public int deleteSurveyAnswerByIds(Long[] ids) {
        return surveyAnswerMapper.deleteSurveyAnswerByIds(ids);
    }

    /**
     * 删除用户填写的完整问卷记录信息
     *
     * @param id 用户填写的完整问卷记录主键
     * @return 结果
     */
    @Override
    public int deleteSurveyAnswerById(Long id) {
        return surveyAnswerMapper.deleteSurveyAnswerById(id);
    }

    //endregion
    @Override
    public QueryWrapper<SurveyAnswer> getQueryWrapper(SurveyAnswerQuery surveyAnswerQuery) {
        QueryWrapper<SurveyAnswer> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = surveyAnswerQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = surveyAnswerQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long surveyId = surveyAnswerQuery.getSurveyId();
        queryWrapper.eq(StringUtils.isNotNull(surveyId), "survey_id", surveyId);

        Long questionId = surveyAnswerQuery.getQuestionId();
        queryWrapper.eq(StringUtils.isNotNull(questionId), "question_id", questionId);

        String questionType = surveyAnswerQuery.getQuestionType();
        queryWrapper.eq(StringUtils.isNotEmpty(questionType), "question_type", questionType);

        Long userId = surveyAnswerQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = surveyAnswerQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Long questionOrder = surveyAnswerQuery.getQuestionOrder();
        queryWrapper.eq(StringUtils.isNotNull(questionOrder), "question_order", questionOrder);

        Date createTime = surveyAnswerQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        Date submitTime = surveyAnswerQuery.getSubmitTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginSubmitTime")) && StringUtils.isNotNull(params.get("endSubmitTime")), "submit_time", params.get("beginSubmitTime"), params.get("endSubmitTime"));

        String submitStatus = surveyAnswerQuery.getSubmitStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(submitStatus), "submit_status", submitStatus);

        return queryWrapper;
    }

    @Override
    public List<SurveyAnswerVo> convertVoList(List<SurveyAnswer> surveyAnswerList) {
        if (StringUtils.isEmpty(surveyAnswerList)) {
            return Collections.emptyList();
        }
        return surveyAnswerList.stream().map(SurveyAnswerVo::objToVo).collect(Collectors.toList());
    }

}
