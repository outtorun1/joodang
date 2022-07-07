package com.joodang.community.controller;

import com.joodang.community.dto.RelishSearchDto;
import com.joodang.community.dto.TotalRelishDto;
import com.joodang.community.service.RelishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/relishPage")
public class RelishController {

    private final RelishService relishService;
    @GetMapping(value = "/totalrelish")
    public String totalrelish(RelishSearchDto relishSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<TotalRelishDto> relishs = relishService.getTotalRelishPage(relishSearchDto, pageable);

        model.addAttribute("relishs", relishs);

        return "community/relish";
    }

    @GetMapping(value = "/reInsert")
    public String reInsert(){
        return "community/reInsertForm";
    }


}
