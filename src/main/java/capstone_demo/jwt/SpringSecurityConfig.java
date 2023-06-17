//package capstone_demo.jwt;
//
//import jakarta.servlet.DispatcherType;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig{
//
//   // private final JwtTokenProvider jwtTokenProvider;
//
//    @Bean //AuthenticationManger는 일반적으로 SecurityConfig에 빈 등록
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authenticationConfiguration
//    )throws Exception{
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    public void configure(WebSecurity web) throws Exception{
//        web.ignoring().requestMatchers("/h2-console/**");
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/**","/notifsys/**")
//                        .permitAll())
////                .requestMatchers("/**","/notifsys/**").permitAll()
////                .requestMatchers("/managesys/admin/**").hasRole("ADMIN")
//           //     .anyRequest().authenticated()
//                .headers().frameOptions().disable()
//                .and()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return new AccessDeniedHandler() {
//            @Override
//            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                response.setContentType("application/json");
//                response.getWriter().write("Access is forbidden");
//            }
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//}
