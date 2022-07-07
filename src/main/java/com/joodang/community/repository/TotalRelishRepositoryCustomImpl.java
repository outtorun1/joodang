package com.joodang.community.repository;

import com.joodang.community.dto.QTotalRelishDto;
import com.joodang.community.dto.RelishSearchDto;
import com.joodang.community.dto.TotalRelishDto;
import com.joodang.community.entity.QRelish;
import com.joodang.community.entity.QRelishImg;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class TotalRelishRepositoryCustomImpl implements TotalRelishRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public TotalRelishRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<TotalRelishDto> getTotalRelishPage(RelishSearchDto relishSearchDto, Pageable pageable) {
        QRelish relish = QRelish.relish;
        QRelishImg relishImg = QRelishImg.relishImg;

        QueryResults<TotalRelishDto> results = queryFactory
                .select(
                        new QTotalRelishDto(
                                relish.id,
                                relish.reName,
                                relishImg.reImgUrl,
                                relish.relishCategorys.stringValue()
                        )
                )
                .from(relishImg)
                .join(relishImg.relish, relish)
                .where(relishImg.reRepImgYn.eq("Y"))
                .orderBy(relish.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<TotalRelishDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
