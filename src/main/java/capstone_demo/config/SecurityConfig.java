package capstone_demo.config;


import capstone_demo.jwt.JwtAuthenticationFilter;
import capstone_demo.jwt.JwtTokenProvider;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public AuthenticationManager authenticationManager( //AuthenticationManager는 일반적으로 SecurityConfig에 빈 등록
            AuthenticationConfiguration authenticationConfiguration
    )throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("Access is forbidden");
            }
        };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable().csrf().disable() //rest라서 basic auth, crsf 보안 해제
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT 사용할 것이므로 세션 사용 해제
                .and()
                .headers().frameOptions().disable()
                .and()
                .formLogin(form -> form.disable()) //formLogin 해제
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() //어떤 템플릿으로 넘길진 모르겠지만 일단 거쳐야 하니까 설정
                        .requestMatchers("/**","/notifsys/**","/notifsys/login","/managesys/login/resident","/managesys/adminJoin").permitAll()
                        .requestMatchers("/managesys/login/deliverer", "managesys/adminJoin").permitAll()
                        .requestMatchers("/h2-console/**","/h2-console").permitAll()
                        .requestMatchers("/notifsys/home/**", "/managesys/resident/**","managesys/deliverer/**").hasRole("USER")
                        .requestMatchers("/managesys/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요
                )
//                .logout(withDefaults())	// 로그아웃은 기본설정으로 (/logout으로 인증해제)
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf().ignoringRequestMatchers("/h2-console/**").disable().httpBasic()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
