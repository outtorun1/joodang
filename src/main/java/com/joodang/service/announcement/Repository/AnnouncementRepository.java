package com.joodang.service.announcement.Repository;


import com.joodang.service.announcement.Entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Page<Announcement> findByTitleContaining(String searchKeyword, Pageable pageable);
}
