package com.lz.manage.model.vo.researchSurvey;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ResearchSurvey;
/**
 * 调研信息Vo对象 tb_research_survey
 *
 * @author YY
 * @date 2025-05-04
 */
@Data
public class ResearchSurveyVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 调研标题 */
    @Excel(name = "调研标题")
    private String surveyTitle;

    /** 封面 */
    @Excel(name = "封面")
    private String coverUrl;

    /** 调研说明 */
    @Excel(name = "调研说明")
    private String surveyDesc;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

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

    /** 更新人 */
    @Excel(name = "更新人")
    private String updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;


     /**
     * 对象转封装类
     *
     * @param researchSurvey ResearchSurvey实体对象
     * @return ResearchSurveyVo
     */
    public static ResearchSurveyVo objToVo(ResearchSurvey researchSurvey) {
        if (researchSurvey == null) {
            return null;
        }
        ResearchSurveyVo researchSurveyVo = new ResearchSurveyVo();
        BeanUtils.copyProperties(researchSurvey, researchSurveyVo);
        return researchSurveyVo;
    }
}
