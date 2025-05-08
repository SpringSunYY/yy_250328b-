package com.lz.manage.model.statics.vo;

import java.util.List;

@lombok.Data
public class PieStaticVo {
    private List<String> names;

    private List<Data> datas;

    @lombok.Data
    public static class Data {
        private String name;
        private Long value;
    }
}
