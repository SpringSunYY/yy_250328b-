package com.lz.manage.model.dto.remindHistory;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.RemindHistory;
/**
 * 提醒记录Query对象 tb_remind_history
 *
 * @author ruoyi
 * @date 2025-05-04
 */
@Data
public class RemindHistoryQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户 */
    private Long userId;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 状态 */
    private String isRead;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param remindHistoryQuery 查询对象
     * @return RemindHistory
     */
    public static RemindHistory queryToObj(RemindHistoryQuery remindHistoryQuery) {
        if (remindHistoryQuery == null) {
            return null;
        }
        RemindHistory remindHistory = new RemindHistory();
        BeanUtils.copyProperties(remindHistoryQuery, remindHistory);
        return remindHistory;
    }
}
