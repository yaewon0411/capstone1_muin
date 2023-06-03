package capstone_demo.service;

import capstone_demo.domain.*;
import capstone_demo.dto.ParcelInfo;
import capstone_demo.repository.ParcelHistoryRepository;
import capstone_demo.repository.ResidentSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.resource.cci.ResultSetInfo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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

        String company;
        String trackingNumber;
        String status;
        String dateTime;

        JSONArray ary = new JSONArray();

        List<Object[]> find = parcelHistoryRepository.findCompanyAndTrackingNumberAndStatus(resident);

        for(int i = 0;i<find.size();i++){

            ParcelInfo info = new ParcelInfo();
            Object[] objects = find.get(i);

            company = objects[0].toString();
            trackingNumber = objects[1].toString();
            status = objects[2].toString();
            dateTime = objects[3].toString();

            info.setCompany(company);
            info.setTrackingNumber(trackingNumber);
            info.setStatus(status);
            info.setLocalDateTime(dateTime);

            ary.add(info);
        }
        return ary;
    }
    public Object findNameAndAddressAndStatusCount(Resident resident){

        String name = resident.getName();
        String address = resident.getAddress();

        List<ParcelInfo> findList = findCompanyAndTrackingNumberAndStatus(resident);

        int pickupCount=0;
        int deliveredCount=0;
        int returnCount=0;
        int returnedCount=0;

        for (ParcelInfo parcelInfo : findList) {
            if(parcelInfo.getStatus().equals("AWAITING_PICKUP")){
                pickupCount++;
            }
            else if(parcelInfo.getStatus().equals("DELIVERED")){
                deliveredCount++;
            }
            else if(parcelInfo.getStatus().equals("AWAITING_RETURN")){
                returnCount++;
            }
            else{
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

}
