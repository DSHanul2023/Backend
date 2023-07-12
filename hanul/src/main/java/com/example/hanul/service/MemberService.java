package com.example.hanul.service;

import com.example.hanul.model.MemberEntity;
import com.example.hanul.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    public MemberEntity create(final MemberEntity memberEntity){
        if(memberEntity == null || memberEntity.getEmail() == null){
            throw new RuntimeException("Invalid arguments");
        }
        final String email = memberEntity.getEmail();
        if(memberRepository.existsByEmail(email)){
            log.warn("Email already exists {}",email);
            throw new RuntimeException("Email already exists");
        }
        return memberRepository.save(memberEntity);
    }
    public MemberEntity getByCredentials(final String email,final String password){ //,final PasswordEncoder encoder
        return memberRepository.findByEmailAndPassword(email,password);
//        final MemberEntity orginalUser = memberRepository.findByEmail(email);
//        if(orginalUser != null && encoder.matches(password,orginalUser.getPassword())){
//            return orginalUser;
//        }
//        return null;
    }
}
