package com.joodang.community.controller;


import com.joodang.community.service.RelishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class RelishDeleteController {

    private final RelishService relishService;

    @GetMapping("/relishDelete/{id}")
    public String relishDelete(@PathVariable("id") Long id) throws Exception{
        if (id == null){
            return "redirect:/member/login";
        }
        relishService.relishDelete(id);
        return "redirect:/relishPage/totalrelish";
    }
}
