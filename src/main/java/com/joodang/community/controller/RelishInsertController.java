package com.joodang.community.controller;

import com.joodang.community.dto.RelishFormDto;
import com.joodang.community.service.RelishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/relishInsert")
public class RelishInsertController {

    @GetMapping(value = "/HQ/relish/new")
    public String reInsertForm(Model model){
        // relishFormDto : 안주를 등록하면 이 객체에 데이터 정보가 들어감
        model.addAttribute("relishFormDto", new RelishFormDto());
        return "community/reInsertForm";
    }

    private final RelishService relishService;

    @PostMapping(value = "/HQ/relish/new")
    public String relishNew(@Valid RelishFormDto relishFormDto, BindingResult bindingResult, Model model,
                             @RequestParam("relishImgFile")
                                     List<MultipartFile> relishImgFileList){
        if (relishImgFileList.get(0).isEmpty() && relishFormDto.getId() == null){
            model.addAttribute("errorMessage", "이미지는 필수 입력 값입니다.");
            return "community/reInsertForm";
        }
        try{
            relishService.saveRelish(relishFormDto, relishImgFileList);

        }catch (Exception e){
            model.addAttribute("errorMessage", "안주 등록 중에 오류가 발생하였습니다.");
            return "community/reInsertForm";
        }
        return "redirect:/relishPage/totalrelish"; // 메인 페이지로 이동
    }
}
