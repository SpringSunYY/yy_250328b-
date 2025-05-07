package com.lz.manage.model.vo.surveyUser;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.SurveyUser;

/**
 * 用户调研Vo对象 tb_survey_user
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private Long id;

    /**
     * 答卷
     */
    @Excel(name = "答卷")
    private String surveyName;
    private Long surveyId;

    /**
     * 用户
     */
    @Excel(name = "用户")
    private String userName;
    private Long userId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /**
     * 部门
     */
    @Excel(name = "部门")
    private String deptName;
    private Long deptId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createBy;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;


    /**
     * 对象转封装类
     *
     * @param surveyUser SurveyUser实体对象
     * @return SurveyUserVo
     */
    public static SurveyUserVo objToVo(SurveyUser surveyUser) {
        if (surveyUser == null) {
            return null;
        }
        SurveyUserVo surveyUserVo = new SurveyUserVo();
        BeanUtils.copyProperties(surveyUser, surveyUserVo);
        return surveyUserVo;
    }
}
