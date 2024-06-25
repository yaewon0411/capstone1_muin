package capstone_demo.domain.resident;

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
