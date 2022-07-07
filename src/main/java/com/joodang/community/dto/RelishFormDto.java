package com.joodang.community.dto;

import com.joodang.community.constant.RelishCategorys;
import com.joodang.community.entity.Relish;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RelishFormDto {

    private Long id;

    @NotBlank(message = "안주 이름은 필수 입력 값 입니다.")
    private String reName;

    private RelishCategorys relishCategorys;

    // 안주 등록시 첨부할 안주 이미지 정보들을 저장할 리스트 컬렉션
    private List<RelishImgDto> relishImgDtoList = new ArrayList<RelishImgDto>();

    // 안주 등록시 해당 이미지들의 unique id 값을 저장할 리스트 컬렉션
    private List<Long> relishImgIds = new ArrayList<Long>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Relish createRelish(){
        return modelMapper.map(this, Relish.class);
    }

    public static RelishFormDto of(Relish relish){
        return modelMapper.map(relish, RelishFormDto.class);
    }
}
