package com.joodang.community.dto;

import com.joodang.community.entity.RelishImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class RelishImgDto {
    private Long id;
    private String reImgName;
    private String reOriImgName;
    private String reImgUrl;
    private String reRepImgYn;

    // mapper 객체 생성
    private static ModelMapper modelMapper = new ModelMapper();

    public static RelishImgDto of(RelishImg relishImg){
        // 안주의 이미지 정보를 이용하여 안주 dto 객체로 변환
        return modelMapper.map(relishImg, RelishImgDto.class);
    }
}
