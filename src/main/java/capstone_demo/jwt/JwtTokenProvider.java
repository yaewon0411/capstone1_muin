package capstone_demo.jwt;

import capstone_demo.dto.TokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("jwt토큰프로바이더 생성자 실행");
    }
    public Key getKey(){
        return key;
    }

    //유저 정보로 accessToken 과 refreshToken 생성
    public TokenInfo generateToken(Authentication authentication){

        System.out.println("authentication.getName() = " + authentication.getName());
        //권한 가져오기
        String authority="";
        String s = authentication.getAuthorities().stream().toString();
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_[ROLE_USER]")))
            authority = "ROLE_USER";
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_[ROLE_ADMIN]")))
            authority = "ROLE_ADMIN";

        System.out.println("authority = " + authority);
        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + 86400000);
        //accessToken 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authority)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        //refreshToken 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //jwt 토큰 복호화하여 토큰에 있는 정보 추출
    public Authentication getAuthentication(String accessToken){

        //토큰 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.get("auth")==null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        //클레임에서 권한 정보 추출
        Collection<?extends GrantedAuthority> authority =
                Collections.singletonList(new SimpleGrantedAuthority(claims.get("auth").toString()));

        //Authentication 리턴
        UserDetails principal = new User(claims.getSubject(),"",authority);
        return new UsernamePasswordAuthenticationToken(principal,"",authority);
    }
    //Claim 파싱
    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
    //Resident 토큰 이용하려고 일단 추가. accessToken받는 ResidentServcie login 메서드 안에서 setSubject에 거주인 이름 정보 넣어야함
    public String extractResidentNameFromToken(String accessToken){
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getSubject();
        return subject.substring(14);
    }
    public String extractIdFromToken(String accessToken){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getSubject();
    }
    //토큰 정보 검증
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("Invalid JWT Token",e);
        }catch (ExpiredJwtException e){
            log.info("Expired JWT Token",e);
        }catch (UnsupportedJwtException e){
            log.info("Unsupported JWT Token",e);
        }catch (IllegalArgumentException e){
            log.info("JWT Claims string is empty",e);
        }
        return false;
    }
}
