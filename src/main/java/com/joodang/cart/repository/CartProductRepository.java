package com.joodang.cart.repository;

import com.joodang.cart.dto.CartDetailDto;
import com.joodang.cart.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    // 카트 정보와 상품 정보를 이용하여 특정 장바구니에 상품을 조회
    CartProduct findByCartIdAndProductId(Long cartId, Long productId);

    // 특정 카트 안에 들어 있는 카트 상품 목록들을 조회하되, 상품 이미지는 대표 이미지만 조회
    @Query(" select new com.joodang.cart.dto.CartDetailDto(cp.id, p.productNm, p.price, cp.count, pi.imgUrl)" +
            " from CartProduct cp, ProductImg pi " +
            " join cp.product p" +
            " where cp.cart.id = :cartId " +
            " and pi.product.id = cp.product.id " +
            " and pi.repImgYn = 'Y' " +
            " order by cp.regTime desc " )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}