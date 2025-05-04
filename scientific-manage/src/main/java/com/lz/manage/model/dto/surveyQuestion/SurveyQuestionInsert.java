package com.lz.manage.model.dto.surveyQuestion;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.SurveyQuestion;
/**
 * 调研题目Vo对象 tb_survey_question
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyQuestionInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 答卷 */
    private Long surveyId;

    /** 题目类型 */
    private String questionType;

    /** 题干内容 */
    private String questionTitle;

    /** 题目内容 */
    private String questionOptions;

    /** 题目顺序 */
    private Long questionOrder;

    /** 创建人 */
    private Long userId;

    /** 部门 */
    private Long deptId;

    /** 是否必答 */
    private String isRequired;

    /**
     * 对象转封装类
     *
     * @param surveyQuestionInsert 插入对象
     * @return SurveyQuestionInsert
     */
    public static SurveyQuestion insertToObj(SurveyQuestionInsert surveyQuestionInsert) {
        if (surveyQuestionInsert == null) {
            return null;
        }
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        BeanUtils.copyProperties(surveyQuestionInsert, surveyQuestion);
        return surveyQuestion;
    }
}
