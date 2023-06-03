package capstone_demo.Controller;

import capstone_demo.domain.*;
import capstone_demo.dto.ParcelInfo;
import capstone_demo.dto.ResidentDto;
import capstone_demo.repository.ResiRepo;
import capstone_demo.service.ParcelHistoryService;
import capstone_demo.service.ResidentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
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
    public Object history(Resident resident){

        return parcelHistoryService.findCompanyAndTrackingNumberAndStatus(resident);
    }
    //내 정보
    @PostMapping("/home/myInfo") //name, address, 각 택배 상태에 대한 카운트 값만 전달
    public Object myInfo(@RequestBody Resident resident) throws JsonProcessingException {
        
        residentService.validateResident(resident); //자꾸 여기서 오류 발생
        return parcelHistoryService.findNameAndAddressAndStatusCount(resident);
    }





}
