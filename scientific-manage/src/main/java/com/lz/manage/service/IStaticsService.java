package com.lz.manage.service;

import com.lz.manage.model.statics.vo.BarStaticVo;
import com.lz.manage.model.statics.vo.LineStaticVo;
import com.lz.manage.model.statics.vo.PieStaticVo;

public interface IStaticsService {
    /**
     * 统计登录总数
     *
     * @param
     * @return java.lang.Long
     * @author YY
     * @method loginCount
     * @date 2025/5/8 22:56
     **/
    Long loginCount();

    /**
     * 用户总数
     *
     * @param
     * @return java.lang.Long
     * @author YY
     * @method userCount
     * @date 2025/5/8 22:59
     **/
    Long userCount();

    /**
     * 近十天登录每日登录总数
     *
     * @param
     * @return com.lz.manage.model.statics.vo.LineStaticVo
     * @author YY
     * @method loginCountDay
     * @date 2025/5/8 23:17
     **/
    LineStaticVo loginCountDay();

    /**
     * @param
     * @return PieStaticVo
     * @author YY
     * @method userSexPie
     * @date 2025/5/8 23:29
     **/
    PieStaticVo userSexPie();

    BarStaticVo userAge();
}
