package capstone_demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "ParcelHistory")
public class ParcelHistory {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracking_number")
    private Parcel parcel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "deliverer_id", referencedColumnName = "deliverer_id"),
            @JoinColumn(name = "delivery_company", referencedColumnName = "delivery_company"),
    })
    private Deliverer deliverer;
    private LocalDateTime localDateTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "resident_name", referencedColumnName = "resident_name"),
            @JoinColumn(name = "resident_address", referencedColumnName = "resident_address"),
    })
    private Resident resident;

    public void setResident(Resident resident){
        this.resident = resident;
//        resident.getParcelHistories().add(this);
    }

    //static으로 선언하지 않으면 이 클래스의 객체가 호출한 메서드 안에 있는 parcelHistory 객체에 값이 저장되므로 아무리 em.persist해도 db에 값 안들어옴
    public static ParcelHistory createParcelHistory(Parcel parcel){

        ParcelHistory parcelHistory  = new ParcelHistory();
        parcelHistory.setParcel(parcel);
        parcelHistory.setResident(parcel.getResident());
        parcelHistory.setLocalDateTime(LocalDateTime.now());
        parcelHistory.setStatus(ParcelStatus.AWAITING_PICKUP);
        parcelHistory.setDeliverer(parcel.getDeliverer());

        return parcelHistory;
    }
    public static ParcelHistory updateParcelHistory(Parcel parcel, ParcelStatus status){

        ParcelHistory parcelHistory = new ParcelHistory();
        parcelHistory.setParcel(parcel);
        parcelHistory.setStatus(status);
        parcelHistory.setResident(parcel.getResident());
        parcelHistory.setLocalDateTime(LocalDateTime.now());

        return parcelHistory;
    }
}
