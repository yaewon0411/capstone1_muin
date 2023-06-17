package capstone_demo.Controller;

import capstone_demo.domain.*;
import capstone_demo.dto.*;
import capstone_demo.jwt.JwtTokenProvider;
import capstone_demo.jwt.TokenUtil;
import capstone_demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managesys")
public class ManagesysController {

    private final AdminService adminService;
    private final ResidentService residentService;
    private final ParcelHistoryService parcelHistoryService;
    private final ParcelService parcelService;
    private final DelivererService delivererService;
    private final TokenUtil tokenUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final ImageService imageService;

    //관리 시스템 관리인 회원가입
    @PostMapping("/adminJoin")
    public boolean adminJoin(@RequestBody Admin admin) {
        adminService.join(admin);
        return true;
    }

    //=========================거주인==============================


    //수취할 송장번호 입력
    @PostMapping("/resident/delivered/trackingNumber")
    public String forParcelDelivered(@RequestBody PathAndTrackingNumberDto dto) {
        Parcel findParcel = parcelService.findByTrackingNumber(dto.getTrackingNumber());
        ParcelHistory findHistory
                = parcelHistoryService.findFirstRegistratedHistoryByTrackingNumber(dto.getTrackingNumber());
        //Image 생성 메서드 만들어서 나중에 테스트해볼것
        Image image = new Image();
        image.setPath(dto.getPath());
        image.setParcelHistory(findHistory);
        imageService.imageSave(image);

        parcelHistoryService.checkAwaitingPickupStatusAndUpdate(findParcel);
        return "success";
    }

    //반송 등록할 송장번호 입력
    @PostMapping("/resident/awaitingReturn/trackingNumber")
    public String forParcelReturned(@RequestBody TrackingNumberDto dto) {
        Parcel findParcel = parcelService.findByTrackingNumber(dto.getTrackingNumber());
        parcelHistoryService.checkDeliveredStatusAndUpdate(findParcel);
        return "success";
    }


    //=====================배달인================================

    //택배 등록. 수취대기 상태인 택배 내역도 같이 등록
    @PostMapping("/deliverer/parcel/registration")
    public String parcelRegistration(@RequestBody ParcelDto dto, HttpServletRequest request) {

        String getToken = tokenUtil.getToken(request);

      //  String delivererName = tokenUtil.extractNameFromToken(getToken); //토큰에서 현재 로그인중인 배달인의 name 추출
        String delivererId =  jwtTokenProvider.extractIdFromToken(getToken); //토큰에서 현재 로그인중인 배달인의 id 추출
        Deliverer findOne = delivererService.findDelivererById(delivererId);
        Resident findResident = residentService.findByNameAndAddress(dto.getName(), dto.getAddress());
        Deliverer findDeliverer = delivererService.findDelivererByIdAndName(delivererId, findOne.getName());

      //  Parcel 생성 메서드로 나중에 바꿀것. 만약 리스트로 출력될 때 생성 메서드도 포함되면 걍 Parcel에 있는 생성 메서드 지울것
        Parcel parcel = new Parcel();
        parcel.setResident(findResident);
        parcel.setDeliverer(findDeliverer);
        parcel.setTrackingNumber(dto.getTrackingNumber());

//        Parcel parcel = Parcel.create(dto.getTrackingNumber(), findResident, findDeliverer);

        parcelService.parcelRegistration(parcel);
        parcelHistoryService.parcelHistoryRegistration(ParcelHistory.createParcelHistory(parcel));

        return "success";
    }



    //택배 수거 화면
    @PostMapping("/deliverer/parcel/awaitingReturnList")
    public Object pageForAwaitingReturnParcelList(HttpServletRequest request){

        String getToken = tokenUtil.getToken(request);
        String delivererId= jwtTokenProvider.extractIdFromToken(getToken);
        Deliverer findDeliverer = delivererService.findDelivererById(delivererId);
        String company = findDeliverer.getCompany();

        return parcelHistoryService.findAwaitingReturnParcelList(company);
    }
    //반송 수거할 택배 송장번호 찍으면 택배 내역 수거 완료로 변경
    @PostMapping("/deliverer/parcel/pickup")
    public String parcelPickUp(@RequestBody TrackingNumberDto dto){
        System.out.println("trackingNumber = " + dto.getTrackingNumber());
        Parcel findParcel = parcelService.findByTrackingNumber(dto.getTrackingNumber());
        parcelHistoryService.checkAwaitingReturnStatusAndUpdate(findParcel);
        return "success";
    }


