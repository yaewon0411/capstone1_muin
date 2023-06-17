package capstone_demo.dto;

import lombok.Data;

@Data
public class ResidentLoginRequestDto {
    private String birth;
    private String address;
    private String name;
}
//    private String id; //birth+address+name
//    private String password; //name