package capstone_demo.Controller;

import capstone_demo.domain.Admin;
import capstone_demo.domain.Resident;
import capstone_demo.service.AdminService;
import capstone_demo.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managesys")
public class ManagesysController {

    private final AdminService adminService;
    private final ResidentService residentService;

    @PostMapping("/adminJoin")
    public void adminJoin(@RequestBody Admin admin){
        adminService.join(admin);
    }

    @PostMapping("/resident/login")
    public void residentLogin(@RequestBody Resident resident){

    }




}
