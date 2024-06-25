# 무인 통합 택배 관리 시스템
택배 관리로 어려움을 겪는 다세대 건물에서 택배 수취/반송에 관한 모든 내역 관리가 가능한 서비스
## 화면
<img width="462" alt="image" src="https://github.com/yaewon0411/capstone1_muin/assets/44336444/74fd98f3-4ca0-4825-a3f5-7837fbacf6ef">
<img width="463" alt="image" src="https://github.com/yaewon0411/capstone1_muin/assets/44336444/e2f5dd5d-f074-4038-9f6b-87cad9c965f0">
<img width="452" alt="image" src="https://github.com/yaewon0411/capstone1_muin/assets/44336444/27dc9237-fac3-468f-aa67-bfaa45b2607f">

## 기록
### JwtAuthentication 인증 시나리오
![스크린샷 2024-06-25 174501](https://github.com/yaewon0411/capstone1_muin/assets/44336444/aaab48d5-f966-43a6-8ed3-97fffeaf4c1a)

1. **/login으로 request -> JwtAuthenticationFilter 동작 <br>
2. 요청 데이터 파싱 → authentication 객체 만들어서 시큐리티 세션에 안착<br>
3. authentication이 있으면 -> JWT 토큰 생성하고 클레임에 사용자 정보 추가 후 반환<br>
   authentication이 없으면(로그인 실패) -> 오류 메시지 반환 <br>

4. 이후 모든 요청에 대해 토큰 확인<br>
