package capstone_demo.controller;

import capstone_demo.dto.AdminLoginRequestDto;
import capstone_demo.dto.DelivererLoginRequestDto;
import capstone_demo.dto.ResidentLoginRequestDto;
import capstone_demo.dto.TokenInfo;
import capstone_demo.service.AdminService;
import capstone_demo.service.DelivererService;
import capstone_demo.service.ResidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AdminService adminService;

    //관리 시스템 토큰 발급 안하는 로그인
    @PostMapping("/managesys/admin/first-login")
    public ResponseEntity<?> adminFirstLogin(@RequestBody AdminLoginRequestDto dto){
        adminService.firstLogin(dto.getId(), dto.getPassword());
        return new ResponseEntity<>("관리인 로그인에 성공했습니다", HttpStatus.OK);
    }
}
