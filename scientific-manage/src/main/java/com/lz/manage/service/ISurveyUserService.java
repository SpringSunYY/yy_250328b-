package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.SurveyUser;
import com.lz.manage.model.vo.surveyUser.SurveyUserVo;
import com.lz.manage.model.dto.surveyUser.SurveyUserQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 用户调研Service接口
 * 
 * @author YY
 * @date 2025-05-04
 */
public interface ISurveyUserService extends IService<SurveyUser>
{
    //region mybatis代码
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
     * 批量删除用户调研
     * 
     * @param ids 需要删除的用户调研主键集合
     * @return 结果
     */
    public int deleteSurveyUserByIds(Long[] ids);

    /**
     * 删除用户调研信息
     * 
     * @param id 用户调研主键
     * @return 结果
     */
    public int deleteSurveyUserById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param surveyUserQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<SurveyUser> getQueryWrapper(SurveyUserQuery surveyUserQuery);

    /**
     * 转换vo
     *
     * @param surveyUserList SurveyUser集合
     * @return SurveyUserVO集合
     */
    List<SurveyUserVo> convertVoList(List<SurveyUser> surveyUserList);
}
