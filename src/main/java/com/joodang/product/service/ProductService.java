package com.joodang.product.service;

import com.joodang.member.entity.Member;
import com.joodang.member.repository.MemberRepository;
import com.joodang.product.dto.ProductFormDto;
import com.joodang.product.dto.ProductImgDto;
import com.joodang.product.dto.ProductSearchDto;
import com.joodang.product.dto.TotalProductDto;
import com.joodang.product.entity.Product;
import com.joodang.product.entity.ProductImg;
import com.joodang.product.repository.ProductImgRepository;
import com.joodang.product.repository.ProductRepository;
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
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImgService productImgService;
    private final MemberRepository memberRepository ;

    public Long saveProduct(ProductFormDto productFormDto, List<MultipartFile> productImgFileList) throws Exception{
        // 상품 등록
        // 상품 등록 화면에서 넘어온 데이터를 이용하여 Product Entity를 생성
        Product product = productFormDto.createProduct();
        productRepository.save(product); // 상품 데이터 저장

        // 상품에 들어 가는 이미지 등록
        for (int i = 0; i < productImgFileList.size(); i++){
            ProductImg productImg = new ProductImg();

            // 해당 상품과 이미지와 연계를 맺어줌
            // 실제 데이터 베이스에 product_id 컬럼에 동일한 값이 들어감
            productImg.setProduct(product);

            // 1번째 이미지는 대표 이미지로 지정
            if (i==0){
                productImg.setRepImgYn("Y");

            }else {
                productImg.setRepImgYn("N");
            }

            // 상품의 이미지 정보를 저장
            productImgService.saveProductImg(productImg, productImgFileList.get(i));

        } // end for
        return product.getId();
    }

    private final ProductImgRepository productImgRepository;

    // 상품 아이디를 이용한 상품 이미지 목록 구해오기
    @Transactional(readOnly = true)
    public ProductFormDto getProductDtl(Long productId){
        // 상품 이미지와 관련된 Entity 목록을 조회
        List<ProductImg> productImgList = productImgRepository.findByProductIdOrderByIdAsc(productId);

        // dto를 저장시킬 리스트 컬렉션
        List<ProductImgDto> productImgDtoList = new ArrayList<ProductImgDto>();

        // 반복문을 사용해 Entity를 Dto로 변경 시켜 dto 컬렉션에 담기
        for (ProductImg productImg : productImgList){
            ProductImgDto productImgDto = ProductImgDto.of(productImg);
            productImgDtoList.add(productImgDto);
        }

        // 상품 Entity 정보를 구함
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        ProductFormDto productFormDto = ProductFormDto.of(product);
        productFormDto.setProductImgDtoList(productImgDtoList);

        return productFormDto;
    }

    // 화면에서 넘겨진 상품 정보를 업데이트
    public Long updateProduct(ProductFormDto productFormDto, List<MultipartFile> productImgFileList) throws Exception{
        Product product = productRepository.findById(productFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 화면에서 넘어온 dto를 이용하여 product Entity에게 전달
        product.updateProduct(productFormDto);

        // 이미지에 대한 id 목록
        List<Long> productImgIds = productFormDto.getProductImgIds();

        // 상품 이미지 정보를 수정
        for (int i = 0; i < productImgFileList.size(); i++){
            productImgService.updateProductImg(productImgIds.get(i), productImgFileList.get(i));
        }
        
        // 수정된 상품의 id를 반환
        return product.getId();
    }

    // 상품에 들어 있는 특정 상품의 id를 이용하여, 목록에서 상품을 삭제
    public void productDelete(Long id) throws Exception{
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TotalProductDto> getTotalProductPage(ProductSearchDto productSearchDto, Pageable pageable){
        return productRepository.getTotalProductPage(productSearchDto, pageable);
    }
}