package capstone_demo.service;

import capstone_demo.domain.Deliverer;
import capstone_demo.dto.TokenInfo;
import capstone_demo.jwt.JwtTokenProvider;
import capstone_demo.repository.DelivererRepository;
import capstone_demo.repository.search.DelivererSearch;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DelivererService {

    private final DelivererRepository delivererRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void join(Deliverer deliverer){
        validateDuplicateDeliverer(deliverer);
        delivererRepository.save(deliverer);
        System.out.println("가입 완료");
    }

    private void validateDuplicateDeliverer(Deliverer deliverer) {
        if(delivererRepository.findByDeliverer(deliverer)!=null)
            throw new IllegalStateException("이미 존재하는 유저입니다.");
    }

    @Transactional
    public TokenInfo login(String id, String password){ //id = id, password = company

        //1. 아이디, 비번 기반으로 authentication객체 생성
        // authentication는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        System.out.println("authenticationToken = " + authenticationToken);
        //2. 실제 검증
        //authenticate 메서드가 실행될 때 오버라이딩한 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        System.out.println("authentication = " + authentication);
        //3. 인증 정보 기반으로 jwt 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

//        //claim에 로그인한 유저 이름 정보 추가로 넣음. 작동하는지 확인할것. 기존 발급받은 토큰 클레임에 새 클레임 추가
//        String accessToken = tokenInfo.getAccessToken();
//        accessToken = Jwts.builder().claim("name",findDelivererByIdAndName(id,password)).compact();
//        String plusToken = Jwts.builder().claim("name",findDelivererByIdAndName(id,password)).compact();
//        tokenInfo.setAccessToken(accessToken+plusToken);

        return tokenInfo;
    }
    public String findDelivererNameByIdAndPassword(String id, String password){
        List<Deliverer> findList = delivererRepository.findNameByIdAndCompany(id, password);
        if(findList.size()==0) throw new NoResultException("존재하는 배달인 없음");
        return findList.get(0).getName();
    }

    public Deliverer findDelivererByIdAndName(String id, String name){
        List<Deliverer> findList = delivererRepository.findByIdAndName(id, name);
        if(findList.size()==0) throw new NoResultException("존재하지 않는 배달인 입니다");
        else if(findList.size()>1) throw new IllegalStateException("배달인의 이름과 사번은 중복될 수 없다고 가정함...");
        return findList.get(0);
    }

    public List<Deliverer> findAllDeliverer(){
        return delivererRepository.findAllDeliverer();
    }

    //배달인의 사번이 중복되지 않음을 가정..........
    public Deliverer findDelivererById(String id){
        List<Deliverer> findList = delivererRepository.findById(id);
        if(findList.size()==0) throw new NoResultException("해당하는 배달인이 존재하지 않습니다.");
        return findList.get(0);
    }

}
