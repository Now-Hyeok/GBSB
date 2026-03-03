package com.gbsb.api.entity.enums;

import lombok.Getter;

@Getter
public enum CompanyName {
    BAE_MIN("Woowa Bros", "배달의민족"),
    KAKAO("Kakao", "카카오"),
    TOSS("Toss", "토스"),
    NAVER("Naver", "네이버"),
    LINE("Line", "라인"),
    COUPANG("Coupang", "쿠팡"),
    KARROT("Karrot", "당근마켓"),
    YANOLJA("Yanolja", "야놀자");

    private final String name;
    private final String nameKo;

    CompanyName(String name, String nameKo) {
        this.name = name;
        this.nameKo = nameKo;
    }
}
