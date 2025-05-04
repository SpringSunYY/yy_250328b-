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

import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ResearchSurveyMapper;
import com.lz.manage.model.domain.ResearchSurvey;
import com.lz.manage.service.IResearchSurveyService;
import com.lz.manage.model.dto.researchSurvey.ResearchSurveyQuery;
import com.lz.manage.model.vo.researchSurvey.ResearchSurveyVo;

/**
 * 调研信息Service业务层处理
 *
 * @author YY
 * @date 2025-05-04
 */
@Service
public class ResearchSurveyServiceImpl extends ServiceImpl<ResearchSurveyMapper, ResearchSurvey> implements IResearchSurveyService {
    @Resource
    private ResearchSurveyMapper researchSurveyMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    //region mybatis代码

    /**
     * 查询调研信息
     *
     * @param id 调研信息主键
     * @return 调研信息
     */
    @Override
    public ResearchSurvey selectResearchSurveyById(Long id) {
        return researchSurveyMapper.selectResearchSurveyById(id);
    }

    /**
     * 查询调研信息列表
     *
     * @param researchSurvey 调研信息
     * @return 调研信息
     */
    @Override
    public List<ResearchSurvey> selectResearchSurveyList(ResearchSurvey researchSurvey) {
        List<ResearchSurvey> researchSurveys = researchSurveyMapper.selectResearchSurveyList(researchSurvey);
        for (ResearchSurvey info : researchSurveys) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getNickName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
        }
        return researchSurveys;
    }

    /**
     * 新增调研信息
     *
     * @param researchSurvey 调研信息
     * @return 结果
     */
    @Override
    public int insertResearchSurvey(ResearchSurvey researchSurvey) {
        researchSurvey.setUserId(SecurityUtils.getUserId());
        researchSurvey.setDeptId(SecurityUtils.getDeptId());
        researchSurvey.setCreateTime(DateUtils.getNowDate());
        return researchSurveyMapper.insertResearchSurvey(researchSurvey);
    }

    /**
     * 修改调研信息
     *
     * @param researchSurvey 调研信息
     * @return 结果
     */
    @Override
    public int updateResearchSurvey(ResearchSurvey researchSurvey) {
        researchSurvey.setUpdatedBy(SecurityUtils.getUsername());
        researchSurvey.setUpdateTime(DateUtils.getNowDate());
        return researchSurveyMapper.updateResearchSurvey(researchSurvey);
    }

    /**
     * 批量删除调研信息
     *
     * @param ids 需要删除的调研信息主键
     * @return 结果
     */
    @Override
    public int deleteResearchSurveyByIds(Long[] ids) {
        return researchSurveyMapper.deleteResearchSurveyByIds(ids);
    }

    /**
     * 删除调研信息信息
     *
     * @param id 调研信息主键
     * @return 结果
     */
    @Override
    public int deleteResearchSurveyById(Long id) {
        return researchSurveyMapper.deleteResearchSurveyById(id);
    }

    //endregion
    @Override
    public QueryWrapper<ResearchSurvey> getQueryWrapper(ResearchSurveyQuery researchSurveyQuery) {
        QueryWrapper<ResearchSurvey> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = researchSurveyQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = researchSurveyQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String surveyTitle = researchSurveyQuery.getSurveyTitle();
        queryWrapper.like(StringUtils.isNotEmpty(surveyTitle), "survey_title", surveyTitle);

        String surveyDesc = researchSurveyQuery.getSurveyDesc();
        queryWrapper.eq(StringUtils.isNotEmpty(surveyDesc), "survey_desc", surveyDesc);

        String status = researchSurveyQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long userId = researchSurveyQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = researchSurveyQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = researchSurveyQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updatedBy = researchSurveyQuery.getUpdatedBy();
        queryWrapper.like(StringUtils.isNotEmpty(updatedBy), "updated_by", updatedBy);

        Date updateTime = researchSurveyQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<ResearchSurveyVo> convertVoList(List<ResearchSurvey> researchSurveyList) {
        if (StringUtils.isEmpty(researchSurveyList)) {
            return Collections.emptyList();
        }
        return researchSurveyList.stream().map(ResearchSurveyVo::objToVo).collect(Collectors.toList());
    }

}
