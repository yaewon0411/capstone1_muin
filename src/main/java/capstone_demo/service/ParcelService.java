package capstone_demo.service;

import capstone_demo.domain.Parcel;
import capstone_demo.domain.Resident;
import capstone_demo.dto.TrackingNumberDto;
import capstone_demo.repository.ParcelRepository;
import capstone_demo.repository.search.ParcelSearch;
import jakarta.json.JsonArray;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;

    //택배 등록
    @Transactional
    public void parcelRegistration(Parcel parcel){
        parcelRepository.save(parcel);
    }

    public Parcel findByTrackingNumber(String number){
        List<Parcel> findList = parcelRepository.findByTrackingNumber(number);
        System.out.println("parcel service : findList.size() = " + findList.size());
        if(findList.size()==0) throw new NoResultException("해당 택배는 존재하지 않습니다.");
        else return findList.get(0);
    }

    //택배 조회
    public List<Parcel> searchParcel(ParcelSearch parcelSearch){

        List<Parcel> findList = parcelRepository.findAllByString(parcelSearch);

        if(findList.isEmpty()){
            throw new IllegalStateException("존재하는 택배가 없습니다.");
        }

        return findList;
    }
    @Transactional
    public void deleteParcelByResident(Resident findOne) {
        List<Parcel> findList = parcelRepository.findByResident(findOne);
        if(findList.size()==0) throw new NoResultException("해당 거주인의 택배는 등록된 적 없습니다.");
        for (Parcel parcel : findList) {
            parcelRepository.deleteParcel(parcel);
        }
        System.out.println("parcel delete");
    }

    public List<String> findTrackingNumberListByResident(Resident findOne) {
        List<Object[]> findList = parcelRepository.findTrackingNumberListByResident(findOne);
        List<String> numberList = new ArrayList<>();

        for (Object[] objects : findList) {
            numberList.add(objects[0].toString());
        }
        return numberList;
    }
}
