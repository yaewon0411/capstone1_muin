package capstone_demo.service;

import capstone_demo.domain.resident.Resident;
import capstone_demo.domain.resident.ResidentDto;
import capstone_demo.dto.TokenInfo;
//import capstone_demo.jwt.JwtTokenProvider;
import capstone_demo.jwt.JwtTokenProvider;
import capstone_demo.repository.ParcelHistoryRepository;
import capstone_demo.repository.ResidentRepository;
import capstone_demo.domain.resident.ResidentSearch;
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
import org.json.simple.JSONArray;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResidentService{

    private final ResidentRepository residentRepository;
    private final ParcelHistoryRepository parcelHistoryRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager; //SecurityConfig 에 Bean 등록


    //거주인 등록(회원가입)
    @Transactional
    public void join(ResidentDto residentDto){
        validateDuplicateResident(residentDto); //중복 회원 검증
        residentRepository.save(residentDto.toEntity());
    }
    //거주인 중복 검사
    public void validateDuplicateResident(ResidentDto residentDto){
        if(residentRepository.findByResident(residentDto.getName(), residentDto.getAddress(), residentDto.getBirth()) != null)
            throw new IllegalStateException("이미 등록된 거주인입니다.");
    }



    //거주인 로그인
    public TokenInfo login(String id, String password){ //id = birth+address+name / pw = name

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

        return tokenInfo;
    }

    public void validateResident(Resident resident){
        if(residentRepository.findByResident(resident.getName(), resident.getAddress(), resident.getBirth())==null)
            throw new NoResultException("존재하는 거주인이 아닙니다.");

    }

    //모든 거주인 조회
    public Object findAllResident(){
        List<Object[]> findList = residentRepository.findAllResident();
        JSONArray ary = new JSONArray();
        if(findList.size()==0) return ary.add("현재 존재하는 거주인이 없습니다.");


        for(int i = 0;i<findList.size();i++){
            ResidentDto dto = new ResidentDto();
            Object[] objects = findList.get(i);
            dto.setAddress(objects[0].toString());
            dto.setName(objects[1].toString());
            dto.setBirth(objects[2].toString());
            ary.add(dto);
        }
        return ary;
    }

    //거주인 객체로 거주인 조회
    public Resident getResident (ResidentDto residentDto) {
        Resident residentPC = residentRepository.findByResident(residentDto.getName(), residentDto.getAddress(), residentDto.getBirth());
        if(residentPC==null) throw new NoResultException("등록된 거주인이 아닙니다");
        return residentPC;

    }
    @Transactional
    public ResidentDto updateResident(ResidentDto beforeDto, ResidentDto afterDto){
        Resident residentPC = residentRepository.findByResident(beforeDto.getName(), beforeDto.getAddress(), beforeDto.getBirth());
        if(residentPC==null) throw new NoResultException("등록된 거주인이 아닙니다");

        residentPC.setName(afterDto.getName());
        residentPC.setAddress(afterDto.getAddress());
        residentPC.setBirth(afterDto.getBirth());

        return new ResidentDto(residentPC.getName(), residentPC.getAddress(), residentPC.getBirth());
    }

    //이름과 주소로 거주인 조회
    public Resident findByNameAndAddress(String name, String address){
        List<Resident> findList = residentRepository.findByNameAndAddress(name, address);
        if(findList.size()==0) throw new NoResultException("존재하는 거주인이 없습니다.");
        else return findList.get(0);
    }
    //모든 검색 조건을 이용하여 거주인 조회
    public Resident findResidentByAllString(ResidentSearch search){
        return residentRepository.findAllByString(search).get(0);
    }
    //거주인 삭제
    @Transactional
    public void deleteResident(Resident resident){
        residentRepository.deleteResident(resident);
    }

}
