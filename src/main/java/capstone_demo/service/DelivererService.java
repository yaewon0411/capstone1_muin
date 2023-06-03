package capstone_demo.service;

import capstone_demo.domain.Deliverer;
import capstone_demo.repository.DelivererRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DelivererService {

    private final DelivererRepository delivererRepository;

    //배달인 로그인. 회원가입 과정 없음
    public void login(Deliverer deliverer){
        delivererRepository.save(deliverer);
    }
    public List<Deliverer> findAllDeliverer(){
        return delivererRepository.findAllDeliverer();
    }
}
