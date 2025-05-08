package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.SurveyUser;
import com.lz.manage.model.vo.surveyUser.SurveyUserVo;
import com.lz.manage.model.dto.surveyUser.SurveyUserQuery;
import com.lz.manage.model.dto.surveyUser.SurveyUserInsert;
import com.lz.manage.model.dto.surveyUser.SurveyUserEdit;
import com.lz.manage.service.ISurveyUserService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 用户调研Controller
 *
 * @author YY
 * @date 2025-05-04
 */
@RestController
@RequestMapping("/manage/surveyUser")
public class SurveyUserController extends BaseController {
    @Resource
    private ISurveyUserService surveyUserService;

    /**
     * 查询用户调研列表
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(SurveyUserQuery surveyUserQuery) {
        SurveyUser surveyUser = SurveyUserQuery.queryToObj(surveyUserQuery);
        startPage();
        List<SurveyUser> list = surveyUserService.selectSurveyUserList(surveyUser);
        List<SurveyUserVo> listVo = list.stream().map(SurveyUserVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出用户调研列表
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyUser:export')")
    @Log(title = "用户调研", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SurveyUserQuery surveyUserQuery) {
        SurveyUser surveyUser = SurveyUserQuery.queryToObj(surveyUserQuery);
        List<SurveyUser> list = surveyUserService.selectSurveyUserList(surveyUser);
        ExcelUtil<SurveyUser> util = new ExcelUtil<SurveyUser>(SurveyUser.class);
        util.exportExcel(response, list, "用户调研数据");
    }

    /**
     * 获取用户调研详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        SurveyUser surveyUser = surveyUserService.selectSurveyUserById(id);
        return success(SurveyUserVo.objToVo(surveyUser));
    }

    /**
     * 新增用户调研
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyUser:add')")
    @Log(title = "用户调研", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SurveyUserInsert surveyUserInsert) {
        SurveyUser surveyUser = SurveyUserInsert.insertToObj(surveyUserInsert);
        return toAjax(surveyUserService.insertSurveyUser(surveyUser));
    }

    /**
     * 修改用户调研
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyUser:edit')")
    @Log(title = "用户调研", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SurveyUserEdit surveyUserEdit) {
        SurveyUser surveyUser = SurveyUserEdit.editToObj(surveyUserEdit);
        return toAjax(surveyUserService.updateSurveyUser(surveyUser));
    }

    /**
     * 删除用户调研
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyUser:remove')")
    @Log(title = "用户调研", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(surveyUserService.deleteSurveyUserByIds(ids));
    }
}
