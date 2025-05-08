package com.lz.manage.service.impl;

import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.StaticsMapper;
import com.lz.manage.model.statics.ro.StaticRo;
import com.lz.manage.model.statics.vo.BarStaticVo;
import com.lz.manage.model.statics.vo.LineStaticVo;
import com.lz.manage.model.statics.vo.PieStaticVo;
import com.lz.manage.service.IStaticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * StaticsServiceImpl
 *
 * @author YY
 * @version 1.0
 * @project scientific
 * @package com.lz.manage.service.impl
 * @createTime 2025-05-08  22:51
 */
@Service
public class StaticsServiceImpl implements IStaticsService {
    @Resource
    private StaticsMapper staticsMapper;

    @Override
    public Long loginCount() {
        return staticsMapper.loginCount();
    }

    @Override
    public Long userCount() {
        return staticsMapper.userCount();
    }

    @Override
    public LineStaticVo loginCountDay() {
        int day = 10;
        List<StaticRo> staticRoList = staticsMapper.loginCountDay(day);
        if (StringUtils.isEmpty(staticRoList)) {
            return new LineStaticVo();
        }
        LineStaticVo lineStaticVo = new LineStaticVo();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Long> totals = new ArrayList<>();
        for (StaticRo staticRo : staticRoList) {
            names.add(staticRo.getName());
            totals.add(staticRo.getTotal());
        }
        lineStaticVo.setNames(names);
        lineStaticVo.setTotals(totals);
        return lineStaticVo;
    }

    @Override
    public PieStaticVo userSexPie() {
        List<StaticRo> staticRoList = staticsMapper.userSexPie();
        if (StringUtils.isEmpty(staticRoList)) {
            return new PieStaticVo();
        }
        PieStaticVo pieStaticVo = new PieStaticVo();
        ArrayList<PieStaticVo.Data> dates = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (StaticRo staticRo : staticRoList) {
            PieStaticVo.Data date = new PieStaticVo.Data();
            date.setName(staticRo.getName());
            date.setValue(staticRo.getTotal());
            dates.add(date);
            names.add(staticRo.getName());
        }
        pieStaticVo.setDatas(dates);
        pieStaticVo.setNames(names);
        return pieStaticVo;
    }

    @Override
    public BarStaticVo userAge() {
        List<StaticRo> staticRoList = staticsMapper.userAge();
        if (StringUtils.isEmpty(staticRoList)) {
            return new BarStaticVo();
        }
        BarStaticVo barStaticVo = new BarStaticVo();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Long> totals = new ArrayList<>();
        for (StaticRo staticRo : staticRoList) {
            names.add(staticRo.getName());
            totals.add(staticRo.getTotal());
        }
        barStaticVo.setNames(names);
        barStaticVo.setTotals(totals);
        return barStaticVo;
    }
}
