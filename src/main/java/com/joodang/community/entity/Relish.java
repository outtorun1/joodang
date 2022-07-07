package com.joodang.community.entity;

import com.joodang.community.constant.RelishCategorys;
import com.joodang.community.dto.RelishFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "relish")
@Getter @Setter @ToString
public class Relish {
    @Id
    @Column(name = "relish_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // 안주 기본 키 역할

    @Column(nullable = false, length = 30)
    private String reName; // 안주 이름

    @Enumerated(EnumType.STRING)
    private RelishCategorys relishCategorys; // 카테고리

    // 안주에 대한 정보를 업데이트 해주는 메소드
    public void updateRelish(RelishFormDto relishFormDto){
        // relishFormDto는 화면에서 넘겨주는 수정될 dto 객체 정보
        // 모든 변수들의 내용을 Entity 변수에 저장시킴
        this.reName = relishFormDto.getReName();
        this.relishCategorys = relishFormDto.getRelishCategorys();
    }
}
