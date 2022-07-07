package com.joodang.product.controller;

import com.joodang.product.dto.ProductFormDto;
import com.joodang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;

@Controller
@RequiredArgsConstructor
public class ProductDtlController {

    private final ProductService productService;

    @GetMapping(value = "/HQ/product/{productId}")
    public String productDtl(@PathVariable("productId") Long productId, Model model){
        try {
            ProductFormDto productFormDto = productService.getProductDtl(productId);
            model.addAttribute("productFormDto", productFormDto);

        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재 하지 않는 상품입니다.");
            model.addAttribute("productFormDto", new ProductFormDto());
        }
        return "product/productForm";
    }

    @GetMapping(value = "/product/{productId}")
    public String productDtl(Model model, @PathVariable("productId") Long productId){
        ProductFormDto productFormDto = productService.getProductDtl(productId);
        model.addAttribute("product", productFormDto);
        return "product/productDtl";
    }
}