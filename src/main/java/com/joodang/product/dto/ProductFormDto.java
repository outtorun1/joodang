package com.joodang.product.dto;

import com.joodang.product.constant.ProductCategorys;
import com.joodang.product.constant.ProductSellStatus;
import com.joodang.product.entity.Product;
import jdk.dynalink.linker.LinkerServices;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductFormDto {
    private Long id;

    @NotBlank(message = "상품이름은 필수 입력 값입니다.")
    private String productNm;

    @NotBlank(message = "도수는 필수 입력 값입니다.")
    private String alcohol;

    @NotNull(message = "용량은 필수 입력 값입니다.")
    private Integer ml;

    @NotBlank(message = "상품설명은 필수 입력 값입니다.")
    private String productDetail;

    @NotBlank(message = "양조장은 필수 입력 값입니다.")
    private String brewage;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price ;

    @NotNull(message = "재고 수량은 필수 입력 값입니다.")
    private Integer stockNumber ;

    private ProductSellStatus productSellStatus;

    private ProductCategorys productCategorys;

    // 상품 등록시 첨부할 상품 이미지 정보들을 저장할 리스트 컬렉션
    private List<ProductImgDto> productImgDtoList = new ArrayList<ProductImgDto>();

    // 상품 수정시 해당 이미지들의 unique id 값을 저장할 리스트 컬렉션
    private List<Long> productImgIds = new ArrayList<Long>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Product createProduct(){
        return modelMapper.map(this, Product.class);
    }

    public static ProductFormDto of(Product product){
        return modelMapper.map(product, ProductFormDto.class);
    }
}