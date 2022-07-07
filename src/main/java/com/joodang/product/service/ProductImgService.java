package com.joodang.product.service;

import com.joodang.common.FileService;
import com.joodang.product.entity.ProductImg;
import com.joodang.product.repository.ProductImgRepository;
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
public class ProductImgService {
    @Value("${productImgLocation}")
    private String productImgLocation; // 상품 이미지가 업로드 되는 경로

    private final ProductImgRepository productImgRepository;
    private final FileService fileService;

    private String savedImagePath = "/images/productImg/";

    // 상품에 대한 이미지 정보를 저장
    public void saveProductImg(ProductImg productImg, MultipartFile productImgFile) throws Exception{
        // 업로드 했던 이미지의 원본 파일 이름
        String oriImgName = productImgFile.getOriginalFilename();
        // uuid 형식의 저장된 이미지 이름
        String imgName = "";
        // 상품 이미지 불러 오는 경로
        String imgUrl = "";

        // 원본 파일 이름이 있으면 업로드
        if (!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(productImgLocation, oriImgName, productImgFile.getBytes());
            imgUrl = savedImagePath + imgName;
        }

        // 상품 이미지 정보를 저장
        productImg.updateProductImg(oriImgName, imgName, imgUrl);

        productImgRepository.save(productImg);
    }

    // 이미지 업데이트
    public void updateProductImg(Long productImgId, MultipartFile productImgFile) throws Exception{
        // 업로드할 파일이 존재하면
        if (!productImgFile.isEmpty()){
            ProductImg savedProductImg = productImgRepository.findById(productImgId)
                    .orElseThrow(EntityNotFoundException::new);

            // 기존에 등록했던 이미지 삭제
            if (!StringUtils.isEmpty(savedProductImg.getImgName())){
                fileService.deleteFile(productImgLocation + "/" + savedProductImg.getImgName());
            }

            String oriImgName = productImgFile.getOriginalFilename();

            String imgName = fileService.uploadFile(productImgLocation, oriImgName, productImgFile.getBytes());
            String imgUrl = savedImagePath + imgName;

            // 상품 이미지 파일을 업로드
            savedProductImg.updateProductImg(oriImgName, imgName, imgUrl);
        }
    }
}