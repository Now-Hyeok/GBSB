package com.gbsb.api.entity.enums;

public enum CompanyName {
    BAE_MIN("baemin","배달의 민족"),
    KAKAO("kakao", "카카오"),
    TOSS("toss", "토스");

    CompanyName(String name, String nameKo) {
        this.name = name;
        this.nameKo = nameKo;
    }

    private String name;
    private String nameKo;


    public String getName() {
        return name;
    }

    public String getNameKo() {
        return nameKo;
    }
}
