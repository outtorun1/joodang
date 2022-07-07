package com.joodang.service.faq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// FAQ

@Controller
public class FaqController {
    @GetMapping(value = "/faq")
    public String Faq(){
        return "service/faq";
    }
}