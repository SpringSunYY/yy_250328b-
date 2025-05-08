package com.lz.manage.model.dto.surveyAnswer;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.SurveyAnswer;

/**
 * 用户填写的完整问卷记录Vo对象 tb_survey_answer
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyAnswerEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 答卷
     */
    private Long surveyId;

    /**
     * 题目
     */
    private Long questionId;

    private Long surveyUserId;


    /**
     * 题目类型
     */
    private String questionType;

    /**
     * 答案
     */
    private String answer;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 部门
     */
    private Long deptId;

    /**
     * 题目顺序
     */
    private Long questionOrder;

    /**
     * 提交状态
     */
    private String submitStatus;

    /**
     * 对象转封装类
     *
     * @param surveyAnswerEdit 编辑对象
     * @return SurveyAnswer
     */
    public static SurveyAnswer editToObj(SurveyAnswerEdit surveyAnswerEdit) {
        if (surveyAnswerEdit == null) {
            return null;
        }
        SurveyAnswer surveyAnswer = new SurveyAnswer();
        BeanUtils.copyProperties(surveyAnswerEdit, surveyAnswer);
        return surveyAnswer;
    }
}
