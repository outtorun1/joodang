package com.joodang.community.service;

import com.joodang.common.FileService;
import com.joodang.community.entity.RelishImg;
import com.joodang.community.repository.RelishImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class RelishImgService {
    @Value("${relishImgLocation}")
    private String relishImgLocation; // 안주 이미지가 업로드 되는 경로

    private final RelishImgRepository relishImgRepository;

    private final FileService fileService;

    private String savedImagePath = "/images/relishImg/";

    // 안주에 대한 이미지 정보를 저장
    public void saveRelishImg(RelishImg relishImg, MultipartFile relishImgFile) throws Exception{
        // 업로드 했던 이미지의 원본 파일 이름
        String reOriImgName = relishImgFile.getOriginalFilename();
        // uuid 형식의 저장된 이미지 이름
        String reImgName = "";
        // 안주 이미지 불러오는 경로
        String reImgUrl = "";

        if (!StringUtils.isEmpty(reOriImgName)){
            reImgName = fileService.uploadFile(relishImgLocation, reOriImgName, relishImgFile.getBytes());
            reImgUrl = savedImagePath + reImgName;
        }

        // 안주 이미지 정보를 저장
        relishImg.updateRelishImg(reOriImgName, reImgName, reImgUrl);

        relishImgRepository.save(relishImg);
    }


    public void updateRelishImg(Long relishImgId, MultipartFile relishImgFile) throws Exception{
        if(!relishImgFile.isEmpty()){ // 업로드할 파일이 있으면
            RelishImg savedRelishImg = relishImgRepository.findById(relishImgId)
                    .orElseThrow(EntityNotFoundException::new) ;

            // 기존에 등록했던 옛날 이미지는 삭제 합니다.
            if(!StringUtils.isEmpty(savedRelishImg.getReImgName())){
                fileService.deleteFile(relishImgLocation + "/" + savedRelishImg.getReImgName());
            }

            String reOriImgName = relishImgFile.getOriginalFilename() ;

            String reImgName = fileService.uploadFile(relishImgLocation, reOriImgName, relishImgFile.getBytes());
            String reImgUrl = savedImagePath +  reImgName;

            // 안주 이미지 파일을 업로드합니다.
            savedRelishImg.updateRelishImg(reOriImgName, reImgName, reImgUrl);
        }
    }
}
