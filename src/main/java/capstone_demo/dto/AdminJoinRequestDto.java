package capstone_demo.dto;

import lombok.Data;

@Data
public class AdminJoinRequestDto {
    private String id;
    private String password;
    private String repassword;
    private String residence;
    private String zipcode;
}
