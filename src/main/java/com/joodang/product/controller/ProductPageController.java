package com.joodang.product.controller;


import com.joodang.product.dto.ProductSearchDto;
import com.joodang.product.dto.TotalProductDto;
import com.joodang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping(value="/productPage")
public class ProductPageController {

    private final ProductService productService;
    @GetMapping(value = "/totalproduct")
    public String totalproduct(ProductSearchDto productSearchDto, Optional <Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 999);

        Page<TotalProductDto> produts = productService.getTotalProductPage(productSearchDto, pageable) ;

        model.addAttribute("products", produts);

        return "product/product";
    }
    // 기타주류
    @GetMapping(value = "/alcoholic")
    public String alcoholic(ProductSearchDto productSearchDto, Optional <Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 999);

        Page<TotalProductDto> produts = productService.getTotalProductPage(productSearchDto, pageable) ;

        model.addAttribute("products", produts);

        return "product/productAlcoholic";
    }
    // 청주
    @GetMapping(value = "/chengju")
    public String chengju(ProductSearchDto productSearchDto, Optional <Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 999);

        Page<TotalProductDto> produts = productService.getTotalProductPage(productSearchDto, pageable) ;

        model.addAttribute("products", produts);

        return "product/productChengju";
    }
    // 과실주
    @GetMapping(value = "/fruitWine")
    public String fruitWine(ProductSearchDto productSearchDto, Optional <Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 999);

        Page<TotalProductDto> produts = productService.getTotalProductPage(productSearchDto, pageable) ;

        model.addAttribute("products", produts);

        return "product/productFruitWine";
    }
    // 증류주
    @GetMapping(value = "/liquor")
    public String liquor(ProductSearchDto productSearchDto, Optional <Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 999);

        Page<TotalProductDto> produts = productService.getTotalProductPage(productSearchDto, pageable) ;

        model.addAttribute("products", produts);

        return "product/productLiquor";
    }
    // 탁주
    @GetMapping(value = "/riceWine")
    public String riceWine(ProductSearchDto productSearchDto, Optional <Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 999);

        Page<TotalProductDto> produts = productService.getTotalProductPage(productSearchDto, pageable) ;

        model.addAttribute("products", produts);

        return "product/productRiceWine";
    }
}