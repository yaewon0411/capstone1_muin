package capstone_demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Parcel {
    @Id
    private String trackingNumber;

    @ManyToOne(fetch = FetchType.LAZY) //cascade REMOVE 지움
    @JoinColumns({
            @JoinColumn(name = "resident_name", referencedColumnName = "resident_name"),
            @JoinColumn(name = "resident_address", referencedColumnName = "resident_address")
    })
    private Resident resident;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "deliverer_id", referencedColumnName = "deliverer_id"),
            @JoinColumn(name = "delivery_company", referencedColumnName = "delivery_company"),
    })
    private Deliverer deliverer;

    //===생성 메서드===
    public static Parcel create(String trackingNumber, Resident resident, Deliverer deliverer){
        Parcel parcel = new Parcel();
        parcel.setTrackingNumber(trackingNumber);
        parcel.setResident(resident);
        parcel.setDeliverer(deliverer);
        return parcel;
    }
    //생성자 만들어서
//    public static Parcel create(String trackingNumber, Resident resident, Deliverer deliverer){
//
//    }

}
