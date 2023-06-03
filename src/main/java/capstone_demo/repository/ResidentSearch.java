package capstone_demo.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ResidentSearch {

    private String name;
    private String address;
    private String birth;

}
