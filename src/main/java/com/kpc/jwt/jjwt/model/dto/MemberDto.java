package com.kpc.jwt.jjwt.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String username;
    private String password;

    public MemberDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
