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
import com.lz.manage.model.domain.SurveyQuestion;
import com.lz.manage.model.vo.surveyQuestion.SurveyQuestionVo;
import com.lz.manage.model.dto.surveyQuestion.SurveyQuestionQuery;
import com.lz.manage.model.dto.surveyQuestion.SurveyQuestionInsert;
import com.lz.manage.model.dto.surveyQuestion.SurveyQuestionEdit;
import com.lz.manage.service.ISurveyQuestionService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 调研题目Controller
 *
 * @author YY
 * @date 2025-05-04
 */
@RestController
@RequestMapping("/manage/surveyQuestion")
public class SurveyQuestionController extends BaseController
{
    @Resource
    private ISurveyQuestionService surveyQuestionService;

    /**
     * 查询调研题目列表
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyQuestion:list')")
    @GetMapping("/list")
    public TableDataInfo list(SurveyQuestionQuery surveyQuestionQuery)
    {
        SurveyQuestion surveyQuestion = SurveyQuestionQuery.queryToObj(surveyQuestionQuery);
        startPage();
        List<SurveyQuestion> list = surveyQuestionService.selectSurveyQuestionList(surveyQuestion);
        List<SurveyQuestionVo> listVo= list.stream().map(SurveyQuestionVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出调研题目列表
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyQuestion:export')")
    @Log(title = "调研题目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SurveyQuestionQuery surveyQuestionQuery)
    {
        SurveyQuestion surveyQuestion = SurveyQuestionQuery.queryToObj(surveyQuestionQuery);
        List<SurveyQuestion> list = surveyQuestionService.selectSurveyQuestionList(surveyQuestion);
        ExcelUtil<SurveyQuestion> util = new ExcelUtil<SurveyQuestion>(SurveyQuestion.class);
        util.exportExcel(response, list, "调研题目数据");
    }

    /**
     * 获取调研题目详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyQuestion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        SurveyQuestion surveyQuestion = surveyQuestionService.selectSurveyQuestionById(id);
        return success(SurveyQuestionVo.objToVo(surveyQuestion));
    }

    /**
     * 新增调研题目
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyQuestion:add')")
    @Log(title = "调研题目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SurveyQuestionInsert surveyQuestionInsert)
    {
        SurveyQuestion surveyQuestion = SurveyQuestionInsert.insertToObj(surveyQuestionInsert);
        return toAjax(surveyQuestionService.insertSurveyQuestion(surveyQuestion));
    }

    /**
     * 修改调研题目
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyQuestion:edit')")
    @Log(title = "调研题目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SurveyQuestionEdit surveyQuestionEdit)
    {
        SurveyQuestion surveyQuestion = SurveyQuestionEdit.editToObj(surveyQuestionEdit);
        return toAjax(surveyQuestionService.updateSurveyQuestion(surveyQuestion));
    }

    /**
     * 删除调研题目
     */
    @PreAuthorize("@ss.hasPermi('manage:surveyQuestion:remove')")
    @Log(title = "调研题目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(surveyQuestionService.deleteSurveyQuestionByIds(ids));
    }
}
