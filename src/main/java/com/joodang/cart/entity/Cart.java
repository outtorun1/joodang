package com.joodang.cart.entity;

import com.joodang.member.entity.BaseEntity;
import com.joodang.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 회원 entity를 참조
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email") // 포린키
    private Member member;

    public static Cart createCart(Member member){
        Cart cart = new Cart();

        // 장바구니는 특정 회원 (일대일 연관 매핑)
        cart.setMember(member);
        return cart;
    }
}