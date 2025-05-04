package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ResearchSurvey;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 调研信息Mapper接口
 * 
 * @author YY
 * @date 2025-05-04
 */
public interface ResearchSurveyMapper extends BaseMapper<ResearchSurvey>
{
    /**
     * 查询调研信息
     * 
     * @param id 调研信息主键
     * @return 调研信息
     */
    public ResearchSurvey selectResearchSurveyById(Long id);

    /**
     * 查询调研信息列表
     * 
     * @param researchSurvey 调研信息
     * @return 调研信息集合
     */
    public List<ResearchSurvey> selectResearchSurveyList(ResearchSurvey researchSurvey);

    /**
     * 新增调研信息
     * 
     * @param researchSurvey 调研信息
     * @return 结果
     */
    public int insertResearchSurvey(ResearchSurvey researchSurvey);

    /**
     * 修改调研信息
     * 
     * @param researchSurvey 调研信息
     * @return 结果
     */
    public int updateResearchSurvey(ResearchSurvey researchSurvey);

    /**
     * 删除调研信息
     * 
     * @param id 调研信息主键
     * @return 结果
     */
    public int deleteResearchSurveyById(Long id);

    /**
     * 批量删除调研信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResearchSurveyByIds(Long[] ids);
}
