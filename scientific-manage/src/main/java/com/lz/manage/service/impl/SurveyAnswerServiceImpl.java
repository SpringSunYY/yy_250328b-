package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.*;
import com.lz.manage.model.dto.surveyAnswer.SurveyAnswerInsert;
import com.lz.manage.model.dto.surveyAnswer.SurveyAnswerRequest;
import com.lz.manage.service.*;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.SurveyAnswerMapper;
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

    @Resource
    private IResearchSurveyService researchSurveyService;

    @Resource
    private ISurveyUserService surveyUserService;

    @Resource
    private ISurveyQuestionService surveyQuestionService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IRemindHistoryService remindHistoryService;

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
        List<SurveyAnswer> surveyAnswers = surveyAnswerMapper.selectSurveyAnswerList(surveyAnswer);
        for (SurveyAnswer info : surveyAnswers) {
            SurveyQuestion surveyQuestion = surveyQuestionService.selectSurveyQuestionById(info.getQuestionId());
            if (StringUtils.isNotNull(surveyQuestion)) {
                info.setQuestionName(surveyQuestion.getQuestionTitle());
            }
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
        return surveyAnswers;
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

    @Override
    public int insertSurveyAnswerList(SurveyAnswerRequest surveyAnswerRequest) {
        //判断用户是否已经提交
        SurveyUser surveyUser = surveyUserService.selectSurveyUserById(surveyAnswerRequest.getSurveyUserId());
        if (StringUtils.isNull(surveyUser)) {
            throw new ServiceException("答题不存在！！！");
        }
        if (surveyUser.getStatus().equals("1")) {
            throw new ServiceException("用户已经提交了问卷！！！");
        }
        //如果时间超过结束时间
        if (surveyUser.getEndTime().before(new Date())) {
            throw new ServiceException("答题时间已结束！！！");
        }
        //如果用户传过来是提交
        List<SurveyAnswerInsert> surveyAnswerInserts = surveyAnswerRequest.getSurveyAnswerInserts();
        List<SurveyAnswer> surveyAnswers = SurveyAnswerInsert.insertToObj(surveyAnswerInserts);
        if (StringUtils.isEmpty(surveyAnswers)) {
            throw new ServiceException("请填写问卷！！！");
        }

        SurveyAnswer surveyAnswer = surveyAnswers.get(0);
        //校验是否有必填的题没有填
        for (SurveyAnswer info : surveyAnswers) {
            SurveyQuestion surveyQuestion = surveyQuestionService.selectSurveyQuestionById(info.getQuestionId());
            if (StringUtils.isNull(surveyQuestion)) {
                throw new ServiceException("题目不存在！！！");
            }
            if (info.getSubmitStatus().equals("1") && surveyQuestion.getIsRequired().equals("1") && StringUtils.isEmpty(info.getAnswer())) {
                throw new ServiceException("题目" + surveyQuestion.getQuestionTitle() + "为必填项！！！");
            }
            if (info.getSubmitStatus().equals("1")) {
                info.setSubmitTime(DateUtils.getNowDate());
            }
            if (StringUtils.isNull(info.getId())) {
                info.setCreateTime(DateUtils.getNowDate());
            }
            info.setSurveyUserId(surveyAnswerRequest.getSurveyUserId());
            info.setSurveyId(surveyQuestion.getSurveyId());
            info.setQuestionType(surveyQuestion.getQuestionType());
            info.setQuestionOrder(surveyQuestion.getQuestionOrder());
            info.setDeptId(surveyUser.getDeptId());
            info.setUserId(surveyUser.getUserId());
        }
        if (surveyAnswer.getSubmitStatus().equals("1")) {
            //更新用户答题状态
            surveyUser.setSubmitTime(DateUtils.getNowDate());
            surveyUser.setStatus("1");
            surveyUserService.updateSurveyUser(surveyUser);

            //发送消息给创建人
            RemindHistory remindHistory = new RemindHistory();
            remindHistory.setUserId(userService.selectUserByUserName(surveyUser.getCreateBy()).getUserId());
            remindHistory.setDeptId(surveyUser.getDeptId());
            remindHistory.setContent(StringUtils.format("用户{}已经填写问卷{}，请立即查看！！！",
                    userService.selectUserById(surveyUser.getUserId()).getUserName(),
                    surveyQuestionService.selectSurveyQuestionById(surveyUser.getSurveyId()).getQuestionTitle()));
            remindHistoryService.insertRemindHistory(remindHistory);
        }
        return this.saveOrUpdateBatch(surveyAnswers) ? 1 : 0;
    }

}
