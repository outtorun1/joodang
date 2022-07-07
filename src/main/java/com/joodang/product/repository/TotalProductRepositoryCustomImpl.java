package com.joodang.product.repository;

import com.joodang.product.dto.ProductSearchDto;
import com.joodang.product.dto.QTotalProductDto;
import com.joodang.product.dto.TotalProductDto;
import com.joodang.product.entity.Product;
import com.joodang.product.entity.QProduct;
import com.joodang.product.entity.QProductImg;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class TotalProductRepositoryCustomImpl implements TotalProductRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public TotalProductRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<TotalProductDto> getTotalProductPage(ProductSearchDto productSearchDto, Pageable pageable){
        QProduct product = QProduct.product;
        QProductImg productImg = QProductImg.productImg;

        QueryResults<TotalProductDto> results = queryFactory
                .select(
                        new QTotalProductDto(
                                product.id,
                                product.productNm,
                                product.productDetail,
                                productImg.imgUrl,
                                product.price,
                                product.productCategorys.stringValue()
                        )
                )
                .from(productImg)
                .join(productImg.product, product)
                .where(productImg.repImgYn.eq("Y"))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<TotalProductDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}