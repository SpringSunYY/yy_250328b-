package com.lz.manage.controller;

import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.manage.service.IStaticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * StaticsController
 *
 * @author YY
 * @version 1.0
 * @project scientific
 * @package com.lz.manage.controller
 * @createTime 2025-05-08  22:50
 */
@RestController
@RequestMapping("/manage/statics")
public class StaticsController extends BaseController {
    @Resource
    private IStaticsService staticsService;

    /**
     * 登录总数
     */
    @GetMapping("/loginCount")
    public AjaxResult loginCount() {
        return success(staticsService.loginCount());
    }

    @GetMapping("/userCount")
    public AjaxResult userCount() {
        return success(staticsService.userCount());
    }

    @GetMapping("/loginCountDay")
    public AjaxResult loginCountDay() {
        return success(staticsService.loginCountDay());
    }

    @GetMapping("/userSexPie")
    public AjaxResult userSexPie() {
        return success(staticsService.userSexPie());
    }

    @GetMapping("/userAge")
    public AjaxResult userAge() {
        return success(staticsService.userAge());
    }
}
