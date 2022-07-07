package com.joodang.config;

import com.joodang.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("[ SecurityConfig.configure : " + http.toString() + " ]");
        http.formLogin()
                .loginPage("/member/meLogin")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/member/meLogin/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/meLogout"))
                .logoutSuccessUrl("/");

        /*
            authorizeRequests : 시큐리티에서 request 를 사용하고자 할때 사용하는 메소드
            permitAll() : 로그인 하지 않아도 접근 가능하게 설정
            anyRequest().authenticated() : 상기 위에서 열거한 내용 이외의 모든 항목은 인증을 요구합니다.
        */
        http.authorizeRequests()
                .mvcMatchers("/", "/member/**",  "/story/**", "/product/**", "/productPage/**", "/community/**", "/relishPage/**", "/brewagePage/**", "/service/**", "/announcement/**", "/faq/**", "/OneByOne/**", "/way_to_come/**", "/cart/**").permitAll()
                .mvcMatchers("/admin/**", "/HQ/**", "/BRANCH/**", "/productInsert/**",  "/reInsert/**").hasAnyRole("HQ", "BRANCH")
                .anyRequest().authenticated();


        /* 인증을 받지 못한 사용자가 접근 시도시 http 응답 코드 401을 보여줍니다. */
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 다음 항목들은 인증 절차를 무시하도록 하겠습니다.
        web.ignoring().antMatchers("/css/**", "/js/**", "/imgs/**", "/images/**");
    }

    @Autowired
    MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 인증 관리자(AuthenticationManager) 객체에 회원 정보를 읽어 오는 UserDetailService 객체를 지정해 주어야 하는데,
        // MemberService를 지정해 줍니다.
        // 그리고, 암호화 객체도 지정해 주어야 하는데 passwordEncoder() 메소드를 호출하면 해결 됩니다.
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
