package com.joodang.service.OneByOne.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "oboBoard")
@Setter @Getter @ToString
public class OneByOne {

    @Id
    @Column(name = "obo_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long no;

    @Column(name = "obo_writer",nullable = false)
    private String writer;

    @Column(name = "obo_title",nullable = false)
    private String title;

    @Column(name = "obo_content", nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "date default sysdate")
    @Temporal(TemporalType.DATE)
    private Date redTime = new Date();

    @Column(name = "obo_secret")
    private boolean secret;

    @Column(name = "obo_secretPass")
    private int secretPass;

}

/*
1by1_writer(작성자) varchar2(100)
1by1_title(제목) varchar2(30) not null
1by1_coment(내용) varchar2(255)  not null
1by1_regdate(작성일) date default sysdate
 */