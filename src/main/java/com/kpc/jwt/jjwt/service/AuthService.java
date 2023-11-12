package com.kpc.jwt.jjwt.service;

import com.kpc.jwt.jjwt.model.entity.Member;

public interface AuthService {
    void signUpUser(Member member);
    Member loginUser(String id, String password) throws Exception;

}
