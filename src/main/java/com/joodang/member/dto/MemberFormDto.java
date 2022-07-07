package com.joodang.member.dto;

import com.joodang.member.constant.Gender;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberFormDto {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해 주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하로 입력해주세요.")
    private String mPassword;

    @NotEmpty(message = "이름은 필수 입력 값입니다.")
    private String mName;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String mPhone;

    private String mRegion;

    private String mZipcode;

    private String mAddress1;

    private String mAddress2;

    private String gender; // 성별 종류
//    private Gender mGender;
}
