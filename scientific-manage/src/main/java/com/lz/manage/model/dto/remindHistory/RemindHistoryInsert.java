package com.lz.manage.model.dto.remindHistory;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.RemindHistory;
/**
 * 提醒记录Vo对象 tb_remind_history
 *
 * @author ruoyi
 * @date 2025-05-04
 */
@Data
public class RemindHistoryInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户 */
    private Long userId;

    /** 内容 */
    private String content;

    /** 部门 */
    private Long deptId;

    /** 状态 */
    private String isRead;

    /**
     * 对象转封装类
     *
     * @param remindHistoryInsert 插入对象
     * @return RemindHistoryInsert
     */
    public static RemindHistory insertToObj(RemindHistoryInsert remindHistoryInsert) {
        if (remindHistoryInsert == null) {
            return null;
        }
        RemindHistory remindHistory = new RemindHistory();
        BeanUtils.copyProperties(remindHistoryInsert, remindHistory);
        return remindHistory;
    }
}
