package com.lz.manage.model.dto.surveyUser;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.SurveyUser;
/**
 * 用户调研Vo对象 tb_survey_user
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyUserInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 答卷 */
    private Long surveyId;

    /** 用户 */
    private Long userId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;

    /** 部门 */
    private Long deptId;

    /** 状态 */
    private String status;

    /**
     * 对象转封装类
     *
     * @param surveyUserInsert 插入对象
     * @return SurveyUserInsert
     */
    public static SurveyUser insertToObj(SurveyUserInsert surveyUserInsert) {
        if (surveyUserInsert == null) {
            return null;
        }
        SurveyUser surveyUser = new SurveyUser();
        BeanUtils.copyProperties(surveyUserInsert, surveyUser);
        return surveyUser;
    }
}
