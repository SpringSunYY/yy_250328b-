package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.RemindHistory;
import com.lz.manage.model.vo.remindHistory.RemindHistoryVo;
import com.lz.manage.model.dto.remindHistory.RemindHistoryQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 提醒记录Service接口
 * 
 * @author ruoyi
 * @date 2025-05-04
 */
public interface IRemindHistoryService extends IService<RemindHistory>
{
    //region mybatis代码
    /**
     * 查询提醒记录
     * 
     * @param id 提醒记录主键
     * @return 提醒记录
     */
    public RemindHistory selectRemindHistoryById(Long id);

    /**
     * 查询提醒记录列表
     * 
     * @param remindHistory 提醒记录
     * @return 提醒记录集合
     */
    public List<RemindHistory> selectRemindHistoryList(RemindHistory remindHistory);

    /**
     * 新增提醒记录
     * 
     * @param remindHistory 提醒记录
     * @return 结果
     */
    public int insertRemindHistory(RemindHistory remindHistory);

    /**
     * 修改提醒记录
     * 
     * @param remindHistory 提醒记录
     * @return 结果
     */
    public int updateRemindHistory(RemindHistory remindHistory);

    /**
     * 批量删除提醒记录
     * 
     * @param ids 需要删除的提醒记录主键集合
     * @return 结果
     */
    public int deleteRemindHistoryByIds(Long[] ids);

    /**
     * 删除提醒记录信息
     * 
     * @param id 提醒记录主键
     * @return 结果
     */
    public int deleteRemindHistoryById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param remindHistoryQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<RemindHistory> getQueryWrapper(RemindHistoryQuery remindHistoryQuery);

    /**
     * 转换vo
     *
     * @param remindHistoryList RemindHistory集合
     * @return RemindHistoryVO集合
     */
    List<RemindHistoryVo> convertVoList(List<RemindHistory> remindHistoryList);
}
