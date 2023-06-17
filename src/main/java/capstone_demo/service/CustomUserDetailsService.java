package capstone_demo.service;

import capstone_demo.domain.Admin;
import capstone_demo.domain.Deliverer;
import capstone_demo.domain.Resident;
import capstone_demo.repository.AdminRepository;
import capstone_demo.repository.DelivererRepository;
import capstone_demo.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final ResidentRepository residentRepository;
    private final DelivererRepository delivererRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username = " + username);
        
        List<Resident> findResident = residentRepository.findByUsername(username);
        List<Deliverer> findDeliverer = delivererRepository.findByUsername(username);
        List<Admin> findAdmin = adminRepository.findByUsername(username);

        if(findResident.size()!=0){
            System.out.println("유저 정보 : 거주인");
            return createResidentDetails(findResident.get(0));
        }
        else if(findDeliverer.size()!=0){
            System.out.println("유저 정보 : 배달인");
            return createDelivererDetails(findDeliverer.get(0));
        }
        else if(findAdmin.size()!=0){
            System.out.println("유저 정보 : 관리인");
            return createAdminDetails(findAdmin.get(0));
        }
        else{
            throw new UsernameNotFoundException("존재하는 유저가 없습니다.");
        }
    }

    private UserDetails createAdminDetails(Admin admin) {
        return User.builder()
                .username(admin.getUsername())
                .password(passwordEncoder.encode(admin.getPassword()))
                .roles(admin.getRoles().toString())
                .build();
    }

    private UserDetails createDelivererDetails(Deliverer deliverer) {
        return User.builder()
                .username(deliverer.getUsername())
                .password(passwordEncoder.encode(deliverer.getPassword()))
                .roles(deliverer.getRoles().toString())
                .build();
    }

    private UserDetails createResidentDetails(Resident resident){
        return User.builder()
                .username(resident.getUsername())
                .password(passwordEncoder.encode(resident.getPassword()))
                .roles(resident.getRoles().toString())
                .build();
    }
}
