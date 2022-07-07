package com.joodang.product.dto;

import com.joodang.product.entity.ProductImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ProductImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    // mapper 객체 생성
    private static ModelMapper modelMapper = new ModelMapper();

    public static ProductImgDto of(ProductImg productImg){
        // 상품의 이미지 정보를 이용하여 상품 dto 객체로 변환
        return modelMapper.map(productImg, ProductImgDto.class);
    }
}