package capstone_demo.dto;

import capstone_demo.domain.Deliverer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DelivererLoginRequestDto {
    private String id;
    private String company;
    private String name;

    public DelivererLoginRequestDto(Deliverer deliverer) {
        this.id = deliverer.getId();
        this.company = deliverer.getCompany();
        this.name = deliverer.getName();
    }
}
