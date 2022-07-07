package com.joodang.product.entity;

import com.joodang.member.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "product_img")
@Getter
@Setter
public class ProductImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String imgName; // UUID 형식으로 업로드된 이미지 파일 이름
    private String oriImgName; // 이미지 원본 이름
    private String imgUrl;
    private String repImgYn; // 대표 이미지는 값이 Y

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    // 이미지 정보를 업데이트 해주는 메소드
    public void updateProductImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}