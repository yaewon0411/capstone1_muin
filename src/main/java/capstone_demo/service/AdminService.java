package capstone_demo.service;

import capstone_demo.domain.Admin;
import capstone_demo.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
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
    public void findByAdmin(Admin admin){
        Admin findAdmin = adminRepository.findByAdmin(admin);
        if(findAdmin==null){
            throw new IllegalStateException("존재하지 않는 관리인 입니다");
        }
    }



}
