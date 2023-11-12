package com.kpc.jwt.jjwt.service;

import com.kpc.jwt.jjwt.model.entity.Member;
import com.kpc.jwt.jjwt.model.entity.Salt;
import com.kpc.jwt.jjwt.repository.MemberRepository;
import com.kpc.jwt.jjwt.repository.SaltRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SaltRepository saltRepository;

    @Autowired
    private SaltUtil saltUtil;

    @Override
    @Transactional
    public void signUpUser(Member member) {
        String password = member.getPassword();
        String salt = saltUtil.genSalt();
        log.info(salt);
        System.out.println("salt = " + salt);
        System.out.println("password = " + password);
        member.setSalt(new Salt(salt));
        member.setPassword(saltUtil.encodePassword(salt,password));
        memberRepository.save(member);
    }

    @Override
    public Member loginUser(String id, String password) throws Exception{
        Member member = memberRepository.findByUsername(id);
        if(member==null) throw new Exception ("멤버가 조회되지 않음");
        String salt = member.getSalt().getSalt();
        password = saltUtil.encodePassword(salt,password);
        if(!member.getPassword().equals(password))
            throw new Exception ("비밀번호가 틀립니다.");
        return member;
    }
}
