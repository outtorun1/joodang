package com.joodang.community.repository;

import com.joodang.community.dto.RelishSearchDto;
import com.joodang.community.dto.TotalRelishDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TotalRelishRepositoryCustom {
    Page<TotalRelishDto> getTotalRelishPage(RelishSearchDto relishSearchDto, Pageable pageable);
}
