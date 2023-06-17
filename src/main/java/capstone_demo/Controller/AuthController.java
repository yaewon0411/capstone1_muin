package capstone_demo.Controller;

import capstone_demo.dto.AdminLoginRequestDto;
import capstone_demo.dto.DelivererLoginRequestDto;
import capstone_demo.dto.ResidentLoginRequestDto;
import capstone_demo.dto.TokenInfo;
import capstone_demo.service.AdminService;
import capstone_demo.service.DelivererService;
import capstone_demo.service.ResidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final ResidentService residentService;
    private final AdminService adminService;
    private final DelivererService delivererService;

    //알림 시스템 거주인 로그인
    @PostMapping("/notifsys/login")
    public TokenInfo notifsysLogin(@RequestBody ResidentLoginRequestDto dto){
        return residentService.login(dto.getBirth()+dto.getAddress()+dto.getName(),
                dto.getName());
    }

    //관리 시스템 거주인 로그인
    @PostMapping("/managesys/login/resident")
    public TokenInfo residentLogin(@RequestBody ResidentLoginRequestDto dto){
        return residentService.login(dto.getBirth()+dto.getAddress()+dto.getName(), dto.getName());
    }
    //관리 시스템 배달인 로그인
    @PostMapping("/managesys/login/deliverer")
    public TokenInfo delivererLogin(@RequestBody DelivererLoginRequestDto dto){
        return delivererService.login(dto.getId(), dto.getCompany());
    }
    //관리 시스템 관리인 로그인
    @PostMapping("/managesys/relogin/admin")
    public TokenInfo adminLodin(@RequestBody AdminLoginRequestDto dto){
        return adminService.login(dto.getId(), dto.getPassword());
    }
    //관리 시스템 토큰 발급 안하는 로그인
    @PostMapping("/managesys/firstlogin")
    public String adminFirstLogin(@RequestBody AdminLoginRequestDto dto){
        adminService.firstLogin(dto.getId(), dto.getPassword());
        return "success";
    }
}
