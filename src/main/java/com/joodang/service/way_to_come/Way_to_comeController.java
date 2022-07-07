package com.joodang.service.way_to_come;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 오시는길

@Controller
public class Way_to_comeController {
    @GetMapping(value = "/way_to_come")
    public String eat(){
        return "service/way_to_come";
    }
}