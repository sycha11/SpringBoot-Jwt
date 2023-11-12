package com.kpc.jwt.jjwt.controller;

import com.kpc.jwt.jjwt.model.dto.MemberDto;
import com.kpc.jwt.jjwt.model.entity.Member;
import com.kpc.jwt.jjwt.service.AuthService;
import com.kpc.jwt.jjwt.service.CookieUtil;
import com.kpc.jwt.jjwt.service.JwtUtil;
import com.kpc.jwt.jjwt.service.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
//@RequestMapping("/user")
public class MemberController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/user/signup")
    public ResponseEntity<Member> signUpUser(@RequestBody Member member) {
        System.out.println("member = " + member);
        try {
            authService.signUpUser(member);
            return new ResponseEntity<Member>(member,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<Member> login(@RequestBody MemberDto user, HttpServletRequest req, HttpServletResponse res) {
        try {
            final Member member = authService.loginUser(user.getUsername(), user.getPassword());
            final String token = jwtUtil.generateToken(member);
            final String refreshJwt = jwtUtil.generateRefreshToken(member);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
//            return new ResponseEntity<MemberDto>("success", "로그인에 성공했습니다.", token);
            return new ResponseEntity<Member>(member, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
        }
    }
}