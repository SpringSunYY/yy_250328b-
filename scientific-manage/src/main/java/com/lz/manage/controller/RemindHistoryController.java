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
import com.lz.manage.model.domain.RemindHistory;
import com.lz.manage.model.vo.remindHistory.RemindHistoryVo;
import com.lz.manage.model.dto.remindHistory.RemindHistoryQuery;
import com.lz.manage.model.dto.remindHistory.RemindHistoryInsert;
import com.lz.manage.model.dto.remindHistory.RemindHistoryEdit;
import com.lz.manage.service.IRemindHistoryService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 提醒记录Controller
 *
 * @author ruoyi
 * @date 2025-05-04
 */
@RestController
@RequestMapping("/manage/remindHistory")
public class RemindHistoryController extends BaseController
{
    @Resource
    private IRemindHistoryService remindHistoryService;

    /**
     * 查询提醒记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:remindHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(RemindHistoryQuery remindHistoryQuery)
    {
        RemindHistory remindHistory = RemindHistoryQuery.queryToObj(remindHistoryQuery);
        startPage();
        List<RemindHistory> list = remindHistoryService.selectRemindHistoryList(remindHistory);
        List<RemindHistoryVo> listVo= list.stream().map(RemindHistoryVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出提醒记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:remindHistory:export')")
    @Log(title = "提醒记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RemindHistoryQuery remindHistoryQuery)
    {
        RemindHistory remindHistory = RemindHistoryQuery.queryToObj(remindHistoryQuery);
        List<RemindHistory> list = remindHistoryService.selectRemindHistoryList(remindHistory);
        ExcelUtil<RemindHistory> util = new ExcelUtil<RemindHistory>(RemindHistory.class);
        util.exportExcel(response, list, "提醒记录数据");
    }

    /**
     * 获取提醒记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:remindHistory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        RemindHistory remindHistory = remindHistoryService.selectRemindHistoryById(id);
        return success(RemindHistoryVo.objToVo(remindHistory));
    }

    /**
     * 新增提醒记录
     */
    @PreAuthorize("@ss.hasPermi('manage:remindHistory:add')")
    @Log(title = "提醒记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RemindHistoryInsert remindHistoryInsert)
    {
        RemindHistory remindHistory = RemindHistoryInsert.insertToObj(remindHistoryInsert);
        return toAjax(remindHistoryService.insertRemindHistory(remindHistory));
    }

    /**
     * 修改提醒记录
     */
    @PreAuthorize("@ss.hasPermi('manage:remindHistory:edit')")
    @Log(title = "提醒记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RemindHistoryEdit remindHistoryEdit)
    {
        RemindHistory remindHistory = RemindHistoryEdit.editToObj(remindHistoryEdit);
        return toAjax(remindHistoryService.updateRemindHistory(remindHistory));
    }

    /**
     * 删除提醒记录
     */
    @PreAuthorize("@ss.hasPermi('manage:remindHistory:remove')")
    @Log(title = "提醒记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(remindHistoryService.deleteRemindHistoryByIds(ids));
    }
}
