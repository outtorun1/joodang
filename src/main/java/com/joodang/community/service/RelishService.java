package com.joodang.community.service;

import com.joodang.community.dto.RelishFormDto;
import com.joodang.community.dto.RelishImgDto;
import com.joodang.community.dto.RelishSearchDto;
import com.joodang.community.dto.TotalRelishDto;
import com.joodang.community.entity.Relish;
import com.joodang.community.entity.RelishImg;
import com.joodang.community.repository.RelishImgRepository;
import com.joodang.community.repository.RelishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RelishService {

    private final RelishRepository relishRepository;

    private final RelishImgService relishImgService;

    public Long saveRelish(RelishFormDto relishFormDto, List<MultipartFile> relishImgFileList) throws Exception{
        // 안주 등록
        // 안주 등록 화면에서 넘어온 데이터를 이용하여 Relish Entity를 생성
        Relish relish = relishFormDto.createRelish();
        relishRepository.save(relish); // 안주 데이터 저장

        // 안주에 들어가는 이미지 등록
        for (int i = 0; i < relishImgFileList.size(); i++){
            RelishImg relishImg = new RelishImg();

            // 해당 안주와 이미지와 연계를 맺어줌
            // 실제 데이터 베이스에 relish_id 컬럼에 동일한 값이 들어감
            relishImg.setRelish(relish);

            // 1번째 이미지는 대표 이미지로 지정
            if (i==0){
                relishImg.setReRepImgYn("Y");
            }else{
                relishImg.setReRepImgYn("N");
            }

            // 안주 이미지 정보를 저장
            relishImgService.saveRelishImg(relishImg, relishImgFileList.get(i));

        } // end for

        return relish.getId();
    }

    private final RelishImgRepository relishImgRepository;

    // 안주 아이디를 이용한 상품 이미지 목록 구해오기
    @Transactional(readOnly = true)
    public RelishFormDto getRelishDtl(Long relishId){
        // 안주 이미지와 관련된 Entity 목록을 조회
        List<RelishImg> relishImgList = relishImgRepository.findByRelishIdOrderByIdAsc(relishId);

        // dto를 저장시킬 리스트 컬렉션
        List<RelishImgDto> relishImgDtoList = new ArrayList<RelishImgDto>();

        // 반복문을 사용해 Entity를 Dto로 변경 시켜 dto 컬렉션에 담기
        for (RelishImg relishImg : relishImgList){
            RelishImgDto relishImgDto = RelishImgDto.of(relishImg);
            relishImgDtoList.add(relishImgDto);
        }

        // 안주 Entity 정보를 구함
        Relish relish = relishRepository.findById(relishId)
                .orElseThrow(EntityNotFoundException::new);
        RelishFormDto relishFormDto = RelishFormDto.of(relish);
        relishFormDto.setRelishImgDtoList(relishImgDtoList);

        return relishFormDto;
    }


    // 화면에서 넘겨진 상품 정보를 업데이트
    public Long updateRelish(RelishFormDto relishFormDto, List<MultipartFile> relishImgFileList) throws Exception{
        Relish relish = relishRepository.findById(relishFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 화면에서 넘어온 dto를 이용하여 product Entity에게 전달
        relish.updateRelish(relishFormDto);

        // 이미지에 대한 id 목록
        List<Long> relishImgIds = relishFormDto.getRelishImgIds();

        // 상품 이미지 정보를 수정
        for (int i = 0; i < relishImgFileList.size(); i++){
            relishImgService.updateRelishImg(relishImgIds.get(i), relishImgFileList.get(i));
        }

        // 수정된 상품의 id를 반환
        return relish.getId();
    }

    // 상품에 들어 있는 특정 상품의 id를 이용하여, 목록에서 상품을 삭제
    public void relishDelete(Long id) throws Exception{
        relishRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TotalRelishDto> getTotalRelishPage(RelishSearchDto relishSearchDto, Pageable pageable){
        return relishRepository.getTotalRelishPage(relishSearchDto, pageable);
    }

}
