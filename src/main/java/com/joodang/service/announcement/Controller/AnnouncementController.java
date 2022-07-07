package com.joodang.service.announcement.Controller;


// 공지사항
import com.joodang.member.mapper.MemberMapperInterface;
import com.joodang.member.service.MemberModelService;
import com.joodang.service.announcement.Entity.Announcement;
import com.joodang.service.announcement.service.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class AnnouncementController {

    private AnnouncementService announcementService;
    private final MemberModelService memberModelService;



    // 게시글 등록하기
    @GetMapping(value = "/announcement/anno_Insert")
    public String Anno_InsertG(Principal principal, Model model){


        String email = principal.getName();

        String mName = memberModelService.SelectOne(email).getM_name();

        model.addAttribute("mName", mName);

        return "service/announcement/anno_Insert";
    }

    @PostMapping(value = "/announcement/anno_Insert")
    public String Anno_InsertP(Announcement announcement, Model model){
        announcementService.announcementWrite(announcement);
        model.addAttribute("message", "글 작성이 등록되었습니다.");
        model.addAttribute("searchUrl", "anno_List");
;

        return "service/announcement/anno_message";
    }


    // 게시글 목록 보기
    @GetMapping(value = "/announcement/anno_List")
    public String Anno_List(Model model, @PageableDefault(page=0, sort = "no", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword){

        // 검색 기능
        Page<Announcement> list = null;
        if(searchKeyword == null){
            list = announcementService.announcementList(pageable);
        }else {
            list = announcementService.announcementSearchList(searchKeyword, pageable);
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



        return "service/announcement/anno_List";
    }

    // 게시글 상세 페이지 보기
    @GetMapping(value = "/announcement/anno_Detail")
    public String Anno_Detail(Model model, Long no){
        model.addAttribute("anno_Detail", announcementService.announcementDetail(no));
        return "service/announcement/anno_Detail";
    }

    // 게시글 삭제
    @GetMapping("/announcement/anno_Delete")
    public String Anno_Delete(Long no, Model model){
        announcementService.announcementDelete(no);

        model.addAttribute("message", "글 삭제가 완료 되었습니다.");
        model.addAttribute("searchUrl", "anno_List");

        return "service/announcement/anno_message";
    }

    // 게시글 수정
    @GetMapping("/announcement/anno_Update/{no}")
    public String Anno_UpdateG( @PathVariable("no") Long  no, Model model){
        model.addAttribute("anno_Detail", announcementService.announcementDetail(no));
        return "service/announcement/anno_Update";
    }

    @PostMapping("/announcement/anno_Update/{no}")
    public String Anno_UpdateP(@PathVariable("no") Long no, Announcement announcement, Model model){
        Announcement anno_Update = announcementService.announcementDetail(no);
        anno_Update.setNo(announcement.getNo());
        anno_Update.setTitle(announcement.getTitle());
        anno_Update.setContent(announcement.getContent());

        announcementService.announcementWrite(anno_Update);

        model.addAttribute("message", "글 수정이 완료 되었습니다.");
        model.addAttribute("searchUrl", "/announcement/anno_List");

        return "service/announcement/anno_message";
    }

}