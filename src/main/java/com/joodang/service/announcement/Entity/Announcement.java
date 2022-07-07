package com.joodang.service.announcement.Entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/*
   1. Entity는 DB가 해당 객체를 인식 가능
 */
@Entity
@Table(name = "annoBoard")
@Getter @Setter @ToString(exclude = "member")
public class Announcement {


    @Id
    @Column(name = "anno_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long no;

    @Column(name = "anno_writer", nullable = false)
    private String writer;

    @Column(name = "anno_title", nullable = false)
    private String title;

    @Column(name = "anno_content", nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "date default sysdate")
    @Temporal(TemporalType.DATE)
    private Date redTime = new Date();

}


/*

1. Column 내용
ann_no(글번호)  number(5) 시퀀스
ann_writer(작성자) varchar2(100)
ann_title(제목) varchar2(50)  not null
ann_coment(내용) varchar2(255)  not null
ann_regdate(작성일) date default sysdate


 */
