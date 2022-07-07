package com.joodang.community.controller;

import com.joodang.community.dto.RelishFormDto;
import com.joodang.community.service.RelishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RelishUpdateController {

    private final RelishService relishService;

    @PostMapping(value = "/HQ/relish/{relishId}")
    public String relishUpdate(@Valid RelishFormDto relishFormDto, BindingResult bindingResult, @RequestParam("relishImgFile") List<MultipartFile> relishImgFileList, Model model){

        String whenError = "community/reInsertForm";

        if (bindingResult.hasErrors()){
            return whenError;
        }

        if (relishImgFileList.get(0).isEmpty() && relishFormDto.getId() == null){
            model.addAttribute("errorMessage", "안주 이미지는 필수 입력 값입니다.");
            return whenError;
        }

        try {
            relishService.updateRelish(relishFormDto, relishImgFileList);

        }catch (Exception e){
            model.addAttribute("errorMessage", "안주 수정 중에 오류가 발생하였습니다.");
            e.printStackTrace();
            return whenError;
        }

        // 메인 페이지로 이동
        return "redirect:/";
    }
}
