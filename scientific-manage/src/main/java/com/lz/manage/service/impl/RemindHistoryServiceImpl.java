package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.RemindHistoryMapper;
import com.lz.manage.model.domain.RemindHistory;
import com.lz.manage.service.IRemindHistoryService;
import com.lz.manage.model.dto.remindHistory.RemindHistoryQuery;
import com.lz.manage.model.vo.remindHistory.RemindHistoryVo;

/**
 * 提醒记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-04
 */
@Service
public class RemindHistoryServiceImpl extends ServiceImpl<RemindHistoryMapper, RemindHistory> implements IRemindHistoryService
{
    @Resource
    private RemindHistoryMapper remindHistoryMapper;

    //region mybatis代码
    /**
     * 查询提醒记录
     * 
     * @param id 提醒记录主键
     * @return 提醒记录
     */
    @Override
    public RemindHistory selectRemindHistoryById(Long id)
    {
        return remindHistoryMapper.selectRemindHistoryById(id);
    }

    /**
     * 查询提醒记录列表
     * 
     * @param remindHistory 提醒记录
     * @return 提醒记录
     */
    @Override
    public List<RemindHistory> selectRemindHistoryList(RemindHistory remindHistory)
    {
        return remindHistoryMapper.selectRemindHistoryList(remindHistory);
    }

    /**
     * 新增提醒记录
     * 
     * @param remindHistory 提醒记录
     * @return 结果
     */
    @Override
    public int insertRemindHistory(RemindHistory remindHistory)
    {
        remindHistory.setCreateTime(DateUtils.getNowDate());
        return remindHistoryMapper.insertRemindHistory(remindHistory);
    }

    /**
     * 修改提醒记录
     * 
     * @param remindHistory 提醒记录
     * @return 结果
     */
    @Override
    public int updateRemindHistory(RemindHistory remindHistory)
    {
        return remindHistoryMapper.updateRemindHistory(remindHistory);
    }

    /**
     * 批量删除提醒记录
     * 
     * @param ids 需要删除的提醒记录主键
     * @return 结果
     */
    @Override
    public int deleteRemindHistoryByIds(Long[] ids)
    {
        return remindHistoryMapper.deleteRemindHistoryByIds(ids);
    }

    /**
     * 删除提醒记录信息
     * 
     * @param id 提醒记录主键
     * @return 结果
     */
    @Override
    public int deleteRemindHistoryById(Long id)
    {
        return remindHistoryMapper.deleteRemindHistoryById(id);
    }
    //endregion
    @Override
    public QueryWrapper<RemindHistory> getQueryWrapper(RemindHistoryQuery remindHistoryQuery){
        QueryWrapper<RemindHistory> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = remindHistoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = remindHistoryQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long userId = remindHistoryQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String createBy = remindHistoryQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = remindHistoryQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String isRead = remindHistoryQuery.getIsRead();
        queryWrapper.eq(StringUtils.isNotEmpty(isRead) ,"is_read",isRead);

        return queryWrapper;
    }

    @Override
    public List<RemindHistoryVo> convertVoList(List<RemindHistory> remindHistoryList) {
        if (StringUtils.isEmpty(remindHistoryList)) {
            return Collections.emptyList();
        }
        return remindHistoryList.stream().map(RemindHistoryVo::objToVo).collect(Collectors.toList());
    }

}
