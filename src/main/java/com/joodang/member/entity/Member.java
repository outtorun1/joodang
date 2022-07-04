package com.joodang.member.entity;

import com.joodang.member.constant.Gender;
import com.joodang.member.constant.Role;
import com.joodang.member.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "members")
@Getter @Setter @ToString
public class Member {
    @Id
    private String email;

    @Column(nullable = false)
    private String mPassword;

    @Column(nullable = false)
    private String mName;

    @Column(nullable = false)
    private String mPhone;

    @Enumerated(EnumType.STRING)
    private Gender mGender;

    @Column(nullable = false)
    private String mZipcode;

    @Column(nullable = false)
    private String mAddress1;

    @Column(nullable = false)
    private String mRegion;

    @Column(nullable = false)
    private String mAddress2;

    @Column(nullable = false, columnDefinition = "date default sysdate")
    @Temporal(TemporalType.DATE)
    private Date mJoinDate = new Date();

    @Column(nullable = false, columnDefinition = "number default 3000")
    private int mCoupon;

    @Column(columnDefinition = "number default 0")
    private int mPoint;

    private String mRemark;

    @Enumerated(EnumType.STRING)
    private Role mRole;


    // 화면에서 넘어오는 dto 객체와 비번을 암호화 해주는 객체를 사용하여 Member Entity 객체를 생성하기
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.setEmail(memberFormDto.getEmail());
        member.setMName(memberFormDto.getMName());
        member.setMPhone(memberFormDto.getMPhone());
//        member.setMZipcode(memberFormDto.getMZipcode());
//        member.setMAddress1(memberFormDto.getMAddress1());
//        member.setMAddress1(memberFormDto.getMAddress1());
        member.setMZipcode("01234");
        member.setMAddress1("서울");
        member.setMAddress2("본사");
//        member.setMGender(memberFormDto.getMGender());

        // 고객 지역 설정 : 고객 주소지에 속한 가맹점을 지정하기 위한 설정
//        String[] spRegion = memberFormDto.getMAddress1().split(" ");
//        member.setMRegion(spRegion[0]);
        member.setMRegion("본사");

        String password = passwordEncoder.encode(memberFormDto.getMPassword());
        member.setMPassword(password);
//        member.setMRole(Role.HQ);       // 본사 관리자
//        member.setMRole(Role.BRANCH);   // 가맹점 관리자
        member.setMRole(Role.USER);     // 일반 사용자

        return member;
    }
}
