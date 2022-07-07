package com.joodang.product.repository;

import com.joodang.product.dto.ProductSearchDto;
import com.joodang.product.dto.TotalProductDto;
import com.joodang.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TotalProductRepositoryCustom {

    Page<TotalProductDto> getTotalProductPage(ProductSearchDto productSearchDto, Pageable pageable);
}