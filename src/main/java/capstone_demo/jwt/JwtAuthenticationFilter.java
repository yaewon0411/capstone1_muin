package capstone_demo.jwt;

import capstone_demo.domain.Admin;
import capstone_demo.domain.Deliverer;
import capstone_demo.domain.resident.Resident;
import capstone_demo.dto.AdminLoginRequestDto;
import capstone_demo.dto.DelivererLoginRequestDto;
import capstone_demo.dto.ResidentLoginRequestDto;
import capstone_demo.dto.TokenInfo;
import capstone_demo.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private Authentication authentication;
    private final static Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.debug("디버그 : attemptAuthentication 호출됨");
        String uri = request.getRequestURI();

        if (uri.equals("/notifsys/login") || uri.equals("/managesys/resident/login")) {
            return handleResidentLogin(request);
        } else if (uri.equals("/managesys/deliverer/login")) {
            return handleDelivererLogin(request);
        } else if (uri.equals("/managesys/admin/re-login")) {
            return handleAdminLogin(request);
        }
        return null;
    }

    //거주인 로그인 처리
    private Authentication handleResidentLogin(HttpServletRequest request) throws AuthenticationException {
        try{
            ObjectMapper om = new ObjectMapper();
            ResidentLoginRequestDto dto = om.readValue(request.getInputStream(), ResidentLoginRequestDto.class);

            //강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    dto.getBirth()+dto.getAddress()+dto.getName(), dto.getName()
            );

            authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        }catch (IOException e){
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    //배달인 로그인 처리
    private Authentication handleDelivererLogin(HttpServletRequest request) throws AuthenticationException {
        try{
            ObjectMapper om = new ObjectMapper();
            DelivererLoginRequestDto dto = om.readValue(request.getInputStream(), DelivererLoginRequestDto.class);

            //강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    dto.getId(), dto.getCompany()
            );

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        }catch (IOException e){
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    private Authentication handleAdminLogin(HttpServletRequest request) throws AuthenticationException {
        try{
            ObjectMapper om = new ObjectMapper();
            AdminLoginRequestDto dto = om.readValue(request.getInputStream(), AdminLoginRequestDto.class);

            //강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    dto.getId(), dto.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        }catch (IOException e){
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    //return authentication이 잘 작동하면 successfulAuthentication가 호출됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("디버그 : successfulAuthentication 호출됨");
        Object principal = authResult.getPrincipal();

        if(principal instanceof Resident){
            Resident resident = (Resident) principal;
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            response.addHeader(JwtVo.HEADER, tokenInfo.getAccessToken());

            ResidentLoginRequestDto loginRequestDto = new ResidentLoginRequestDto(resident);
            ResponseUtil.success(response, loginRequestDto);
        }
        else if(principal instanceof Deliverer){
            Deliverer deliverer = (Deliverer) principal;
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            response.addHeader(JwtVo.HEADER, tokenInfo.getAccessToken());

            DelivererLoginRequestDto loginRequestDto = new DelivererLoginRequestDto(deliverer);
            ResponseUtil.success(response, loginRequestDto);
        }
        else if(principal instanceof Admin){
            Admin admin = (Admin) principal;
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            response.addHeader(JwtVo.HEADER, tokenInfo.getAccessToken());

            AdminLoginRequestDto loginRequestDto = new AdminLoginRequestDto(admin);
            ResponseUtil.success(response, loginRequestDto);
        }

    }

    // 로그인 실패 시 호출됨
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.fail(response, "로그인실패", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //request header에서 jwt 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        //validateToken 으로 토큰 유효성 검사
        if(token != null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    //Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
