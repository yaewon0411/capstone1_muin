package capstone_demo.dto;

import capstone_demo.domain.resident.Resident;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResidentLoginRequestDto {
    private String birth;
    private String address;
    private String name;

    public ResidentLoginRequestDto(Resident resident) {
        this.birth = resident.getBirth();
        this.address = resident.getAddress();
        this.name = resident.getName();
    }
}
//    private String id; //birth+address+name
//    private String password; //name