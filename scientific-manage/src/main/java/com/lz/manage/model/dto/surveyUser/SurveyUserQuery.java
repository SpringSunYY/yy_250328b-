package com.lz.manage.model.dto.surveyUser;

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
import com.lz.manage.model.domain.SurveyUser;
/**
 * 用户调研Query对象 tb_survey_user
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyUserQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 答卷 */
    private Long surveyId;

    /** 用户 */
    private Long userId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date submitTime;

    /** 部门 */
    private Long deptId;

    /** 创建人 */
    private String createBy;

    /** 状态 */
    private String status;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param surveyUserQuery 查询对象
     * @return SurveyUser
     */
    public static SurveyUser queryToObj(SurveyUserQuery surveyUserQuery) {
        if (surveyUserQuery == null) {
            return null;
        }
        SurveyUser surveyUser = new SurveyUser();
        BeanUtils.copyProperties(surveyUserQuery, surveyUser);
        return surveyUser;
    }
}
