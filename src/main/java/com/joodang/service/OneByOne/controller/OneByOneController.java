package com.joodang.service.OneByOne.controller;



import com.joodang.service.OneByOne.Entity.OneByOne;
import com.joodang.service.OneByOne.Repository.OneByOneRepository;
import com.joodang.service.OneByOne.dto.OneByOneDto;
import com.joodang.service.OneByOne.service.OneByOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OneByOneController {

    @Autowired
    private OneByOneService oneByOneService;
    private OneByOneRepository oneByOneRepository;

    // 게시글 등록하기
    @GetMapping(value = "/OneByOne/obo_Insert")
    public String Obo_InsertG(OneByOne oneByOne){
        return "service/OneByOne/obo_Insert";
    }

    @PostMapping(value = "/OneByOne/obo_Insert")
    public String Obo_InsertP(OneByOne oneByOne, Model model){
        oneByOneService.oneByOneWrite(oneByOne);

        model.addAttribute("message", "글 작성이 등록되었습니다.");
        model.addAttribute("searchUrl", "obo_List");
        return "service/announcement/anno_message";
    }

    // 게시글 목록 보기
    @GetMapping(value = "/OneByOne/obo_List")
    public String Obo_List(Model model, @PageableDefault(page=0, sort = "no", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword, OneByOneDto oneByOneDto, Long no){

        // 검색 기능
        Page<OneByOne> list = null;
        if(searchKeyword == null){
            list = oneByOneService.oneByOneList(pageable);
        }else {
            list = oneByOneService.oneByOneSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1 ;
        int startPage = Math.max(nowPage -3, 1);
        int endPage = Math.min(nowPage +1, list.getTotalPages());
        if (endPage == 2) {
            endPage = endPage +3;
        }else if(endPage ==3) {
            endPage = endPage +2;
        }else if(endPage ==4) {
            endPage = endPage +1;
        }else if(endPage ==5) {
            endPage = endPage +1;
        }
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "service/OneByOne/obo_List";
    }

    // 게시글 상세 페이지 보기
    @GetMapping(value = "/OneByOne/obo_Detail")
    public String Obo_Detail(Model model, Long no){
        model.addAttribute("obo_Detail", oneByOneService.oneByOneDetail(no));
        return "service/OneByOne/obo_Detail";
    }

//     비밀글 설정
    @GetMapping("/OneByOne/secretPass")
    public String Obo_SecretPass(Model model, Long no, OneByOneDto oneByOneDto){
        model.addAttribute("obo_secretPass", oneByOneService.oneByOneSecretPass(no));
        return "service/OneByOne/obo_secretPass";
    }

    // 게시글 삭제
    @GetMapping("/OneByOne/obo_Delete")
    public String Obo_Delete(Long no, Model model){
        oneByOneService.oneByOneDelete(no);

        model.addAttribute("message", "글 삭제가 완료 되었습니다.");
        model.addAttribute("searchUrl", "obo_List");

        return "service/announcement/anno_message";
    }

    // 게시글 수정
    @GetMapping("/OneByOne/obo_Update/{no}")
    public String Obo_UpdateG(@PathVariable("no") Long  no, Model model){
        model.addAttribute("obo_Detail", oneByOneService.oneByOneDetail(no));
        return "service/OneByOne/obo_Update";
    }

    @PostMapping("/OneByOne/obo_Update/{no}")
    public String Obo_UpdateP(@PathVariable("no") Long no, OneByOne oneByOne, Model model){
        OneByOne obo_Update = oneByOneService.oneByOneDetail(no);
        obo_Update.setNo(oneByOne.getNo());
        obo_Update.setTitle(oneByOne.getTitle());
        obo_Update.setContent(oneByOne.getContent());

        oneByOneService.oneByOneWrite(obo_Update);

        model.addAttribute("message", "글 수정이 완료 되었습니다.");
        model.addAttribute("searchUrl", "/OneByOne/obo_List");

        return "service/announcement/anno_message";
    }

}