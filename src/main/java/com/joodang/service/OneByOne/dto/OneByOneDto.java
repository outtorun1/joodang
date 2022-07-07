package com.joodang.service.OneByOne.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter @Getter @ToString
public class OneByOneDto {
    private Long no;
    private  String writer;
    private String title;
    private String content;
    private Date redTime = new Date();
    private boolean secret;
    private int secretPass;

}
