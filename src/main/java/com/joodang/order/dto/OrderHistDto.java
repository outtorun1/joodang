package com.joodang.order.dto;

import com.joodang.order.constant.OrderStatus;
import com.joodang.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// 특정 고객의 주문 정보 내역을 담고 있는 클래스
@Getter
@Setter
public class OrderHistDto {
    private Long orderId ; // 주문 아이디
    private String orderDate ; // 주문 일자
    private OrderStatus orderStatus ; // 주문 상태(ORDER, CANCEL)

    // 주문 상품 목록을 저장할 리스트
    // 여기서, OrderProductDto는 상품 1개에 대한 정보입니다.
    private List<OrderProductDto> orderProductDtoList = new ArrayList<OrderProductDto>() ;

    public void addOrderProductDto(OrderProductDto orderProductDto){
        orderProductDtoList.add(orderProductDto);
    }

    public OrderHistDto(Order order){
        this.orderId = order.getId() ;

        String pattern = "yyyy-MM-dd HH:mm" ;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        this.orderDate = order.getOrderDate().format(dateTimeFormatter);

        this.orderStatus = order.getOrderStatus() ;
    }
}