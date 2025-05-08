package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.manage.model.dto.surveyAnswer.SurveyAnswerRequest;
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
import com.lz.manage.model.domain.SurveyAnswer;
import com.lz.manage.model.vo.surveyAnswer.SurveyAnswerVo;
import com.lz.manage.model.dto.surveyAnswer.SurveyAnswerQuery;
import com.lz.manage.model.dto.surveyAnswer.SurveyAnswerInsert;
import com.lz.manage.model.dto.surveyAnswer.SurveyAnswerEdit;
import com.lz.manage.service.ISurveyAnswerService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 用户问卷记录Controller
 *
 * @author YY
 * @date 2025-05-04
 */
@RestController
@RequestMapping("/manage/surveyAnswer")
public class SurveyAnswerController extends BaseController {
    @Resource
    private ISurveyAnswerService surveyAnswerService;

    /**
     * 查询用户问卷记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyAnswer:list')")
    @GetMapping("/list")
    public TableDataInfo list(SurveyAnswerQuery surveyAnswerQuery) {
        SurveyAnswer surveyAnswer = SurveyAnswerQuery.queryToObj(surveyAnswerQuery);
        startPage();
        List<SurveyAnswer> list = surveyAnswerService.selectSurveyAnswerList(surveyAnswer);
        List<SurveyAnswerVo> listVo = list.stream().map(SurveyAnswerVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出用户问卷记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyAnswer:export')")
    @Log(title = "用户问卷记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SurveyAnswerQuery surveyAnswerQuery) {
        SurveyAnswer surveyAnswer = SurveyAnswerQuery.queryToObj(surveyAnswerQuery);
        List<SurveyAnswer> list = surveyAnswerService.selectSurveyAnswerList(surveyAnswer);
        ExcelUtil<SurveyAnswer> util = new ExcelUtil<SurveyAnswer>(SurveyAnswer.class);
        util.exportExcel(response, list, "用户问卷记录数据");
    }

    /**
     * 获取用户问卷记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyAnswer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        SurveyAnswer surveyAnswer = surveyAnswerService.selectSurveyAnswerById(id);
        return success(SurveyAnswerVo.objToVo(surveyAnswer));
    }

    /**
     * 新增用户问卷记录
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyAnswer:add')")
    @Log(title = "用户问卷记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SurveyAnswerInsert surveyAnswerInsert) {
        SurveyAnswer surveyAnswer = SurveyAnswerInsert.insertToObj(surveyAnswerInsert);
        return toAjax(surveyAnswerService.insertSurveyAnswer(surveyAnswer));
    }

    @PreAuthorize("@ss.hasPermi('manage:surveyAnswer:add')")
    @Log(title = "用户问卷记录", businessType = BusinessType.INSERT)
    @PostMapping("/add/batch")
    public AjaxResult addByAnswerList(@RequestBody SurveyAnswerRequest surveyAnswerRequest) {
        return toAjax(surveyAnswerService.insertSurveyAnswerList(surveyAnswerRequest));
    }

    /**
     * 修改用户问卷记录
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyAnswer:edit')")
    @Log(title = "用户问卷记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SurveyAnswerEdit surveyAnswerEdit) {
        SurveyAnswer surveyAnswer = SurveyAnswerEdit.editToObj(surveyAnswerEdit);
        return toAjax(surveyAnswerService.updateSurveyAnswer(surveyAnswer));
    }

    /**
     * 删除用户问卷记录
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyAnswer:remove')")
    @Log(title = "用户问卷记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(surveyAnswerService.deleteSurveyAnswerByIds(ids));
    }
}
