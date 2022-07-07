package com.joodang.service.announcement.Dto;

import com.joodang.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

// dto 객체 :  게시판 화면에서 데이터(Data)가 전송(Transfer)될 때 값을 저장할 객체(Object)
@Getter @Setter
public class AnnouncementDto {
    private Long no;
    private String writer;
    private String title;
    private String content;
    private Date redTime;
    private Member member;
}

/*
    1. @NotBlank : NULL 체크 및 문자열의 경우 길이가 0 및 비어 있는 문자열("")인지를 검사합니다.
    2. message : message는 유효성 검사를 하고 나서 일치하지 않을 경우 알람을 띄워주는 속성입니다.
    3. @Length는 글자수를 제한 할 때 사용합니다.
        - 속성 : min(최소), max(최대), message(불일치 시 오류 메시지) 
 */