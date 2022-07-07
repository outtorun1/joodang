package com.joodang.order.entity;

import com.joodang.member.entity.BaseEntity;
import com.joodang.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="order_product_id")
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY) // 상품들은 여러 개의 주문상품에 포함될 수 있습니다.
    @JoinColumn(name = "product_id")
    private Product product ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order ;

    private int orderPrice ; // 단가
    private int count ; // 수량
//    private LocalDateTime regTime ;
//    private LocalDateTime updateTime ;

    // 주문할 상품 정보와 주문 수량을 이용하여 OrderProduct 객체를 생성합니다.
    public static OrderProduct createOrderProduct(Product product, int count){
        // orderProduct : 특정 상품에 대하여 주문 수량과 가격 정보를 담고 있는 객체
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setCount(count);
        orderProduct.setOrderPrice(product.getPrice());

        product.removeStock(count); // 재고 수량 감소
        return orderProduct ;
    }

    public int getTotalPrice(){
        return orderPrice * count ; // 금액 = 가격 * 수량
    }

    // 주문 취소시 해당 상품의 재고 수량을 다시 늘려 줍니다.
    public void cancel(){
        this.getProduct().addStock(count);
    }
}
