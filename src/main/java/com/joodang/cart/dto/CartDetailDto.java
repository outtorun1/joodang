package com.joodang.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 장바구니 조회 페이지에 전달할 dto 클래스
@Getter
@Setter
public class CartDetailDto {
    private Long cartProductId; // 장바구니 상품을 위한 아이디
    private String productNm; // 상품 이름
    private int price; // 상품 금액
    private int count; // 구매하고자 하는 수량
    private String imgUrl; // 상품 이미지 경로

    // 장바구니 조회 페이지에 전달해줄 데이터들을 생성자를 이용하여 주입
    public CartDetailDto(Long cartProductId, String productNm, int price, int count, String imgUrl){
        this.cartProductId = cartProductId;
        this.productNm = productNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}