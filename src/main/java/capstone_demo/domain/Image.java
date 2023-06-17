package capstone_demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Image {

    @Id
    private String path;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcelHistory_id")
    private ParcelHistory parcelHistory;

}
