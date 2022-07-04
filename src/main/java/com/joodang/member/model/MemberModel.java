package com.joodang.member.model;

import com.joodang.member.constant.Gender;
import com.joodang.member.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberModel {
    private String email;
    private String m_password;
    private String m_name;
    private String m_phone;
    private String m_gender;
    private String m_zipcode;
    private String m_address1;
    private String m_region;
    private String m_address2;
    private int m_coupon;
    private int m_point;
    private String m_remark;
    private String m_role;
}
