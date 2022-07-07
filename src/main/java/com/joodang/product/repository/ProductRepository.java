package com.joodang.product.repository;

import com.joodang.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, TotalProductRepositoryCustom{
    // 상품 이름으로 데이터를 조회
    List<Product> findByProductNm(String productNm);

    // 단가가 price보다 적은 상품들을 조회합니다.
    List<Product> findByPriceLessThan(Integer price) ;

    // 단가가 price보다 적은 상품들을 조회하되, 단가가 큰 상품부터 보여 줍니다.
    List<Product> findByPriceLessThanOrderByPriceDesc(Integer price) ;
}