package com.joodang.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String productNm;
    private Integer alcohol;
    private Integer ml;
    private String productDetail;
    private String brewage;
    private Integer price;
    private String productSellStatus;
    private String productCategorys;
}