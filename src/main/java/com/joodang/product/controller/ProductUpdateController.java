package com.joodang.product.controller;

import com.joodang.product.dto.ProductFormDto;
import com.joodang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductUpdateController {
    private final ProductService productService;

    @PostMapping(value = "/HQ/product/{productId}")
    public String productUpdate(@Valid ProductFormDto productFormDto, BindingResult bindingResult, @RequestParam("productImgFile") List<MultipartFile> productImgFileList, Model model){

        String whenError = "product/productForm";

        if (bindingResult.hasErrors()){
            return whenError;
        }

        if (productImgFileList.get(0).isEmpty() && productFormDto.getId() == null){
            model.addAttribute("errorMessage", "상품 이미지는 필수 입력 값입니다.");
            return whenError;
        }

        try {
            productService.updateProduct(productFormDto, productImgFileList);

        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중에 오류가 발생하였습니다.");
            e.printStackTrace();
            return whenError;
        }

        // 메인 페이지로 이동
        return "redirect:/";
    }
}