package com.joodang.member.controller;

import com.joodang.member.dto.MemberFormDto;
import com.joodang.member.entity.Member;
import com.joodang.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/meInsertSecondForm";
    }

    @GetMapping(value = "/meMypage")
    public String memberMypage() {

        return "member/meMyForm";
    }

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // @Vaild 는 유효성 검사를 수행해주는 어노테이션
    @PostMapping(value = "/meInsert")
    public String memberInsert(@Valid MemberFormDto memberFormDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {    // 필드에 문제가 있으면
            return "member/meInsertSecondForm";
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

    @GetMapping(value = "/meFindId")
    public String memberFindId() {
        return "member/meFindIdForm";
    }

}