    //===================관리인============================//
    //관리인 회원가입
    @PostMapping("/admin/join")
    public String adminJoin(@RequestBody AdminJoinRequestDto dto){
        Admin admin = Admin.create(dto.getId(), dto.getPassword(), dto.getResidence(), dto.getZipcode());
        adminService.join(admin);
        return "success";
    }
    //택배 통합 조회 기능. date, 송장번호, 거주인 이름, 택배사명 출력
    @PostMapping("/admin/parcel/showAll")
    public Object parcelAllList(){
        return parcelHistoryService.findListUsedByAdmin();
    }

    //거주인 안면 사진 보여주기
    @PostMapping("/admin/parcel/showAll/image")
    public Path showImage(@RequestBody TrackingNumberDto dto){
        ParcelHistory findHistory = parcelHistoryService.findFirstRegistratedHistoryByTrackingNumber(dto.getTrackingNumber());
        Path path = new Path();
        path.setPath(imageService.findPathByHistoryId(findHistory.getId()));
        return path;
    }
    //거주자 정보 관리 - 호실, 이름, 생년월일 반환
    @PostMapping("/admin/resident/showAll")
    public Object showResidentList(){
        return residentService.findAllResident();
    }
    //거주자 정보 관리 - 거주자 추가
    @PostMapping("/admin/resident/create")
    public String createResident(@RequestBody ResidentDto dto){
        Resident resident = new Resident();
        resident.setAddress(dto.getAddress());
        resident.setName(dto.getName());
        resident.setBirth(dto.getBirth());
        residentService.join(resident);
        return "success";
    }
    //거주자 정보 관리 - 거주자 수정
    @PostMapping("/admin/resident/update")
    public String updateResident(@RequestBody ResidentDto beforeDto, @RequestBody ResidentDto afterDto){

        Resident resident = new Resident();

        resident.setBirth(beforeDto.getBirth());
        resident.setName(beforeDto.getName());
        resident.setAddress(beforeDto.getAddress());
        Resident findResident = residentService.findByResident(resident);

        if(!findResident.getName().equals(afterDto.getName())||
                !findResident.getAddress().equals(afterDto.getAddress())||
                !findResident.getBirth().equals(afterDto.getBirth())) {
            Resident after = new Resident();
            if (!findResident.getName().equals(afterDto.getName())) {
                after.setName(afterDto.getName());
            }
            if (!findResident.getAddress().equals(afterDto.getAddress())) {
                after.setAddress(afterDto.getAddress());
            }
            if (!findResident.getBirth().equals(afterDto.getBirth())) {
                after.setBirth(afterDto.getBirth());
            }
            residentService.deleteResident(resident);
            residentService.join(after);
        }
        return "success";
    }
    //거주자 정보 관리 - 거주자 삭제. 외래키 제약조건 고려해야함. 택배내역 -> 이미지 -> 택배 -> 거주인 순으로 삭제해야함
    @PostMapping("/admin/resident/delete")
    public String deleteResidnet(@RequestBody ResidentDto dto){
        Resident resident = new Resident();
        resident.setBirth(dto.getBirth());
        resident.setName(dto.getName());
        resident.setAddress(dto.getAddress());
        Resident findOne = residentService.findByResident(resident);

        //송장번호 먼저 찾아야 findFirstRegistratedHistoryByTrackingNumber 을 통해 택배 내역 찾고 삭제 가능
        List<String> trackingNumberList = parcelService.findTrackingNumberListByResident(findOne);
        //택배 내역에 등록된 적 없는 거주인 삭제인 경우
        System.out.println("trackingNumberList.size() = " + trackingNumberList.size());
        if(trackingNumberList.size()==0){
            residentService.deleteResident(findOne);
            return "success";
        }
        //해당 거주인 이미지 삭제
        //해당 송장번호로 등록된 택배내역 중 아이디 값이 가장 작은 내역의 아이디 추출하고 연결된 이미지 경로 레코드 삭제
        for (String s : trackingNumberList) {
            System.out.println("trackingNumber = " + s);
            ParcelHistory findHistory = parcelHistoryService.findFirstRegistratedHistoryByTrackingNumber(s);
            System.out.println("findHistory.getId() = " + findHistory.getId());
            //해당 거주인의 택배 내역이 '수취 대기'만 있어서 이미지 테이블에 등록된 적 없는 경우
            if(imageService.checkImage(findHistory.getId())) {
                System.out.println("이미지 테이블에 등록된 적 없음 = " + findHistory.getId());
                imageService.deleteImageByHistoryId(findHistory.getId());
            }
        }
        //해당 거주인 택배 내역 삭제
        parcelHistoryService.deleteResidentParcelHistory(findOne);
        //해당 거주인 택배 삭제
        parcelService.deleteParcelByResident(findOne);
        //해당 거주인 삭제
        residentService.deleteResident(findOne);

        return "삭제 완료";
    }


}
