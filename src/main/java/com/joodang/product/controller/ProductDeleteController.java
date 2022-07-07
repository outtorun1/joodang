package com.joodang.product.controller;

import com.joodang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductDeleteController {
    private final ProductService productService;

    @GetMapping("/productDelete/{id}")
    public String productDelete(@PathVariable("id") Long id) throws Exception{
        if (id == null){
            return "redirect:/member/login";
        }
        productService.productDelete(id);
        return "redirect:/productPage/totalproduct";
    }
}