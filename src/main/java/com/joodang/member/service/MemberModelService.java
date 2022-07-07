package com.joodang.member.service;

import com.joodang.member.mapper.MemberMapperInterface;
import com.joodang.member.model.MemberModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberModelService {
    private final MemberMapperInterface memberMapperInterface;

    public List<MemberModel> SelectAll() {
        return memberMapperInterface.SelectAll();
    }

    public MemberModel SelectOne(String email) {
        return memberMapperInterface.SelectOne(email);
    }

    public MemberModel SelectOnePwReset(MemberModel memberModel) {
        String email = memberModel.getEmail();
        String m_name = memberModel.getM_name();
        String m_phone = memberModel.getM_phone();
        return memberMapperInterface.SelectOnePwReset(email, m_name, m_phone);
    }

    private final PasswordEncoder passwordEncoder;

    public int Insert(MemberModel memberModel) {
        String password = passwordEncoder.encode(memberModel.getM_password());
        memberModel.setM_password(password);
        return memberMapperInterface.Insert(memberModel);
    }

    public int PasswordReset(MemberModel memberModel) {
        String password = passwordEncoder.encode(memberModel.getM_password());
        String email = memberModel.getEmail();
        return memberMapperInterface.PasswordReset(password, email);
    }

    public MemberModel FindEmail(String m_name, String m_phone) {
        MemberModel findMember = memberMapperInterface.FindEmail(m_name, m_phone);
        return findMember;
    }

    public MemberModel EmailCheck(String email) {
        MemberModel findMember = memberMapperInterface.EmailCheck(email);
        return findMember;
    }
}
