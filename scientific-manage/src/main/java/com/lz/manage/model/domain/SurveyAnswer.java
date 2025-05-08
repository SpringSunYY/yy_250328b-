package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 用户填写的完整问卷记录对象 tb_survey_answer
 *
 * @author YY
 * @date 2025-05-04
 */
@TableName("tb_survey_answer")
@Data
public class SurveyAnswer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 答卷
     */
    @Excel(name = "答卷")
    @TableField(exist = false)
    private String surveyName;
    private Long surveyId;

    /**
     * 题目
     */
    @Excel(name = "题目")
    @TableField(exist = false)
    private String questionName;
    private Long questionId;

    @Excel(name = "用户调研")
    private Long surveyUserId;

    /**
     * 题目类型
     */
    @Excel(name = "题目类型")
    private String questionType;

    /**
     * 答案
     */
    @Excel(name = "答案")
    private String answer;

    /**
     * 用户
     */
    @Excel(name = "用户")
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 部门
     */
    @Excel(name = "部门")
    @TableField(exist = false)
    private String deptName;
    private Long deptId;

    /**
     * 题目顺序
     */
    @Excel(name = "题目顺序")
    private Long questionOrder;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /**
     * 提交状态
     */
    @Excel(name = "提交状态")
    private String submitStatus;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
