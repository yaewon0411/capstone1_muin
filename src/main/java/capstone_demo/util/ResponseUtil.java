package capstone_demo.util;

import capstone_demo.dto.api.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.print.DocFlavor;

public class ResponseUtil {

    private final static Logger log = LoggerFactory.getLogger(ResponseUtil.class);

    public static void success(HttpServletResponse response, Object dto){
        try{
            ObjectMapper om = new ObjectMapper();
            ResponseDto<Object> responseDto = new ResponseDto<>("로그인에 성공했습니다", 1, dto);
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);

        } catch (Exception e){
            log.error("서버 파싱 에러");
        }
    }

    public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus){
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(msg, -1, null);
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpStatus.value());
            response.getWriter().println(responseBody); //만약 response status가 403이면, 그 response를 가로채고 내용을 "error"로 바꿈

        }catch (Exception e){
            log.error("서버 파싱 에러");
        }
    }
}
