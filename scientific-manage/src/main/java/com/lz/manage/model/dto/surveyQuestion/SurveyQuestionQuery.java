package com.lz.manage.model.dto.surveyQuestion;

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
import com.lz.manage.model.domain.SurveyQuestion;
/**
 * 调研题目Query对象 tb_survey_question
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyQuestionQuery implements Serializable
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

    /** 题目顺序 */
    private Long questionOrder;

    /** 创建人 */
    private Long userId;

    /** 部门 */
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 是否必答 */
    private String isRequired;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param surveyQuestionQuery 查询对象
     * @return SurveyQuestion
     */
    public static SurveyQuestion queryToObj(SurveyQuestionQuery surveyQuestionQuery) {
        if (surveyQuestionQuery == null) {
            return null;
        }
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        BeanUtils.copyProperties(surveyQuestionQuery, surveyQuestion);
        return surveyQuestion;
    }
}
