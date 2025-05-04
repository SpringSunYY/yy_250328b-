package com.lz.manage.model.vo.remindHistory;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.RemindHistory;
/**
 * 提醒记录Vo对象 tb_remind_history
 *
 * @author ruoyi
 * @date 2025-05-04
 */
@Data
public class RemindHistoryVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 用户 */
    @Excel(name = "用户")
    private Long userId;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 状态 */
    @Excel(name = "状态")
    private String isRead;


     /**
     * 对象转封装类
     *
     * @param remindHistory RemindHistory实体对象
     * @return RemindHistoryVo
     */
    public static RemindHistoryVo objToVo(RemindHistory remindHistory) {
        if (remindHistory == null) {
            return null;
        }
        RemindHistoryVo remindHistoryVo = new RemindHistoryVo();
        BeanUtils.copyProperties(remindHistory, remindHistoryVo);
        return remindHistoryVo;
    }
}
