package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.RemindHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 提醒记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-05-04
 */
public interface RemindHistoryMapper extends BaseMapper<RemindHistory>
{
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
     * 删除提醒记录
     * 
     * @param id 提醒记录主键
     * @return 结果
     */
    public int deleteRemindHistoryById(Long id);

    /**
     * 批量删除提醒记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRemindHistoryByIds(Long[] ids);
}
