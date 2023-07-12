package com.example.hanul.security;
import com.example.hanul.model.MemberEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;



@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "NMA8NMA8JPctFuna59fNMA8JPctFuna59f55JPctFuNNMA8JPctFuna59f5MA8JNMA8JPctFuna59f5PctFuna59f5na59f5";

    public String create(MemberEntity memberEntity){
        //기한은 지금부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        //jwt token 생성
        return Jwts.builder()
                //header에 들어갈 내용 및 서명을 하기 위한 secret_key
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                //payload에 들어갈 내용
                .setSubject(memberEntity.getId()) //sub
                .setIssuer("demo app") //iss
                .setIssuedAt(new Date()) //iat
                .setExpiration(expiryDate) //exp
                .compact();
    }
    public String validateAndGetUserId(String token){
        //parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        //헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        //위조되지 않았다면 페이로드(Claims)리턴, 위조라면 예외를 날림
        //그중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}