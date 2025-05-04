package com.lz.manage.model.vo.surveyAnswer;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.SurveyAnswer;
/**
 * 用户填写的完整问卷记录Vo对象 tb_survey_answer
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class SurveyAnswerVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 答卷 */
    @Excel(name = "答卷")
    private Long surveyId;

    /** 题目 */
    @Excel(name = "题目")
    private Long questionId;

    /** 题目类型 */
    @Excel(name = "题目类型")
    private String questionType;

    /** 答案 */
    @Excel(name = "答案")
    private String answer;

    /** 用户 */
    @Excel(name = "用户")
    private Long userId;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

    /** 题目顺序 */
    @Excel(name = "题目顺序")
    private Long questionOrder;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 提交状态 */
    @Excel(name = "提交状态")
    private String submitStatus;


     /**
     * 对象转封装类
     *
     * @param surveyAnswer SurveyAnswer实体对象
     * @return SurveyAnswerVo
     */
    public static SurveyAnswerVo objToVo(SurveyAnswer surveyAnswer) {
        if (surveyAnswer == null) {
            return null;
        }
        SurveyAnswerVo surveyAnswerVo = new SurveyAnswerVo();
        BeanUtils.copyProperties(surveyAnswer, surveyAnswerVo);
        return surveyAnswerVo;
    }
}
