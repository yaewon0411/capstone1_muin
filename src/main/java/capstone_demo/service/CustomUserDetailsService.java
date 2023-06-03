package capstone_demo.service;

import capstone_demo.domain.Resident;
import capstone_demo.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final ResidentRepository residentRepository;
//    private final PasswordEncoder passwordEncoder;
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        //id : 생년월일(6자리)+주소, pw : 이름
////        String birth = username.substring(0,6);
////        String address = username.substring(6);
////        System.out.println("birth = " + birth);
////        System.out.println("address = " + address);
////        Resident resident = residentRepository.findByBirthAndAddress(birth, address);
////        System.out.println(resident.getName());
////        System.out.println("=====");
////        return createUserDetails(resident);
//
//
////        return resident
////                .map(this::createUserDetails)
////                .orElseThrow(()->new UsernameNotFoundException("해당하는 거주인이 없습니다."));
//    }
////    private UserDetails createUserDetails(Resident resident){
////
////        return User.builder()
////                .username(resident.getUsername())
////                .password(passwordEncoder.encode(resident.getPassword()))
////                .roles("ROLE_USER")
////                .build();
////    }
//}
