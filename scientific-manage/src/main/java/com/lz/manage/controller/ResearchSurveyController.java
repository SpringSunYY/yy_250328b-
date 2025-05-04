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
import com.lz.manage.model.domain.ResearchSurvey;
import com.lz.manage.model.vo.researchSurvey.ResearchSurveyVo;
import com.lz.manage.model.dto.researchSurvey.ResearchSurveyQuery;
import com.lz.manage.model.dto.researchSurvey.ResearchSurveyInsert;
import com.lz.manage.model.dto.researchSurvey.ResearchSurveyEdit;
import com.lz.manage.service.IResearchSurveyService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 调研信息Controller
 *
 * @author YY
 * @date 2025-05-04
 */
@RestController
@RequestMapping("/manage/researchSurvey")
public class ResearchSurveyController extends BaseController
{
    @Resource
    private IResearchSurveyService researchSurveyService;

    /**
     * 查询调研信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:researchSurvey:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResearchSurveyQuery researchSurveyQuery)
    {
        ResearchSurvey researchSurvey = ResearchSurveyQuery.queryToObj(researchSurveyQuery);
        startPage();
        List<ResearchSurvey> list = researchSurveyService.selectResearchSurveyList(researchSurvey);
        List<ResearchSurveyVo> listVo= list.stream().map(ResearchSurveyVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出调研信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:researchSurvey:export')")
    @Log(title = "调研信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResearchSurveyQuery researchSurveyQuery)
    {
        ResearchSurvey researchSurvey = ResearchSurveyQuery.queryToObj(researchSurveyQuery);
        List<ResearchSurvey> list = researchSurveyService.selectResearchSurveyList(researchSurvey);
        ExcelUtil<ResearchSurvey> util = new ExcelUtil<ResearchSurvey>(ResearchSurvey.class);
        util.exportExcel(response, list, "调研信息数据");
    }

    /**
     * 获取调研信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:researchSurvey:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ResearchSurvey researchSurvey = researchSurveyService.selectResearchSurveyById(id);
        return success(ResearchSurveyVo.objToVo(researchSurvey));
    }

    /**
     * 新增调研信息
     */
    @PreAuthorize("@ss.hasPermi('manage:researchSurvey:add')")
    @Log(title = "调研信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResearchSurveyInsert researchSurveyInsert)
    {
        ResearchSurvey researchSurvey = ResearchSurveyInsert.insertToObj(researchSurveyInsert);
        return toAjax(researchSurveyService.insertResearchSurvey(researchSurvey));
    }

    /**
     * 修改调研信息
     */
    @PreAuthorize("@ss.hasPermi('manage:researchSurvey:edit')")
    @Log(title = "调研信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResearchSurveyEdit researchSurveyEdit)
    {
        ResearchSurvey researchSurvey = ResearchSurveyEdit.editToObj(researchSurveyEdit);
        return toAjax(researchSurveyService.updateResearchSurvey(researchSurvey));
    }

    /**
     * 删除调研信息
     */
    @PreAuthorize("@ss.hasPermi('manage:researchSurvey:remove')")
    @Log(title = "调研信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(researchSurveyService.deleteResearchSurveyByIds(ids));
    }
}
