package com.joodang.product.controller;

import com.joodang.product.dto.ProductFormDto;
import com.joodang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/productInsert")
public class ProductInsertController {
    @GetMapping(value = "/HQ/product/new")
    public String productForm(Model model){
        // productFormDto : 상품 등록하면 이 객체에 데이터 정보가 들어감
        model.addAttribute("productFormDto", new ProductFormDto());
        return "product/productForm";
    }
    private final ProductService productService;

    @PostMapping(value = "/HQ/product/new")
    public String productNew(@Valid ProductFormDto productFormDto, BindingResult bindingResult, Model model, HttpServletResponse response,
                             @RequestParam("productImgFile")
                             List<MultipartFile> productImgFileList) throws IOException {
        String message = "";
        if (productImgFileList.get(0).isEmpty() && productFormDto.getId() == null){
            model.addAttribute("errorMessage", "이미지는 필수 입력 값입니다.");
            return "product/productForm";
        }
        try{
            productService.saveProduct(productFormDto, productImgFileList);

        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중에 오류가 발생하였습니다.");
            return "product/productForm";
        }

        return "redirect:/"; // 메인 페이지로 이동
    }
}