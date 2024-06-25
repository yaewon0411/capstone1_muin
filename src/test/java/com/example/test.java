package com.example;

//import capstone_demo.jwt.JwtTokenProvider;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

        import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringBootTest.class)
public class test {

//    @Mock
//    private Authentication authentication;
//
//    private JwtTokenProvider jwtTokenProvider;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        jwtTokenProvider = new JwtTokenProvider("tlzmfltzjdkfaAej235skdf9dfskdjkDdhxksltygpdl12qhrhltvek");
//    }
//
//    @Test
//    public void testGenerateToken() {
//        // Mock user authentication
//        when(authentication.getName()).thenReturn("username");
//       // when(authentication.getAuthorities()).thenReturn(getAuthorities());
//
//        // Generate token
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
//
//        // Verify token properties
//        assertEquals("Bearer", tokenInfo.getGrantType());
//        System.out.println("tokenInfo.getAccessToken() = " + tokenInfo.getAccessToken());
//        System.out.println("tokenInfo.getRefreshToken() = " + tokenInfo.getRefreshToken());
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities() {
//        // Return a collection of user authorities
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//    }



}


