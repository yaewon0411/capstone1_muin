package capstone_demo.Controller;

import capstone_demo.JwtUtil;
import capstone_demo.domain.Admin;
import capstone_demo.domain.Resident;
import capstone_demo.dto.DelivererLoginRequestDto;
import capstone_demo.dto.ResidentLoginRequestDto;
import capstone_demo.dto.TokenInfo;

import capstone_demo.repository.ResiRepo;
import capstone_demo.repository.search.DelivererSearch;
import capstone_demo.service.AdminService;
import capstone_demo.service.DelivererService;
import capstone_demo.service.ResidentService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.h2.command.Token;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final ResidentService residentService;
    private final AdminService adminService;
    private final DelivererService delivererService;


//
//    @PostMapping("/notifsys/login")
//    public TokenInfo login(@RequestBody ResidentLoginRequestDto dto){
//        String id = dto.getId();
//        String password = dto.getPassword();
//        TokenInfo tokenInfo = residentService.login(id, password);
//
//        return tokenInfo;
//    }
//    @PostMapping("/managesys/login/resident")
//    public TokenInfo residentLogin(@RequestBody ResidentLoginRequestDto dto){
//        String id = dto.getId();
//        String password = dto.getPassword();
//        TokenInfo tokenInfo = residentService.login(id, password);
//
//        return tokenInfo;
//    }
//    @PostMapping("/managesys/login/deliverer")
//    public TokenInfo delivererLogin(@RequestBody DelivererLoginRequestDto dto){
//
//    }
}
