package com.joodang.product.dto;

import com.joodang.product.constant.ProductCategorys;
import com.joodang.product.constant.ProductSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductSearchDto {
    private String searchDateType; // 조회 기간 범위
    private ProductSellStatus searchSellStatus; // 판매중 / 품절
    private ProductCategorys productCategorys;
    private String searchBy ; // 검색 필드(상품 이름 또는 등록자 아이디)
    private String searchQuery = ""; // 검색 키워드
}