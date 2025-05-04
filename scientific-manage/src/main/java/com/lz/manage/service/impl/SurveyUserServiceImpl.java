package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.annotation.Resource;
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
public class SurveyUserServiceImpl extends ServiceImpl<SurveyUserMapper, SurveyUser> implements ISurveyUserService
{
    @Resource
    private SurveyUserMapper surveyUserMapper;

    //region mybatis代码
    /**
     * 查询用户调研
     * 
     * @param id 用户调研主键
     * @return 用户调研
     */
    @Override
    public SurveyUser selectSurveyUserById(Long id)
    {
        return surveyUserMapper.selectSurveyUserById(id);
    }

    /**
     * 查询用户调研列表
     * 
     * @param surveyUser 用户调研
     * @return 用户调研
     */
    @Override
    public List<SurveyUser> selectSurveyUserList(SurveyUser surveyUser)
    {
        return surveyUserMapper.selectSurveyUserList(surveyUser);
    }

    /**
     * 新增用户调研
     * 
     * @param surveyUser 用户调研
     * @return 结果
     */
    @Override
    public int insertSurveyUser(SurveyUser surveyUser)
    {
        return surveyUserMapper.insertSurveyUser(surveyUser);
    }

    /**
     * 修改用户调研
     * 
     * @param surveyUser 用户调研
     * @return 结果
     */
    @Override
    public int updateSurveyUser(SurveyUser surveyUser)
    {
        return surveyUserMapper.updateSurveyUser(surveyUser);
    }

    /**
     * 批量删除用户调研
     * 
     * @param ids 需要删除的用户调研主键
     * @return 结果
     */
    @Override
    public int deleteSurveyUserByIds(Long[] ids)
    {
        return surveyUserMapper.deleteSurveyUserByIds(ids);
    }

    /**
     * 删除用户调研信息
     * 
     * @param id 用户调研主键
     * @return 结果
     */
    @Override
    public int deleteSurveyUserById(Long id)
    {
        return surveyUserMapper.deleteSurveyUserById(id);
    }
    //endregion
    @Override
    public QueryWrapper<SurveyUser> getQueryWrapper(SurveyUserQuery surveyUserQuery){
        QueryWrapper<SurveyUser> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = surveyUserQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = surveyUserQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long surveyId = surveyUserQuery.getSurveyId();
        queryWrapper.eq( StringUtils.isNotNull(surveyId),"survey_id",surveyId);

        Long userId = surveyUserQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date startTime = surveyUserQuery.getStartTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginStartTime"))&&StringUtils.isNotNull(params.get("endStartTime")),"start_time",params.get("beginStartTime"),params.get("endStartTime"));

        Date endTime = surveyUserQuery.getEndTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginEndTime"))&&StringUtils.isNotNull(params.get("endEndTime")),"end_time",params.get("beginEndTime"),params.get("endEndTime"));

        Date submitTime = surveyUserQuery.getSubmitTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginSubmitTime"))&&StringUtils.isNotNull(params.get("endSubmitTime")),"submit_time",params.get("beginSubmitTime"),params.get("endSubmitTime"));

        Long deptId = surveyUserQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        String createBy = surveyUserQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        String status = surveyUserQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

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
