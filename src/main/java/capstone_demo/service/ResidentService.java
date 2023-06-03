package capstone_demo.service;

import capstone_demo.domain.Resident;
import capstone_demo.dto.TokenInfo;
//import capstone_demo.jwt.JwtTokenProvider;
import capstone_demo.repository.ParcelHistoryRepository;
import capstone_demo.repository.ResidentRepository;
import capstone_demo.repository.ResidentSearch;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResidentService{

    private final ResidentRepository residentRepository;
    private final ParcelHistoryRepository parcelHistoryRepository;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final AuthenticationManager authenticationManager; //SecurityConfig 에 Bean 등록


    //거주인 등록(회원가입)
    public void join(Resident resident){

        validateDuplicateResident(resident); //중복 회원 검증
        residentRepository.save(resident);
        System.out.println("가입 완료");
    }
    //거주인 로그인
//    @Transactional
//    public TokenInfo login(String id, String password){ //id = birth+address / pw = name
//
//        //1. 아이디, 비번 기반으로 authentication객체 생성
//        // authentication는 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
//        System.out.println("authenticationToken = " + authenticationToken);
//        //2. 실제 검증
//        //authenticate 메서드가 실행될 때 오버라이딩한 loadUserByUsername 메서드 실행
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        System.out.println("authentication = " + authentication);
//        //3. 인증 정보 기반으로 jwt 토큰 생성
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
//
//        return tokenInfo;
//    }
    //거주인 중복 검사
    public void validateDuplicateResident(Resident resident){
        //exception
        ResidentSearch search = ResidentSearch.builder()
                .name(resident.getName())
                .address(resident.getAddress())
                .birth(resident.getBirth())
                .build();

        List<Resident> findResidents = residentRepository.findAllByString(search);
        if(!findResidents.isEmpty()){
            throw new IllegalStateException("이미 존재하는 거주인입니다.");
        }
    }
    public void validateResident(Resident resident){

        if(residentRepository.findByResident(resident)==null){
            throw new NoResultException("존재하는 거주인이 아닙니다.");
        }
    }


    //모든 거주인 조회
    public List<Resident> findAllResident(){

        System.out.println("서비스 실행");
        List<Resident> findResident = residentRepository.findAllResident();
        if(findResident.size()==0) throw new NoResultException("거주인이 존재하지 않습니다.");
        return findResident;
    }
    public Resident findByBirthAndAddress(String birth, String address){
        return residentRepository.findByBirthAndAddress(birth, address);
    }

    //거주인 객체로 거주인 조회
    public void findByResident(Resident resident) {

        Resident findResident = residentRepository.findByResident(resident);
        if(findResident==null) throw new IllegalStateException("존재하지 않는 거주인 입니다");

    }
    //이름으로 거주인 조회
    public List<Resident> findResidentByName(String name){
        return residentRepository.findByName(name);
    }
    //주소로 거주인 조회
    public List<Resident> findResidentByAddress(String address){
        return residentRepository.findByAddress(address);
    }
    //생년월일로 거주인 조회
    public List<Resident> findResidentByBirth(String birth){
        return residentRepository.findByBirth(birth);
    }
    //모든 검색 조건을 이용하여 거주인 조회
    public List<Resident> findResidentByAllString(ResidentSearch search){
        return residentRepository.findAllByString(search);
    }
    //거주인 삭제
    public void deleteResident(Resident resident){

        residentRepository.deleteResident(resident);
        parcelHistoryRepository.deleteResidentParcelHistory(resident);

    }

}
