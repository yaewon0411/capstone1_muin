package capstone_demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class TokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;
    private final JwtTokenProvider jwtTokenProvider;

    private  Key key;

    public Key getSignKey(){
     //   byte[] secretBytes = Base64.getDecoder().decode(key); //JWT 서명키는 일반적으로 Base64로 인코딩된 문자열이어야 해서 Base64 인코딩 후에 byte[] 변환
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    public String extractNameFromToken(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtTokenProvider.getKey()).build().parseClaimsJws(token).getBody();
        return claims.get("name", String.class);
    }
    public String extractIdFromToken(String token){
        Claims claims = Jwts.parserBuilder().build().parseClaimsJws(token).getBody();
        String Id = claims.getSubject();
        return Id;
    }
    public String getToken(HttpServletRequest request){
        // request 에서 Authorization 헤더 추출
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 존재하고 Bearer 토큰인지 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 토큰에서 토큰 부분 추출
            return authorizationHeader.substring(7); // "Bearer " 이후의 부분 추출
        }
        else throw new IllegalStateException("헤더 재확인 바람");
    }
}
