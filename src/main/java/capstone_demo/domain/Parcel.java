package capstone_demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Parcel {
    @Id
    private String trackingNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "resident_name", referencedColumnName = "resident_name"),
            @JoinColumn(name = "resident_address", referencedColumnName = "resident_address")
    })
    private Resident resident;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "delivery_id")
//    private Deliverer deliverer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "deliverer_id", referencedColumnName = "deliverer_id"),
            @JoinColumn(name = "delivery_company", referencedColumnName = "delivery_company"),
    })
    private Deliverer deliverer;

}
