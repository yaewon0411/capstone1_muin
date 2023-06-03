package capstone_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenInfo {

    private  String grantType; //jwt 인증 타입. bearer 사용. http 헤더에 prefix로 붙일것
    private String accessToken;
    private String refreshToken;
}
