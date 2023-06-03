package capstone_demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class DelivererId implements Serializable {
    @Column(name = "deliverer_id")
    private String id;
    @Column(name = "delivery_company")
    private String company;
}
