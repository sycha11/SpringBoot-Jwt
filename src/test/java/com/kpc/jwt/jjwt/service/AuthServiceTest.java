package com.kpc.jwt.jjwt.service;

import com.kpc.jwt.jjwt.model.dto.MemberDto;
import com.kpc.jwt.jjwt.model.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    public void signUp(){
        Member member = new Member();
        member.setUsername("ssafy22");
        member.setPassword("123456");
        member.setName("차싸피");
        member.setEmail("403.forbidden@kakao.com");
        member.setAddress("대한민국 인천광역시 남동구 호구포로");
        authService.signUpUser(member);
    }

    @Test
    public void login(){
        MemberDto loginUser = new MemberDto("ssafy","1234");
        try{
            authService.loginUser(loginUser.getUsername(),loginUser.getPassword());
            log.info("로그인 성공");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}