package capstone_demo.service;

import capstone_demo.domain.*;
import capstone_demo.dto.AdminViewDto;
import capstone_demo.dto.ParcelInfo;
import capstone_demo.repository.ParcelHistoryRepository;
import capstone_demo.repository.ResidentSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.NoResultException;
import jakarta.resource.cci.ResultSetInfo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ParcelHistoryService {

    private final ParcelHistoryRepository parcelHistoryRepository;

    //택배 내역 등록
    public void parcelHistoryRegistration(ParcelHistory parcelHistory){

        parcelHistoryRepository.save(parcelHistory);
    }
    //택배 내역 유효 판단
    public void validateParcelHistory(Parcel parcel){

        List<ParcelHistory> findHistory = parcelHistoryRepository.findHistoryByParcel(parcel);
        if(findHistory.isEmpty()){
            throw new IllegalStateException("존재하는 택배 내역이 없습니다.");
        }
    }
    public void updateParcelStatusToDelivered(Parcel parcel){

        validateParcelHistory(parcel);
        ParcelHistory updateHistory = ParcelHistory.updateParcelHistory(parcel, ParcelStatus.DELIVERED);
        System.out.println("상태 업데이트 : updateHistory.getStatus() = " + updateHistory.getStatus());
        parcelHistoryRepository.save(updateHistory);
    }
    public void updateParcelStatusToAwaitingReturn(Parcel parcel){

        validateParcelHistory(parcel);
        ParcelHistory updateHistory = ParcelHistory.updateParcelHistory(parcel, ParcelStatus.AWAITING_RETURN);
        parcelHistoryRepository.save(updateHistory);
    }
    public void updateParcelStatusToReturned(Parcel parcel){

        validateParcelHistory(parcel);
        ParcelHistory updateHistory = ParcelHistory.updateParcelHistory(parcel, ParcelStatus.RETURNED);
        parcelHistoryRepository.save(updateHistory);
    }
    public void deleteResidentParcelHistory(Resident resident){

        if(parcelHistoryRepository.findHistoryByResident(resident).isEmpty()){
            throw new IllegalStateException("해당 거주인의 삭제할 택배 내역이 존재하지 않습니다");
        }

        parcelHistoryRepository.deleteResidentParcelHistory(resident);
        System.out.print("[name = " + resident.getName());
        System.out.print(", address = " + resident.getAddress());
        System.out.print(", birth = " + resident.getBirth());
        System.out.println("의 택배 내역을 삭제했습니다]");
    }

    public List<ParcelHistory> findHistoryByStatus(ParcelStatus status){

        List<ParcelHistory> findHistory = parcelHistoryRepository.findHistoryByStatus(status);
        if(findHistory.isEmpty()){
            throw new IllegalStateException(status+"상태의 택배 내역 리스트가 존재하지 않습니다");
        }
        return findHistory;
    }
    public int findHistoryByStatusForCount(ParcelStatus status){
        return  parcelHistoryRepository.findHistoryByStatus(status).size();
    }
    public List<ParcelHistory> findHistoryByParcel(Parcel parcel){

        validateParcelHistory(parcel);
        return parcelHistoryRepository.findHistoryByParcel(parcel);
    }
    public List<ParcelHistory> findHistoryByResident(Resident resident){

        List<ParcelHistory> findHistory = parcelHistoryRepository.findHistoryByResident(resident);
        if(findHistory.isEmpty()){
            throw new IllegalStateException("해당 거주인의 택배 내역이 존재하지 않습니다");
        }
        return findHistory;
    }
    public List<ParcelInfo> findCompanyAndTrackingNumberAndStatus(Resident resident){
        String company, trackingNumber, status, dateTime;
        JSONArray ary = new JSONArray();
        List<Object[]> find = parcelHistoryRepository.findCompanyAndTrackingNumberAndStatus(resident);

        for(int i = 0;i<find.size();i++){
            ParcelInfo info = new ParcelInfo();
            Object[] objects = find.get(i);
            int size = parcelHistoryRepository.findHistoryByTrackingNumber(objects[1].toString()).size();

            if(size==2){
                if(objects[2].toString().equals("AWAITING_PICKUP"))
                    continue;
            }
            if(size==3){
                if(objects[2].toString().equals("AWAITING_PICKUP"))
                    continue;
                if(objects[2].toString().equals("DELIVERED"))
                    continue;
            }
            if(size==4){
                if(objects[2].toString().equals("AWAITING_PICKUP"))
                    continue;
                if(objects[2].toString().equals("AWAITING_RETURN"))
                    continue;
                if(objects[2].toString().equals("DELIVERED"))
                    continue;
            }
            company = objects[0].toString();
            trackingNumber = objects[1].toString();
            status = objects[2].toString();
            dateTime = objects[3].toString();

            if(status.equals("AWAITING_PICKUP")){
                info.setStatus("수취대기");
            }
            else if(status.equals("AWAITING_RETURN")){
                info.setStatus("반송대기");
            }
            else if(status.equals("DELIVERED")){
                info.setStatus("수취완료");
            }
            else if(status.equals("RETURNED")){
                info.setStatus("반송완료");
            }
            info.setCompany(company);
            info.setTrackingNumber(trackingNumber);
            info.setLocalDateTime(dateTime);

            ary.add(info);
        }
        return ary;
    }
    public List<ParcelInfo> findCompanyAndTrackingNumberAndStatus2(Resident resident){

        String company, trackingNumber, status, dateTime;
        JSONArray ary = new JSONArray();
        List<Object[]> find = parcelHistoryRepository.findCompanyAndTrackingNumberAndStatus(resident);

        for(int i = 0;i<find.size();i++){

            ParcelInfo info = new ParcelInfo();
            Object[] objects = find.get(i);

            company = objects[0].toString();
            trackingNumber = objects[1].toString();
            status = objects[2].toString();
            dateTime = objects[3].toString();

            if(status.equals("AWAITING_PICKUP")){
                info.setStatus("택배 배달이 완료되었습니다.");
            }
            else if(status.equals("AWAITING_RETURN")){
                info.setStatus("반송 택배 등록이 완료되었습니다.");
            }
            else if(status.equals("DELIVERED")){
                info.setStatus("택배 수취가 완료되었습니다.");
            }
            else if(status.equals("RETURNED")){
                info.setStatus("반송 택배 수거가 완료되었습니다.");
            }

            info.setCompany(company);
            info.setTrackingNumber(trackingNumber);
            info.setLocalDateTime(dateTime);

            ary.add(info);
        }
        return ary;
    }
    public Object findListUsedByAdmin(){
        List<Object[]> find = parcelHistoryRepository.findCompanyAndTrackingNumberAndStatusAndName();

        String company, trackingNumber, status, dateTime, residentName;

        JSONArray ary = new JSONArray();

        for(int i = 0; i<find.size(); i++){

            AdminViewDto dto = new AdminViewDto();
            Object[] objects = find.get(i);

            company = objects[0].toString();
            trackingNumber = objects[1].toString();
            status = objects[2].toString();
            dateTime = objects[3].toString();
            residentName = objects[4].toString();

            if(status.equals("AWAITING_PICKUP")){
                dto.setStatus("수취대기");
            }
            else if(status.equals("AWAITING_RETURN")){
                dto.setStatus("반송대기");
            }
            else if(status.equals("DELIVERED")){
                dto.setStatus("수취완료");
            }
            else if(status.equals("RETURNED")){
                dto.setStatus("반송완료");
            }
            dto.setCompany(company);
            dto.setTrackingNumber(trackingNumber);
            dto.setLocalDateTime(dateTime);
            dto.setResidentName(residentName);

            ary.add(dto);
        }
        return ary;
    }
    public Object findNameAndAddressAndStatusCount(Resident resident){

        String name = resident.getName();
        String address = resident.getAddress();
        List<ParcelInfo> findList = findCompanyAndTrackingNumberAndStatus(resident);

        int pickupCount=0, deliveredCount=0, returnCount=0, returnedCount=0;

        for (ParcelInfo parcelInfo : findList) {
            if(parcelInfo.getStatus().equals("수취대기")){
                pickupCount++;
            }
            else if(parcelInfo.getStatus().equals("수취완료")){
                deliveredCount++;
            }
            else if(parcelInfo.getStatus().equals("반송대기")){
                returnCount++;
            }
            else if(parcelInfo.getStatus().equals("반송완료")){
                returnedCount++;
            }
        }

        Map<String,String> data = new HashMap<>();
        data.put("name",name);
        data.put("address",address);
        data.put("수취 대기",String.valueOf(pickupCount));
        data.put("수취 완료",String.valueOf(deliveredCount));
        data.put("반송 대기",String.valueOf(returnCount));
        data.put("반송 완료",String.valueOf(returnedCount));

        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject(data);

        return jsonObject;
    }
    public void checkAwaitingPickupStatusAndUpdate(Parcel parcel){
        List<ParcelHistory> findHistory = findHistoryByParcel(parcel);
        if(findHistory.size()==1 && findHistory.get(0).getStatus().equals(ParcelStatus.AWAITING_PICKUP)){
            updateParcelStatusToDelivered(findHistory.get(0).getParcel());
        }
    }
    public void checkDeliveredStatusAndUpdate(Parcel parcel){
        List<ParcelHistory> findHistory = findHistoryByParcel(parcel);
        for (ParcelHistory parcelHistory : findHistory) {
            if(parcelHistory.getStatus().equals(ParcelStatus.DELIVERED)){
                updateParcelStatusToAwaitingReturn(parcelHistory.getParcel());
                return;
            }
        }
    }
    public void checkAwaitingReturnStatusAndUpdate(Parcel parcel){
        List<ParcelHistory> findHistory = findHistoryByParcel(parcel);
        for (ParcelHistory parcelHistory : findHistory) {
            if(parcelHistory.getStatus().equals(ParcelStatus.AWAITING_RETURN)){
                updateParcelStatusToReturned(parcelHistory.getParcel());
                return;
            }
        }
    }
    public Object findAwaitingReturnParcelList(String company) {
        List<Object[]> find = parcelHistoryRepository.findAwaitingReturnParcelList(company);

        String cmp, trackingNumber, status, dateTime;
        JSONArray ary = new JSONArray();

        for(int i = 0; i<find.size(); i++){
            ParcelInfo info = new ParcelInfo();
            Object[] objects = find.get(i);

            if(parcelHistoryRepository.findReturnedParcel(objects[1].toString()).size()>0) {
                continue;
            }

            cmp = objects[0].toString();
            trackingNumber = objects[1].toString();
            status = "반송대기";
            dateTime = objects[3].toString();

            info.setCompany(cmp);
            info.setTrackingNumber(trackingNumber);
            info.setStatus(status);
            info.setLocalDateTime(dateTime);

            ary.add(info);
        }
        if(ary.size()==0){
            ary.add("반송 수거 대상 택배가 없습니다.");
        }
        return ary;
    }

    //제일 먼저 수취대기로 올라온 상태의 택배 내역 레코드 번호를 이미지 테이블에 사진 경로와 함께 저장함
    public ParcelHistory findFirstRegistratedHistoryByTrackingNumber(String trackingNumber) {
        List<ParcelHistory> findList = parcelHistoryRepository.findHistoryByTrackingNumber(trackingNumber);
        if(findList.size()==0) throw new NoResultException("해당 송장번호를 가진 택배내역이 존재하지 않습니다.");
        ParcelHistory one = findList.get(0);

        for (ParcelHistory parcelHistory : findList) {
            if(parcelHistory.getStatus().equals(ParcelStatus.AWAITING_PICKUP)){
                return parcelHistory;
            }
        }
        return null;
    }

}
