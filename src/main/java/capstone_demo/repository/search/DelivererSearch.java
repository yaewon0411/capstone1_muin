package capstone_demo.repository.search;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DelivererSearch {

    private String id;
    private String company;
    private String name;
}
