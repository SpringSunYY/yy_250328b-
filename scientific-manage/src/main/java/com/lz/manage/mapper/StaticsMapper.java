package com.lz.manage.mapper;

import com.lz.manage.model.statics.ro.StaticRo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaticsMapper {
    Long loginCount();

    Long userCount();

    /**
     * @param day 天数
     * @return java.util.List<com.lz.manage.model.statics.ro.StaticRo>
     * @author YY
     * @method loginCountDay
     * @date 2025/5/8 23:24
     **/
    List<StaticRo> loginCountDay(@Param("day") int day);

    List<StaticRo> userSexPie();

    List<StaticRo> userAge();
}
