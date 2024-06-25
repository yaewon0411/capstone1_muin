package capstone_demo.dto;

import capstone_demo.domain.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminLoginRequestDto {
    private String id;
    private String password;

    public AdminLoginRequestDto(Admin admin) {
        this.id = admin.getId();
        this.password = admin.getPassword();
    }
}
