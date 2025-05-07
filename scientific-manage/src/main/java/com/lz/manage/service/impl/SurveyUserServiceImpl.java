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
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.annotation.Resource;

import com.lz.manage.model.domain.RemindHistory;
import com.lz.manage.model.domain.ResearchSurvey;
import com.lz.manage.service.IRemindHistoryService;
import com.lz.manage.service.IResearchSurveyService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.SurveyUserMapper;
import com.lz.manage.model.domain.SurveyUser;
import com.lz.manage.service.ISurveyUserService;
import com.lz.manage.model.dto.surveyUser.SurveyUserQuery;
import com.lz.manage.model.vo.surveyUser.SurveyUserVo;

/**
 * 用户调研Service业务层处理
 *
 * @author YY
 * @date 2025-05-04
 */
@Service
public class SurveyUserServiceImpl extends ServiceImpl<SurveyUserMapper, SurveyUser> implements ISurveyUserService {
    @Resource
    private SurveyUserMapper surveyUserMapper;

    @Resource
    private IResearchSurveyService researchSurveyService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private ISysUserService userService;

    @Resource
    private IRemindHistoryService remindHistoryService;

    //region mybatis代码

    /**
     * 查询用户调研
     *
     * @param id 用户调研主键
     * @return 用户调研
     */
    @Override
    public SurveyUser selectSurveyUserById(Long id) {
        return surveyUserMapper.selectSurveyUserById(id);
    }

    /**
     * 查询用户调研列表
     *
     * @param surveyUser 用户调研
     * @return 用户调研
     */
    @DataScope(userAlias = "tb_survey_user", deptAlias = "tb_survey_user")
    @Override
    public List<SurveyUser> selectSurveyUserList(SurveyUser surveyUser) {
        List<SurveyUser> surveyUsers = surveyUserMapper.selectSurveyUserList(surveyUser);
        for (SurveyUser info : surveyUsers) {
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
        return surveyUsers;
    }

    /**
     * 新增用户调研
     *
     * @param surveyUser 用户调研
     * @return 结果
     */
    @Override
    public int insertSurveyUser(SurveyUser surveyUser) {
        //查询是否存在问卷
        ResearchSurvey researchSurvey = researchSurveyService.selectResearchSurveyById(surveyUser.getSurveyId());
        if (StringUtils.isNull(researchSurvey)) {
            throw new ServiceException("问卷不存在！！！");
        }
        surveyUser.setDeptId(researchSurvey.getUserId());
        //查询用户是否存在
        SysUser user = userService.selectUserById(surveyUser.getUserId());
        if (StringUtils.isNull(user)) {
            throw new ServiceException("用户不存在！！！");
        }
        //如果开始时间比结束时间晚
        if (surveyUser.getStartTime().after(surveyUser.getEndTime())) {
            throw new ServiceException("开始时间小于结束时间！！！");
        }
        surveyUser.setStatus("0");
        surveyUser.setCreateBy(SecurityUtils.getUsername());
        //发送通知给对应的用户
        RemindHistory remindHistory = new RemindHistory();
        remindHistory.setUserId(surveyUser.getUserId());
        remindHistory.setDeptId(researchSurvey.getDeptId());
        remindHistory.setContent(StringUtils.format("尊敬的用户：{}，您有一份新的问卷调查：{}，请立即填写",
                user.getNickName(),
                researchSurvey.getSurveyTitle()));
        remindHistoryService.insertRemindHistory(remindHistory);
        return surveyUserMapper.insertSurveyUser(surveyUser);
    }

    /**
     * 修改用户调研
     *
     * @param surveyUser 用户调研
     * @return 结果
     */
    @Override
    public int updateSurveyUser(SurveyUser surveyUser) {
        //如果开始时间比结束时间晚
        if (surveyUser.getStartTime().after(surveyUser.getEndTime())) {
            throw new ServiceException("开始时间小于结束时间！！！");
        }
        //获取数据库内容
        SurveyUser surveyUserDb = surveyUserMapper.selectSurveyUserById(surveyUser.getSurveyId());
        if (StringUtils.isNull(surveyUserDb)) {
            throw new ServiceException("数据不存在！！！");
        }
        //如果改变了问卷或者用户
        if (!surveyUserDb.getSurveyId().equals(surveyUser.getSurveyId()) || !surveyUserDb.getUserId().equals(surveyUser.getUserId())) {
            throw new ServiceException("不可以更改用户或者问卷！！！");
        }
        //如果以及答完题
        if (surveyUserDb.getStatus().equals("1")) {
            throw new ServiceException("用户已答题不可修改！！！");
        }
        return surveyUserMapper.updateSurveyUser(surveyUser);
    }

    /**
     * 批量删除用户调研
     *
     * @param ids 需要删除的用户调研主键
     * @return 结果
     */
    @Override
    public int deleteSurveyUserByIds(Long[] ids) {
        return surveyUserMapper.deleteSurveyUserByIds(ids);
    }

    /**
     * 删除用户调研信息
     *
     * @param id 用户调研主键
     * @return 结果
     */
    @Override
    public int deleteSurveyUserById(Long id) {
        return surveyUserMapper.deleteSurveyUserById(id);
    }

    //endregion
    @Override
    public QueryWrapper<SurveyUser> getQueryWrapper(SurveyUserQuery surveyUserQuery) {
        QueryWrapper<SurveyUser> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = surveyUserQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = surveyUserQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long surveyId = surveyUserQuery.getSurveyId();
        queryWrapper.eq(StringUtils.isNotNull(surveyId), "survey_id", surveyId);

        Long userId = surveyUserQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date startTime = surveyUserQuery.getStartTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginStartTime")) && StringUtils.isNotNull(params.get("endStartTime")), "start_time", params.get("beginStartTime"), params.get("endStartTime"));

        Date endTime = surveyUserQuery.getEndTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginEndTime")) && StringUtils.isNotNull(params.get("endEndTime")), "end_time", params.get("beginEndTime"), params.get("endEndTime"));

        Date submitTime = surveyUserQuery.getSubmitTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginSubmitTime")) && StringUtils.isNotNull(params.get("endSubmitTime")), "submit_time", params.get("beginSubmitTime"), params.get("endSubmitTime"));

        Long deptId = surveyUserQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        String createBy = surveyUserQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        String status = surveyUserQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        return queryWrapper;
    }

    @Override
    public List<SurveyUserVo> convertVoList(List<SurveyUser> surveyUserList) {
        if (StringUtils.isEmpty(surveyUserList)) {
            return Collections.emptyList();
        }
        return surveyUserList.stream().map(SurveyUserVo::objToVo).collect(Collectors.toList());
    }

}
