package capstone_demo.repository.search;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminSearch {

    private String id;
    private String residence;
    private int zipcode;
}
