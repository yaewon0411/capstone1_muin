package capstone_demo.dto.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseDto <T>{
    private final String msg;
    private final int status;
    private final T data;

}
