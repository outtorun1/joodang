package com.joodang.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TotalProductDto {
    private Long id;
    private String productNm;
    private String productDetail;
    private String imgUrl;
    private Integer price;
    private String productCategorys;

    @QueryProjection
    public TotalProductDto(Long id, String productNm, String productDetail, String imgUrl, Integer price, String productCategorys){
        this.id = id;
        this.productNm = productNm;
        this.productDetail = productDetail;
        this.imgUrl = imgUrl;
        this.price = price;
        this.productCategorys = productCategorys;
    }
}