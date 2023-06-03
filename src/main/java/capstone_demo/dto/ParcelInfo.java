package capstone_demo.dto;

import capstone_demo.domain.ParcelHistory;
import lombok.Data;

@Data
public class ParcelInfo {

    private String company;
    private String trackingNumber;
    private String status;
    private String localDateTime;


}
