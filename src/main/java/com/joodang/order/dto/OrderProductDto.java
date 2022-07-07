package com.joodang.order.dto;

import com.joodang.order.entity.OrderProduct;
import lombok.Getter;
import lombok.Setter;

// 주문 상품 1개의 정보를 담고 있는 클래스
@Getter @Setter
public class OrderProductDto {
    private String productNm ; // 상품명
    private int count ; // 주문 수량
    private int orderPrice ; // 주문 금액
    private String imgUrl ; // 상품 이미지 경로

    // 주문 상품 정보와 해당 이미지 경로를 입력 받아서 멤버 변수들에 값을 대입합니다.
    public OrderProductDto(OrderProduct orderProduct, String imgUrl){
        this.productNm = orderProduct.getProduct().getProductNm() ;
        this.count = orderProduct.getCount() ;
        this.orderPrice = orderProduct.getOrderPrice() ;
        this.imgUrl = imgUrl ;
    }
}