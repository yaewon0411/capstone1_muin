package capstone_demo.service;

import capstone_demo.domain.Admin;
import capstone_demo.dto.TokenInfo;
import capstone_demo.jwt.JwtTokenProvider;
import capstone_demo.repository.AdminRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    @Transactional
    public void join(Admin admin){

        validateDuplicateAdmin(admin);
        adminRepository.save(admin);
        System.out.println("가입 완료");
    }
    public void validateDuplicateAdmin(Admin admin){

        List<Admin> findAdmins = adminRepository.findAllAdmin();
        for (Admin findAdmin : findAdmins) {
            if(findAdmins.equals(admin)) throw new IllegalStateException("이미 존재하는 관리자 입니다");
        }
    }
    public void firstLogin(String id, String password){
        List<Admin> findAdmin = adminRepository.firstLogin(id, password);
        if(findAdmin.size()==0) throw new IllegalStateException("해당 관리인은 존재하지 않습니다.");
    }

    public TokenInfo login(String id, String password){ //id = id, pw=pw

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    public void findByAdmin(Admin admin){
        Admin findAdmin = adminRepository.findByAdmin(admin);
        if(findAdmin==null){
            throw new IllegalStateException("존재하지 않는 관리인 입니다");
        }
    }
}
