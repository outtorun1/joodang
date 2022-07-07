package com.joodang.community.repository;

import com.joodang.community.entity.RelishImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 안주에 대한 이미지 정보를 위한 Repository
public interface RelishImgRepository extends JpaRepository<RelishImg, Long> {
    // 특정 안주와 연관된 안주 이미지를 아이디를 이용하여 오름차순으로 정렬하여 조회
    List<RelishImg> findByRelishIdOrderByIdAsc(Long relishId);

    // 안주 아이디를 이용하여 특정 안주에 대한 대표 이미지를 조회
    // 대표 이미지는 ReRepImgYn 매개 변수에 "Y"가 입력되어야 함
    RelishImg findByRelishIdAndReRepImgYn(Long RelishId, String reRepImgYn);
}
