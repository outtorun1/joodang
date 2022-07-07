package com.joodang.service.OneByOne.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.joodang.service.OneByOne.Entity.OneByOne;
import com.joodang.service.OneByOne.Repository.OneByOneRepository;

@Service
public class OneByOneService {

    @Autowired
    private OneByOneRepository oneByOneRepository;

    // 게시판 글 등록 처리
    public void  oneByOneWrite (OneByOne oneByOne){
        oneByOneRepository.save(oneByOne);
    }

    // 게시판 글 목록 보기 처리
    public Page<OneByOne> oneByOneList(Pageable pageable){
        return oneByOneRepository.findAll(pageable);
    }

    // 특정 게시글 불러오기
    public OneByOne oneByOneDetail(Long no){
        return oneByOneRepository.findById(no).get();
    }

    // 특정 게시글 삭제
    public void oneByOneDelete (Long no){
        oneByOneRepository.deleteById(no);
    }

    // 검색 기능
    public Page<OneByOne> oneByOneSearchList(String searchKeyword, Pageable pageable){
        return oneByOneRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 비밀글 설정
    public OneByOne oneByOneSecretPass(Long no){
        return oneByOneRepository.findById(no).get();
    }
}
