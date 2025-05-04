package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ResearchSurvey;
import com.lz.manage.model.vo.researchSurvey.ResearchSurveyVo;
import com.lz.manage.model.dto.researchSurvey.ResearchSurveyQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 调研信息Service接口
 * 
 * @author YY
 * @date 2025-05-04
 */
public interface IResearchSurveyService extends IService<ResearchSurvey>
{
    //region mybatis代码
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
     * 批量删除调研信息
     * 
     * @param ids 需要删除的调研信息主键集合
     * @return 结果
     */
    public int deleteResearchSurveyByIds(Long[] ids);

    /**
     * 删除调研信息信息
     * 
     * @param id 调研信息主键
     * @return 结果
     */
    public int deleteResearchSurveyById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param researchSurveyQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ResearchSurvey> getQueryWrapper(ResearchSurveyQuery researchSurveyQuery);

    /**
     * 转换vo
     *
     * @param researchSurveyList ResearchSurvey集合
     * @return ResearchSurveyVO集合
     */
    List<ResearchSurveyVo> convertVoList(List<ResearchSurvey> researchSurveyList);
}
