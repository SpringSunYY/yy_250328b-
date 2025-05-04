package com.lz.manage.model.dto.researchSurvey;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ResearchSurvey;
/**
 * 调研信息Vo对象 tb_research_survey
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class ResearchSurveyEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 调研标题 */
    private String surveyTitle;

    /** 封面 */
    private String coverUrl;

    /** 调研说明 */
    private String surveyDesc;

    /** 状态 */
    private String status;

    /** 创建人 */
    private Long userId;

    /** 部门 */
    private Long deptId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param researchSurveyEdit 编辑对象
     * @return ResearchSurvey
     */
    public static ResearchSurvey editToObj(ResearchSurveyEdit researchSurveyEdit) {
        if (researchSurveyEdit == null) {
            return null;
        }
        ResearchSurvey researchSurvey = new ResearchSurvey();
        BeanUtils.copyProperties(researchSurveyEdit, researchSurvey);
        return researchSurvey;
    }
}
