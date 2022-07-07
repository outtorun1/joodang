package com.joodang.member.controller;

import com.joodang.member.dto.MemberFormDto;
import com.joodang.member.entity.Member;
import com.joodang.member.model.MemberModel;
import com.joodang.member.repository.MemberRepository;
import com.joodang.member.service.MemberModelService;
import com.joodang.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {
    @GetMapping(value = "/meLogin")     // form 태그와 SecurityConfig 파일에 정의 되어 있습니다.
    public String memberLogin() {
        return "member/meLoginForm";
    }

    @GetMapping(value = "/meLogin/error")
    public String loginError(Model model) {
        // "loginErrorMsg"와 관련된 내용은 파일 meLoginForm.html 안에 구현 되어 있습니다.
        model.addAttribute("loginErrorMsg", "이메일 또는 비밀 번호를 확인해 주세요.");
        return "member/meLoginForm";
    }

    @GetMapping(value = "/meInsertFirst")
    public String memberInsertFirst() {
        return "member/meInsertFirstForm";
    }

    @GetMapping(value = "/meInsertSecond")
    public String memberInsertSecond(Model model) {
        // dto 객체(화면을 통하여 넘겨 주거나 받는 객체)를 모델에 바인딩하면 실제 request 영역에 데이터가 들어갑니다.
        String message = null;
        model.addAttribute("message", message);
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/meInsertSecondForm";
    }

    @GetMapping(value = "/meMypage")
    public String memberMypage() {

        return "member/meMyForm";
    }

    private final MemberModelService memberModelService;
    private final PasswordEncoder passwordEncoder;




    private final MemberService memberService;

    // @Vaild 는 유효성 검사를 수행해주는 어노테이션
    @PostMapping(value = "/meInsert")
    public String memberInsert(@Valid MemberFormDto memberFormDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {    // 필드에 문제가 있으면
            return "member/meInsertSecondForm";
        }

        if (memberFormDto.getMPassword().equals(null)) {

        } else {

        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
//            model.addAttribute("mName", member.getMName());
            memberService.saveMember(member);
            return "member/meInsertThirdForm";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "member/meInsertSecondForm";
        }
    }

    private final MemberRepository memberRepository;

    @GetMapping(value = "/emailCheck/{email}")
    public String emailCheckGET(@PathVariable("email") String email, Model model) throws Exception{
        System.out.println("[ emailCheck / email : " + email + " ]");
        Member findMember = memberRepository.findByEmail(email);
        String message = "";
        MemberFormDto memberFormDto = new MemberFormDto();
        if (findMember == null) {
            message = "ok";
            memberFormDto.setEmail(email);
        } else {
            message = "not";
        }

        model.addAttribute("message", message);
        model.addAttribute("memberFormDto", memberFormDto);
        System.out.println("이메일 컨트롤러 진입");

        return "member/meInsertSecondForm";
    }

    @GetMapping(value = "/meFindEmail")
    public String meFindEmail(Model model) {
        String message = null;
        model.addAttribute("message", message);
        return "member/meFindEmailFirst";
    }

    @PostMapping(value = "/meFindEmail")
    public String meFindEmail(MemberModel memberModel, Model model) {
        String name = memberModel.getM_name();
        String phone = memberModel.getM_phone();
        MemberModel findMember = memberModelService.FindEmail(name, phone);
        String message = "";
        if (findMember != null){
            model.addAttribute("message", message);
            model.addAttribute("email", findMember.getEmail());
            return "member/meFindEmailSecond";
        } else {
            message = "찾으시는 회원 정보가 없습니다.";
            model.addAttribute("message", message);
            return "member/meFindEmailFirst";
        }
    }

    @GetMapping(value = "/meList")
    public String memberList(Model model) {
        List<MemberModel> memberList = memberModelService.SelectAll();
        model.addAttribute("memberList", memberList);
        return "member/meList";
    }

    @GetMapping(value = "/mePwResetFirst")
    public String mePwResetFirst() {

        return "member/mePwResetFirst";
    }

    @PostMapping(value = "/meFindMember")
    public String findMember(MemberModel memberModel, Model model) {
        MemberModel findMemberModel = memberModelService.SelectOnePwReset(memberModel);
        String pwResetMsg = null;
        if (findMemberModel != null) {
            pwResetMsg = "OK";
            model.addAttribute("pwResetMsg", pwResetMsg);
            model.addAttribute("memberMode", findMemberModel);
            return "member/mePwResetSecond";
        } else {
            pwResetMsg = "ERROR";
            model.addAttribute("pwResetMsg", pwResetMsg);
            return "/member/mePwResetFirst";
        }
    }

    @PostMapping(value = "/mePwReset")
    public String mePwReset(MemberModel memberModel, Model model) {
        System.out.printf("[ mePwReset / POST : %s ]%s", memberModel.toString(), System.lineSeparator());
        int cnt = -999;
        cnt = memberModelService.PasswordReset(memberModel);

        String pwResetMsg = "";
        if (cnt == 1) {
            pwResetMsg = "RESET_COMPLETE";
            model.addAttribute("pwResetMsg", pwResetMsg);
            model.addAttribute("memberModel", memberModel);
        } else {
            pwResetMsg = "RESET_ERROR";
            model.addAttribute("pwResetMsg", pwResetMsg);
        }

        return "member/mePwResetSecond";
    }


}
