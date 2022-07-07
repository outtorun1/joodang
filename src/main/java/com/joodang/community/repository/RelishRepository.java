package com.joodang.community.repository;

import com.joodang.community.entity.Relish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelishRepository extends JpaRepository<Relish, Long>, TotalRelishRepositoryCustom{
    // 안주 이름으로 데이터를 조회
    List<Relish> findByReName(String reName);

}
