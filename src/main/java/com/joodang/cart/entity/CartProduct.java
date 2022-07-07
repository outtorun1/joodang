package com.joodang.cart.entity;

import com.joodang.member.entity.BaseEntity;
import com.joodang.product.entity.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart_product")
@Getter
@Setter
@ToString
public class CartProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int count; // 주문 수량

    public static CartProduct createCartProduct(Cart cart, Product product, int count){
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setCount(count);
        return cartProduct;
    }

    public void addCount(int count){
        this.count += count;
    }

    // 장바구니 내역 보기에서 사용자가 상품 수량을 변경할 때 사용되는 메소드
    public void updateCount(int count){
        this.count = count; // 기존 수량 무시하고 덮어 쓰겠습니다.
    }
}