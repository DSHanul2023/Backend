package com.example.hanul.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String token;
    private String id;
    private String name;
    private String email;
    private String password;
}