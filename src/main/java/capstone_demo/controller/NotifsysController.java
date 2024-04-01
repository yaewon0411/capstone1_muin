package capstone_demo.controller;

import capstone_demo.domain.*;
import capstone_demo.dto.ParcelInfo;
import capstone_demo.service.ParcelHistoryService;
import capstone_demo.service.ResidentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifsys")
public class NotifsysController {
    private final ResidentService residentService;
    private final ParcelHistoryService parcelHistoryService;

    //알림 홈
    @PostMapping("/home")
    public List<ParcelInfo> home(@RequestBody Resident resident) throws Exception{
        return parcelHistoryService.findCompanyAndTrackingNumberAndStatus(resident);
    }
    //알림 내역
    @PostMapping("/home/history")
    public Object history(@RequestBody Resident resident) throws Exception{
        //문구를 ~되었습니다 처럼 문장 형식으로 출력하는 버전
        return parcelHistoryService.findCompanyAndTrackingNumberAndStatus2(resident);
    }
    //내 정보
    @PostMapping("/home/myInfo") //name, address, 각 택배 상태에 대한 카운트 값만 전달
    public Object myInfo(@RequestBody Resident resident) throws JsonProcessingException {
        
        residentService.validateResident(resident);
        return parcelHistoryService.findNameAndAddressAndStatusCount(resident);
    }
}
