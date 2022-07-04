package com.joodang.member.test;

import com.joodang.member.mapper.MemberMapperInterface;
import com.joodang.member.model.MemberModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MemberTestInterface {
    @Autowired
    private MemberMapperInterface memberMapperInterface;

    @Test
    @DisplayName("전체 회원 보기")
    public void SelectAll() {
        List<MemberModel> memberList = memberMapperInterface.SelectAll();

        for (MemberModel memberModel : memberList) {
            System.out.println(memberModel.toString());
        }
    }

}
