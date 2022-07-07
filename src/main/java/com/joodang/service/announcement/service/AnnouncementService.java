package com.joodang.service.announcement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.joodang.service.announcement.Entity.Announcement;
import com.joodang.service.announcement.Repository.AnnouncementRepository;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    // 게시판 글 등록 처리
    public void  announcementWrite (Announcement announcement){
        announcementRepository.save(announcement);
    }

    // 게시판 글 목록 보기 처리
    public Page<Announcement> announcementList(Pageable pageable){
        return announcementRepository.findAll(pageable);
    }

    // 특정 게시글 불러오기
    public Announcement announcementDetail(Long no){
        return announcementRepository.findById(no).get();
    }

    // 특정 게시글 삭제
    public void announcementDelete (Long no){
        announcementRepository.deleteById(no);
    }

    // 검색 기능
    public Page<Announcement> announcementSearchList(String searchKeyword, Pageable pageable){
        return announcementRepository.findByTitleContaining(searchKeyword, pageable);
    }
}