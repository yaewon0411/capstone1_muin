package capstone_demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
public class SecretKeyResolver implements SigningKeyResolver {
    private final SecretKey secretKey;

    public SecretKeyResolver(String secret){
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, String s) {
        return secretKey;
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        return secretKey;
    }
}
