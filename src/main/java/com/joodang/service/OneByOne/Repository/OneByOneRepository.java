package com.joodang.service.OneByOne.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.joodang.service.OneByOne.Entity.OneByOne;

@Repository
public interface OneByOneRepository extends JpaRepository<OneByOne, Long> {
    Page<OneByOne> findByTitleContaining(String searchKeyword, Pageable pageable);
}
