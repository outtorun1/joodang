package com.joodang.product.entity;

import com.joodang.member.entity.BaseEntity;
import com.joodang.product.constant.ProductCategorys;
import com.joodang.product.constant.ProductSellStatus;
import com.joodang.product.dto.ProductFormDto;
import com.joodang.product.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
public class Product extends BaseEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // 상품 코드(기본 키역할)

    @Column(nullable = false, length = 30)
    private String productNm; // 상품 이름

    @Column(nullable = false)
    private Integer alcohol; // 도수

    @Column(nullable = false)
    private int ml; // 용량

    @Lob // CLOB(Character Large OBject), BLOB(Binary Large OBject)
    @Column(nullable = false)
    private String productDetail ; // 상품 상세 설명

    @Column(nullable = false, length = 100)
    private String brewage; // 양조장

    @Column(nullable = false, name = "price")
    private int price; // 판매 가격

    @Column(nullable = false)
    private int stockNumber; // 재고

    @Enumerated(EnumType.STRING)
    private ProductSellStatus productSellStatus; // 상품 판매 상태

    @Enumerated(EnumType.STRING)
    private ProductCategorys productCategorys; // 상품 카테고리

    // 상품에 대한 정보를 업데이트 해주는 메소드
    public void updateProduct(ProductFormDto productFormDto){
        // productFormDto는 화면에서 넘겨 주는 수정될 dto 객체 정보
        // 모든 변수들의 내용을 Entity 변수에 저장시킴
        this.productNm = productFormDto.getProductNm();
        this.alcohol = Integer.valueOf(productFormDto.getAlcohol());
        this.ml = productFormDto.getMl();
        this.productDetail = productFormDto.getProductDetail();
        this.brewage = productFormDto.getBrewage();
        this.price = productFormDto.getPrice();
        this.stockNumber = productFormDto.getStockNumber();
        this.productSellStatus = productFormDto.getProductSellStatus();
        this.productCategorys = productFormDto.getProductCategorys();
    }

    // 상품 주문시 재고 수량을 감소해주는 메소드입니다.
    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber ;

        if(restStock<0){ // 재고 부족시 예외 발생 시키기
            String message = "상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockNumber + ")" ;
            throw new OutOfStockException(message);
        }

        // 재고가 충분하므로 주문된 후 남은 재고량으로 갱신
        this.stockNumber = restStock ;
    }

    // 주문 취소시 해당 상품의 재고 수량을 증가시킵니다.
    public void addStock(int stockNumber){
        this.stockNumber += stockNumber ;
    }
}