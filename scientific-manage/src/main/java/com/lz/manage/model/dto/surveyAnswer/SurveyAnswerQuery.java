package com.lz.manage.model.dto.surveyAnswer;

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
import com.lz.manage.model.domain.SurveyAnswer;
/**
 * 用户填写的完整问卷记录Query对象 tb_survey_answer
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyAnswerQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 答卷 */
    private Long surveyId;

    /** 题目 */
    private Long questionId;

    private Long surveyUserId;


    /** 题目类型 */
    private String questionType;

    /** 用户 */
    private Long userId;

    /** 部门 */
    private Long deptId;

    /** 题目顺序 */
    private Long questionOrder;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date submitTime;

    /** 提交状态 */
    private String submitStatus;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param surveyAnswerQuery 查询对象
     * @return SurveyAnswer
     */
    public static SurveyAnswer queryToObj(SurveyAnswerQuery surveyAnswerQuery) {
        if (surveyAnswerQuery == null) {
            return null;
        }
        SurveyAnswer surveyAnswer = new SurveyAnswer();
        BeanUtils.copyProperties(surveyAnswerQuery, surveyAnswer);
        return surveyAnswer;
    }
}
