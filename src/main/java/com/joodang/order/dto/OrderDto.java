package com.joodang.order.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderDto {
    // 상품 id
    @NotNull(message = "상품 아이디는 필수 입력 사항입니다.")
    private Long productId ;

    // 주문 수량
    private final int MAX_VALUE = 999 ;

    @Min(value = 1, message = "최소 주문 수량은 1개입니다.")
    @Max(value = MAX_VALUE, message = "최대 주문 수량은 " + MAX_VALUE + "개입니다.")
    private int count ;
}