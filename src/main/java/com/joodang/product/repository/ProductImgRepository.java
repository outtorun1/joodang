package com.joodang.product.repository;

import com.joodang.product.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 상품에 대한 이미지 정보를 위한 Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    // 특정 상품과 연관된 상품 이미지를 아이디를 이용하여 오름차순으로 정렬하여 조회
    List<ProductImg> findByProductIdOrderByIdAsc(Long prodctId);

    // 상품 아이디를 이용하여 특정 상품에 대한 대표 이미지를 조회
    // 대표 이미지는 repimgYn 매개 변수에 "Y"가 입력되어야함
    ProductImg findByProductIdAndRepImgYn(Long productId, String repimgYn);
}