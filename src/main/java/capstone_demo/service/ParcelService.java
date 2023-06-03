package capstone_demo.service;

import capstone_demo.domain.Parcel;
import capstone_demo.repository.ParcelRepository;
import capstone_demo.repository.search.ParcelSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;

    //택배 등록
    public void parcelRegistration(Parcel parcel){
        parcelRepository.save(parcel);
    }

    //택배 조회
    public List<Parcel> searchParcel(ParcelSearch parcelSearch){

        List<Parcel> findList = parcelRepository.findAllByString(parcelSearch);

        if(findList.isEmpty()){
            throw new IllegalStateException("존재하는 택배가 없습니다.");
        }

        return findList;
    }
    //택배 제거
    public void deleteParcel(Parcel parcel){

        parcelRepository.deleteParcel(parcel);
    }




}
