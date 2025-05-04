package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.SurveyUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户调研Mapper接口
 * 
 * @author YY
 * @date 2025-05-04
 */
public interface SurveyUserMapper extends BaseMapper<SurveyUser>
{
    /**
     * 查询用户调研
     * 
     * @param id 用户调研主键
     * @return 用户调研
     */
    public SurveyUser selectSurveyUserById(Long id);

    /**
     * 查询用户调研列表
     * 
     * @param surveyUser 用户调研
     * @return 用户调研集合
     */
    public List<SurveyUser> selectSurveyUserList(SurveyUser surveyUser);

    /**
     * 新增用户调研
     * 
     * @param surveyUser 用户调研
     * @return 结果
     */
    public int insertSurveyUser(SurveyUser surveyUser);

    /**
     * 修改用户调研
     * 
     * @param surveyUser 用户调研
     * @return 结果
     */
    public int updateSurveyUser(SurveyUser surveyUser);

    /**
     * 删除用户调研
     * 
     * @param id 用户调研主键
     * @return 结果
     */
    public int deleteSurveyUserById(Long id);

    /**
     * 批量删除用户调研
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSurveyUserByIds(Long[] ids);
}
