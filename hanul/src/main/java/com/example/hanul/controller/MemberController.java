package com.example.hanul.controller;

import com.example.hanul.dto.ResponseDTO;
import com.example.hanul.model.MemberEntity;
import com.example.hanul.service.MemberService;
import com.example.hanul.security.TokenProvider;
import com.example.hanul.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;
//    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO) {
        try {
            MemberEntity user = MemberEntity.builder()
                    .email(memberDTO.getEmail())
                    .name(memberDTO.getName())
                    .password(memberDTO.getPassword())
//                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();
            MemberEntity registeredUser = memberService.create(user);
            MemberDTO responseUserDTO = MemberDTO.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .name(registeredUser.getName())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody MemberDTO memberDTO) {
        MemberEntity user = memberService.getByCredentials(
                memberDTO.getEmail(),
                memberDTO.getPassword()); //,passwordEncoder
        if (user != null) {
            final String token = tokenProvider.create(user);
            final MemberDTO responseUserDTO = MemberDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}
