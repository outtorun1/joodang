package com.joodang.community.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "relish_img")
@Getter @Setter
public class RelishImg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reImgName; // UUID 형식으로 업로드된 이미지 파일 이름
    private String reOriImgName; // 이미지 원본 이름
    private String reImgUrl;
    private String reRepImgYn; // 대표 이미지는 값이 Y

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relish_id")
    private Relish relish;

    // 이미지 정보를 업데이트 해주는 메소드
    public void updateRelishImg(String reOriImgName, String reImgName, String reImgUrl){
        this.reOriImgName = reOriImgName;
        this.reImgName = reImgName;
        this.reImgUrl = reImgUrl;
    }
}
