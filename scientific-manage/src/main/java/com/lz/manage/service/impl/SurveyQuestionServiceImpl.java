package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.ResearchSurvey;
import com.lz.manage.service.IResearchSurveyService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.SurveyQuestionMapper;
import com.lz.manage.model.domain.SurveyQuestion;
import com.lz.manage.service.ISurveyQuestionService;
import com.lz.manage.model.dto.surveyQuestion.SurveyQuestionQuery;
import com.lz.manage.model.vo.surveyQuestion.SurveyQuestionVo;

/**
 * 调研题目Service业务层处理
 *
 * @author YY
 * @date 2025-05-04
 */
@Service
public class SurveyQuestionServiceImpl extends ServiceImpl<SurveyQuestionMapper, SurveyQuestion> implements ISurveyQuestionService {
    @Resource
    private SurveyQuestionMapper surveyQuestionMapper;

    @Resource
    private IResearchSurveyService researchSurveyService;
    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    //region mybatis代码

    /**
     * 查询调研题目
     *
     * @param id 调研题目主键
     * @return 调研题目
     */
    @Override
    public SurveyQuestion selectSurveyQuestionById(Long id) {
        return surveyQuestionMapper.selectSurveyQuestionById(id);
    }

    /**
     * 查询调研题目列表
     *
     * @param surveyQuestion 调研题目
     * @return 调研题目
     */
    @Override
    public List<SurveyQuestion> selectSurveyQuestionList(SurveyQuestion surveyQuestion) {
        List<SurveyQuestion> surveyQuestions = surveyQuestionMapper.selectSurveyQuestionList(surveyQuestion);
        for (SurveyQuestion info : surveyQuestions) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getNickName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
            ResearchSurvey researchSurvey = researchSurveyService.selectResearchSurveyById(info.getSurveyId());
            if (StringUtils.isNotNull(researchSurvey)) {
                info.setSurveyName(researchSurvey.getSurveyTitle());
            }
        }
        return surveyQuestions;
    }

    /**
     * 新增调研题目
     *
     * @param surveyQuestion 调研题目
     * @return 结果
     */
    @Override
    public int insertSurveyQuestion(SurveyQuestion surveyQuestion) {
        //获取题目信息
        ResearchSurvey researchSurvey = researchSurveyService.selectResearchSurveyById(surveyQuestion.getSurveyId());
        if (StringUtils.isNull(researchSurvey)) {
            throw new RuntimeException("调研题目不存在");
        }
        surveyQuestion.setDeptId(researchSurvey.getDeptId());
        surveyQuestion.setUserId(SecurityUtils.getUserId());
        surveyQuestion.setCreateTime(DateUtils.getNowDate());
        return surveyQuestionMapper.insertSurveyQuestion(surveyQuestion);
    }

    /**
     * 修改调研题目
     *
     * @param surveyQuestion 调研题目
     * @return 结果
     */
    @Override
    public int updateSurveyQuestion(SurveyQuestion surveyQuestion) {
        //获取题目信息
        ResearchSurvey researchSurvey = researchSurveyService.selectResearchSurveyById(surveyQuestion.getSurveyId());
        if (StringUtils.isNull(researchSurvey)) {
            throw new RuntimeException("调研题目不存在");
        }
        surveyQuestion.setDeptId(surveyQuestion.getDeptId());
        return surveyQuestionMapper.updateSurveyQuestion(surveyQuestion);
    }

    /**
     * 批量删除调研题目
     *
     * @param ids 需要删除的调研题目主键
     * @return 结果
     */
    @Override
    public int deleteSurveyQuestionByIds(Long[] ids) {
        return surveyQuestionMapper.deleteSurveyQuestionByIds(ids);
    }

    /**
     * 删除调研题目信息
     *
     * @param id 调研题目主键
     * @return 结果
     */
    @Override
    public int deleteSurveyQuestionById(Long id) {
        return surveyQuestionMapper.deleteSurveyQuestionById(id);
    }

    //endregion
    @Override
    public QueryWrapper<SurveyQuestion> getQueryWrapper(SurveyQuestionQuery surveyQuestionQuery) {
        QueryWrapper<SurveyQuestion> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = surveyQuestionQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = surveyQuestionQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long surveyId = surveyQuestionQuery.getSurveyId();
        queryWrapper.eq(StringUtils.isNotNull(surveyId), "survey_id", surveyId);

        String questionType = surveyQuestionQuery.getQuestionType();
        queryWrapper.eq(StringUtils.isNotEmpty(questionType), "question_type", questionType);

        String questionTitle = surveyQuestionQuery.getQuestionTitle();
        queryWrapper.like(StringUtils.isNotEmpty(questionTitle), "question_title", questionTitle);

        Long questionOrder = surveyQuestionQuery.getQuestionOrder();
        queryWrapper.eq(StringUtils.isNotNull(questionOrder), "question_order", questionOrder);

        Long userId = surveyQuestionQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = surveyQuestionQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = surveyQuestionQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String isRequired = surveyQuestionQuery.getIsRequired();
        queryWrapper.eq(StringUtils.isNotEmpty(isRequired), "is_required", isRequired);

        return queryWrapper;
    }

    @Override
    public List<SurveyQuestionVo> convertVoList(List<SurveyQuestion> surveyQuestionList) {
        if (StringUtils.isEmpty(surveyQuestionList)) {
            return Collections.emptyList();
        }
        return surveyQuestionList.stream().map(SurveyQuestionVo::objToVo).collect(Collectors.toList());
    }

}
