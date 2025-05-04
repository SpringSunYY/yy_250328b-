package com.lz.manage.model.dto.researchSurvey;

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
import com.lz.manage.model.domain.ResearchSurvey;
/**
 * 调研信息Query对象 tb_research_survey
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class ResearchSurveyQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 调研标题 */
    private String surveyTitle;

    /** 调研说明 */
    private String surveyDesc;

    /** 状态 */
    private String status;

    /** 创建人 */
    private Long userId;

    /** 部门 */
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param researchSurveyQuery 查询对象
     * @return ResearchSurvey
     */
    public static ResearchSurvey queryToObj(ResearchSurveyQuery researchSurveyQuery) {
        if (researchSurveyQuery == null) {
            return null;
        }
        ResearchSurvey researchSurvey = new ResearchSurvey();
        BeanUtils.copyProperties(researchSurveyQuery, researchSurvey);
        return researchSurvey;
    }
}
