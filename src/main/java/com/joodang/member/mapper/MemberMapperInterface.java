package com.joodang.member.mapper;

import com.joodang.member.model.MemberModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper     // 스프링이 자동으로 객체를 생성하고, sql 구문을 분석해 줍니다.
public interface MemberMapperInterface {
    @Select("select * from members")
    List<MemberModel> SelectAll();

    @Select("select * from members where email = #{email}")
    MemberModel SelectOne(@Param("email") String email);

    @Select("select * from members where email = #{email} and m_name = #{m_name} and m_phone = #{m_phone}")
    MemberModel SelectOnePwReset(@Param("email") String email, @Param("m_name") String m_name, @Param("m_phone") String m_phone);

    @Select("select * from members where m_name = #{m_name} and m_phone = #{m_phone}")
    MemberModel FindEmail(@Param("m_name") String m_name, @Param("m_phone") String m_phone);

    @Update("update members set m_password = #{m_password} where email = #{email}")
    int PasswordReset(@Param("m_password") String m_password, @Param("email") String email);

    @Insert("insert into members(email, m_password, m_name, m_phone, m_gender, m_zipcode, m_address1, m_address2, m_region) values(#{email}, #{m_password}, #{m_name}, #{m_phone}, #{m_gender}, #{m_zipcode}, #{m_address1}, #{m_address2}, #{m_region})")
    int Insert(@Param("memberModel") final MemberModel memberModel);

    @Select("select * from members where email = #{email}")
    MemberModel EmailCheck(@Param("email") String email);


}
