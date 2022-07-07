package com.joodang.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TotalRelishDto {

    private Long id;

    private String reName;

    private String reImgUrl;

    private String relishCategorys;

    @QueryProjection
    public TotalRelishDto(Long id, String reName, String reImgUrl, String relishCategorys){
        this.id = id;
        this.reName = reName;
        this.reImgUrl = reImgUrl;
        this.relishCategorys = relishCategorys;
    }
}
