package com.lz.manage.model.vo.surveyQuestion;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.SurveyQuestion;
/**
 * 调研题目Vo对象 tb_survey_question
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyQuestionVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 答卷 */
    @Excel(name = "答卷")
    private Long surveyId;

    /** 题目类型 */
    @Excel(name = "题目类型")
    private String questionType;

    /** 题干内容 */
    @Excel(name = "题干内容")
    private String questionTitle;

    /** 题目内容 */
    @Excel(name = "题目内容")
    private String questionOptions;

    /** 题目顺序 */
    @Excel(name = "题目顺序")
    private Long questionOrder;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long userId;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 是否必答 */
    @Excel(name = "是否必答")
    private String isRequired;


     /**
     * 对象转封装类
     *
     * @param surveyQuestion SurveyQuestion实体对象
     * @return SurveyQuestionVo
     */
    public static SurveyQuestionVo objToVo(SurveyQuestion surveyQuestion) {
        if (surveyQuestion == null) {
            return null;
        }
        SurveyQuestionVo surveyQuestionVo = new SurveyQuestionVo();
        BeanUtils.copyProperties(surveyQuestion, surveyQuestionVo);
        return surveyQuestionVo;
    }
}
