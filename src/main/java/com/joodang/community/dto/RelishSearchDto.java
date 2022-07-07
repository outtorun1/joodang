package com.joodang.community.dto;

import com.joodang.community.constant.RelishCategorys;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RelishSearchDto {
    private String searchDateType; // 조회 기간 범위
    private RelishCategorys relishCategorys;
    private String searchBy; // 검색 필드(안주 이름 또는 등록자 아이디)
    private String searchQuery = ""; // 검색 키워드
}
