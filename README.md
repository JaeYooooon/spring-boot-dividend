# 📍 Dividend Project
OpenAPI 활용 배당금 프로젝트

## ⚙ Tech Stack
- Language : Java
- Build : Gradle
- Framework : Spring Boot 2.5.6
- JDK : JDK 11
- Database : H2 Database

## 🔌 Dependencies
- Spring JPA
- Spring redis
- Spring Web
- Spring security
- H2 Database
- Jsoup
- Jwt
- Commons-Collections4

## 🛠 Function
**Function**                   | **구현 완료** | 
:----------------------------- | :----------------: |  
**Spring boot 프로젝트 세팅**     | :heavy_check_mark: | 
**H2 인메모리 데이터베이스 연동**   | :heavy_check_mark: | 
**Yahoo 파이낸스 데이터 스크래핑**  | :heavy_check_mark: | 
**DB table 모델링 및 연관관계 매핑**| :heavy_check_mark: | 
**스크래핑한 데이터를 DB 저장**      | :heavy_check_mark: | 
**배당금 조회 기능**                | :heavy_check_mark: | 
**자동 완성 기능**                  | :heavy_check_mark: | 
**회사 조회/추가/삭제 기능**          | :heavy_check_mark: | 
**로그인/회원가입 기능**             | :heavy_check_mark: | 
**레디스 서버 구성**                 | :heavy_check_mark: | 
**레디스에 데이터 캐싱/삭제**           | :heavy_check_mark: | 
**로그레벨로 필요한 로그 남기기**       | :heavy_check_mark: | 
**ControllerAdvice에서 에러 처리**    | :heavy_check_mark: | 


## 🔻 RestAPI EndPoint
🔹 배당금 조회
```
- Method Type : GET
- URI : /finance/dividend/{companyName}
- Parameter : 회사 이름
- Policy : 잘못된 회사명이 입력으로 들어온 경우
           400 Status 코드와 에러메시지 반환
- Success Response : 해당 회사의 메타 정보, 배당금 정보
```
🔹 자동 완성
```
- Method Type : GET
- URI : /company/autocomplete
- Parameter : 검색하고자 하는 Prefix
- Success Response : 해당 Prefix 로 검색되는 회사명 리스트 중 10개
```
🔹 회사 조회
```
- Method Type : GET
- URI : /company
- Success Response : 서비스에서 관리하고 있는 모든 회사 목록을 반환 
```
🔹 회사 추가
```
- Method Type : POST
- URI : /company
- Parameter : 회사 Ticker
- Policy : 해당 회사의 정보를 스크래핑하고 저장 ,
           이미 보유하고 있는 회사의 정보일 경우,
           존재하지 않는 회사 Ticker일 경우
           400 Status 코드와 에러 메시지 반환
```
🔹 회사 삭제
```
- Method Type : DELETE
- URI : /company/{ticker}
- Parameter : 회사 Ticker
- Policy : 삭제시 회사의 배당금 정보와 캐시도 모두 삭제
- Success Response : Ticker에 해당하는 회사 정보 삭제
```
🔹 회원 가입
```
- Method Type : POST
- URI : /auth/signup
- Parameter : 유저 이름, 비밀번호, 권한
- Policy : 비밀번호는 암호화된 형태로 저장,
           중복 ID인 경우 실패 응답
```
🔹 로그인
```
- Method Type : POST
- URI : /auth/signin
- Parameter : 유저 이름, 비밀번호
- Policy : 회원가입이 되어있고, 아이디/패스워드 정보가 옳은 경우 JWT 발급
```

## ❗ Impression
이번 프로젝트에서는 Jwt 토큰 발급, Redis 서버 활용, Jsoup 활용 웹 스크래핑 등등 많이 경험해보지 못한 기술들을 활용해 볼 수 있어서 좋았다. 특히 Jwt 부분은 공부를 좀 더 자세히 해봐야겠다.
프로젝트를 진행하면서 오류들이 굉장히 많이 발생했는데 대부분이 오타이거나 import 를 잘못해준 경우였다. 조금 더 꼼꼼하게 코드 작성을 해야겠다고 느꼈다. 다음 프로젝트에서는 
이런 사소한 실수때문에 시간낭비를 하지않게 노력해야겠다.
