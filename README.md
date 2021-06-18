![image](https://user-images.githubusercontent.com/84000909/121766905-113ef580-cb90-11eb-9ae3-2dfef9544f9e.png)


# 카쉐어링

본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.
이는 클라우드 네이티브 애플리케이션의 개발에 요구되는 체크포인트들을 통과하기 위한 예시 답안을 포함합니다.
- 체크포인트 : https://workflowy.com/s/assessment-check-po/T5YrzcMewfo4J6LW


# Table of contents

- [카 쉐어링](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현:](#구현-)
    - [DDD 의 적용](#ddd-의-적용)
    - [폴리글랏 퍼시스턴스](#폴리글랏-퍼시스턴스)
    - [폴리글랏 프로그래밍](#폴리글랏-프로그래밍)
    - [동기식 호출 과 Fallback 처리](#동기식-호출-과-Fallback-처리)
    - [비동기식 호출 과 Eventual Consistency](#비동기식-호출-과-Eventual-Consistency)
  - [운영](#운영)
    - [CI/CD 설정](#cicd설정)
    - [동기식 호출 / 서킷 브레이킹 / 장애격리](#동기식-호출-서킷-브레이킹-장애격리)
    - [오토스케일 아웃](#오토스케일-아웃)
    - [무정지 재배포](#무정지-재배포)
  - [신규 개발 조직의 추가](#신규-개발-조직의-추가)

# 서비스 시나리오

카 쉐어링 커버하기 - https://blog.naver.com/owt1104/221174502416

기능적 요구사항
1. 고객이 대여일자/대여지/차량을 선택하여 예약을 한다.
1. 고객이 결제한다
1. 예약이 되면 예약 내역이 대여점에게 전달된다.
1. 대여점은 예약 정보를 바탕으로 대여지로 이동시킨다.
1. 고객이 예약을 취소할 수 있다
1. 예약이 취소되면 대여지 이동이 취소된다.
2. 고객이 차량을 반납지에 반납한다.
3. 대여점은 반납된 차량을 회수한다.
4. 고객은 예약 서비스를 통해 예약/대여 상태를 중간중간 열람할 수 있어야 한다.

비기능적 요구사항
1. 트랜잭션
    1. 결제가 되지 않은 예약건은 아예 대여처리가 성립되지 않아야 한다 Sync 호출 
1. 장애격리
    1. 대여관리 기능이 수행되지 않더라도 예약은 365일 24시간 받을 수 있어야 한다 Async (event-driven), Eventual Consistency
    1. 예약시스템이 과중되면 사용자를 잠시동안 받지 않고 예약를 잠시후에 하도록 유도한다 Circuit breaker, fallback
1. 성능
    1. 고객이 예약/대여 상태를 고객시스템(프론트엔드)에서 확인할 수 있어야 한다 CQRS
    


# 체크포인트

- 분석 설계


  - 이벤트스토밍: 
    - 스티커 색상별 객체의 의미를 제대로 이해하여 헥사고날 아키텍처와의 연계 설계에 적절히 반영하고 있는가?
    - 각 도메인 이벤트가 의미있는 수준으로 정의되었는가?
    - 어그리게잇: Command와 Event 들을 ACID 트랜잭션 단위의 Aggregate 로 제대로 묶었는가?
    - 기능적 요구사항과 비기능적 요구사항을 누락 없이 반영하였는가?    

  - 서브 도메인, 바운디드 컨텍스트 분리
    - 팀별 KPI 와 관심사, 상이한 배포주기 등에 따른  Sub-domain 이나 Bounded Context 를 적절히 분리하였고 그 분리 기준의 합리성이 충분히 설명되는가?
      - 적어도 3개 이상 서비스 분리
    - 폴리글랏 설계: 각 마이크로 서비스들의 구현 목표와 기능 특성에 따른 각자의 기술 Stack 과 저장소 구조를 다양하게 채택하여 설계하였는가?
    - 서비스 시나리오 중 ACID 트랜잭션이 크리티컬한 Use 케이스에 대하여 무리하게 서비스가 과다하게 조밀히 분리되지 않았는가?
  - 컨텍스트 매핑 / 이벤트 드리븐 아키텍처 
    - 업무 중요성과  도메인간 서열을 구분할 수 있는가? (Core, Supporting, General Domain)
    - Request-Response 방식과 이벤트 드리븐 방식을 구분하여 설계할 수 있는가?
    - 장애격리: 서포팅 서비스를 제거 하여도 기존 서비스에 영향이 없도록 설계하였는가?
    - 신규 서비스를 추가 하였을때 기존 서비스의 데이터베이스에 영향이 없도록 설계(열려있는 아키택처)할 수 있는가?
    - 이벤트와 폴리시를 연결하기 위한 Correlation-key 연결을 제대로 설계하였는가?

  - 헥사고날 아키텍처
    - 설계 결과에 따른 헥사고날 아키텍처 다이어그램을 제대로 그렸는가?
    
- 구현
  - [DDD] 분석단계에서의 스티커별 색상과 헥사고날 아키텍처에 따라 구현체가 매핑되게 개발되었는가?
    - Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 데이터 접근 어댑터를 개발하였는가
    - [헥사고날 아키텍처] REST Inbound adaptor 이외에 gRPC 등의 Inbound Adaptor 를 추가함에 있어서 도메인 모델의 손상을 주지 않고 새로운 프로토콜에 기존 구현체를 적응시킬 수 있는가?
    - 분석단계에서의 유비쿼터스 랭귀지 (업무현장에서 쓰는 용어) 를 사용하여 소스코드가 서술되었는가?
  - Request-Response 방식의 서비스 중심 아키텍처 구현
    - 마이크로 서비스간 Request-Response 호출에 있어 대상 서비스를 어떠한 방식으로 찾아서 호출 하였는가? (Service Discovery, REST, FeignClient)
    - 서킷브레이커를 통하여  장애를 격리시킬 수 있는가?
  - 이벤트 드리븐 아키텍처의 구현
    - 카프카를 이용하여 PubSub 으로 하나 이상의 서비스가 연동되었는가?
    - Correlation-key:  각 이벤트 건 (메시지)가 어떠한 폴리시를 처리할때 어떤 건에 연결된 처리건인지를 구별하기 위한 Correlation-key 연결을 제대로 구현 하였는가?
    - Message Consumer 마이크로서비스가 장애상황에서 수신받지 못했던 기존 이벤트들을 다시 수신받아 처리하는가?
    - Scaling-out: Message Consumer 마이크로서비스의 Replica 를 추가했을때 중복없이 이벤트를 수신할 수 있는가
    - CQRS: Materialized View 를 구현하여, 타 마이크로서비스의 데이터 원본에 접근없이(Composite 서비스나 조인SQL 등 없이) 도 내 서비스의 화면 구성과 잦은 조회가 가능한가?

  - 폴리글랏 플로그래밍
    - 각 마이크로 서비스들이 하나이상의 각자의 기술 Stack 으로 구성되었는가?
    - 각 마이크로 서비스들이 각자의 저장소 구조를 자율적으로 채택하고 각자의 저장소 유형 (RDB, NoSQL, File System 등)을 선택하여 구현하였는가?
  - API 게이트웨이
    - API GW를 통하여 마이크로 서비스들의 집입점을 통일할 수 있는가?
    - 게이트웨이와 인증서버(OAuth), JWT 토큰 인증을 통하여 마이크로서비스들을 보호할 수 있는가?
- 운영
  - SLA 준수
    - 셀프힐링: Liveness Probe 를 통하여 어떠한 서비스의 health 상태가 지속적으로 저하됨에 따라 어떠한 임계치에서 pod 가 재생되는 것을 증명할 수 있는가?
    - 서킷브레이커, 레이트리밋 등을 통한 장애격리와 성능효율을 높힐 수 있는가?
    - 오토스케일러 (HPA) 를 설정하여 확장적 운영이 가능한가?
    - 모니터링, 앨럿팅: 
  - 무정지 운영 CI/CD (10)
    - Readiness Probe 의 설정과 Rolling update을 통하여 신규 버전이 완전히 서비스를 받을 수 있는 상태일때 신규버전의 서비스로 전환됨을 siege 등으로 증명 
    - Contract Test :  자동화된 경계 테스트를 통하여 구현 오류나 API 계약위반를 미리 차단 가능한가?


# 분석/설계


## AS-IS 조직 (Horizontally-Aligned)
  ![image](https://user-images.githubusercontent.com/487999/79684144-2a893200-826a-11ea-9a01-79927d3a0107.png)

## TO-BE 조직 (Vertically-Aligned)
  ![image](https://user-images.githubusercontent.com/487999/79684159-3543c700-826a-11ea-8d5f-a3fc0c4cad87.png)


## Event Storming 결과
* MSAEz 로 모델링한 이벤트스토밍 결과:  http://www.msaez.io/#/storming/U0bzsSn93uN7NxmqmG8mL6n0vz53/share/1d5fca6c8eb5408a3560d9c2e51c78d7


### 완성된 모형

![image](https://user-images.githubusercontent.com/84000909/122478876-25a64680-d005-11eb-9295-14298277a597.png)



### 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증


![image](https://user-images.githubusercontent.com/84000909/122479068-787ffe00-d005-11eb-8265-ca229d308a0c.png)

    - 고객이 차량을 선택하여 예약한다 (ok)
    - 고객이 결제한다 (ok)
    - 예약이 되면 내역이 대여점에 전달된다 (ok)
    - 대여점은 예약일이 되면 차량을 대여지로 이동한다 (ok)


    - 고객이 예약을 취소할 수 있다 (ok)
    - 예약이 취소되면 대여지 이동이 취소된다 (ok)
    - 고객이 주문상태를 중간중간 조회한다 (View-green sticker 의 추가로 ok) 
    
    - 고객이 차량을 반납한다 (ok)
    - 대여점은 반납된 차량을 회수한다 (ok) 
    



### 비기능 요구사항에 대한 검증

![image](https://user-images.githubusercontent.com/84000909/122479151-9188af00-d005-11eb-8d34-f0d088b38f51.png)

    - 마이크로 서비스를 넘나드는 시나리오에 대한 트랜잭션 처리
        - 고객 예약시 결제처리:  결제가 완료되지 않은 예약은 절대 받지 않는다는 경영자의 오랜 신념에 따라, ACID 트랜잭션 적용. 예약완료시 결제처리에 대해서는 Request-Response 방식 처리
        - 결제 완료시 대여점 연결:  Reservation(front) 에서 Rental 마이크로서비스로 대여요청이 전달되는 과정에 있어서 Rental 마이크로 서비스가 별도의 배포주기를 가지기 때문에 Eventual Consistency 방식으로 트랜잭션 처리함.(Pub/Sub 방식)
        - 나머지 모든 inter-microservice 트랜잭션: 예약상태, 대여상태 등 모든 이벤트에 대해 Customer 마이크로 서비스 에서 확인될수 있도록 , 데이터 일관성의 시점이 크리티컬하지 않은 모든 경우가 대부분이라 판단, Eventual Consistency 를 기본으로 채택함.




## 헥사고날 아키텍처 다이어그램 도출
    
![image](https://user-images.githubusercontent.com/84000909/122500748-4d5dd480-d02e-11eb-8dc8-a3419cbf9110.png)


    - Chris Richardson, MSA Patterns 참고하여 Inbound adaptor와 Outbound adaptor를 구분함
    - 호출관계에서 PubSub 과 Req/Resp 를 구분함
    - 서브 도메인과 바운디드 컨텍스트의 분리:  각 팀의 KPI 별로 아래와 같이 관심 구현 스토리를 나눠가짐


# 구현:

분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트으로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 local port : 8081 ~ 8084 이다)

```
cd customer
mvn spring-boot:run

cd payment
mvn spring-boot:run 

cd rental
mvn spring-boot:run  

cd reservation
mvn spring-boot:run 
```

## DDD 의 적용

- 각 서비스내에 도출된 핵심 Aggregate Root 객체를 Entity 로 선언하였다: (예시는 Payment 마이크로 서비스). 이때 가능한 현업에서 사용하는 언어 (유비쿼터스 랭귀지)를 그대로 사용하려고 노력했다. 하지만, 일부 구현에 있어서 영문이 아닌 경우는 실행이 불가능한 경우가 있기 때문에 계속 사용할 방법은 아닌것 같다. (Maven pom.xml, Kafka의 topic id, FeignClient 의 서비스 id 등은 한글로 식별자를 사용하는 경우 오류가 발생하는 것을 확인하였다)

```
package carsharing;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String reserveId;
    private String carId;
    private String amount;
    private String userPhone;
    private String payType;
    private String payNumber;
    private String payCompany;
    private String payStatus;
    private String payCancelDate;


    @PostUpdate
    public void onPostUpdate(){
        if ("PayCanled".equals(this.getPayStatus()))
        {
            PayCanceled payCanceled = new PayCanceled();
            BeanUtils.copyProperties(this, payCanceled);
            payCanceled.publishAfterCommit();
        }                           
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }
    public String getPayCompany() {
        return payCompany;
    }

    public void setPayCompany(String payCompany) {
        this.payCompany = payCompany;
    }
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayCancelDate() {
        return payCancelDate;
    }

    public void setPayCancelDate(String payCancelDate) {
        this.payCancelDate = payCancelDate;
    }  


}

```
- Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 다양한 데이터소스 유형 (RDB or NoSQL) 에 대한 별도의 처리가 없도록 데이터 접근 어댑터를 자동 생성하기 위하여 Spring Data REST 의 RestRepository 를 적용하였다
```
package carsharing;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="payments", path="payments")
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long>{

    Payment findByReserveId(String reserveId);
}

```
- 적용 후 REST API 의 테스트 : 서비스 재가동 하여 IP가 변경됨 20.194.53.119 ==> http://20.194.60.72/

  - reservation 서비스의 예약처리
    http://20.194.53.119:8080/reserve_action.html

    ![image](https://user-images.githubusercontent.com/84000909/122334597-f0eaae80-cf74-11eb-81c8-e69eed108473.png)


  - rental 서비스의 대여처리
    http://20.194.53.119:8080/rental_action.html

    ![image](https://user-images.githubusercontent.com/84000909/122334637-fea03400-cf74-11eb-86f3-6bb3dbdff5bb.png)


  - reservation 서비스의 반납처리
    http://20.194.53.119:8080/return_action.html

    ![image](https://user-images.githubusercontent.com/84000909/122334666-09f35f80-cf75-11eb-88da-53523677a7d8.png)
    

  - rental 서비스의 차량회수
    http://20.194.53.119:8080/retrieve_action.html

    ![image](https://user-images.githubusercontent.com/84000909/122334704-17a8e500-cf75-11eb-871e-06daa2a46b3d.png)

  - reservation 서비스의 예약취소
    http://20.194.53.119:8080/cancel_action.html

    ![image](https://user-images.githubusercontent.com/84000909/122334733-22637a00-cf75-11eb-856c-8a27a3a0fd9e.png)


## CQRS

  - 예약/대여 상태 확인을 위한 CQRS구현(myPages)
    http://20.194.53.119:8080/myPages

    ![image](https://user-images.githubusercontent.com/84000909/122336569-07463980-cf78-11eb-979e-19ab0d82c36c.png)




## Gateway

gateway application.yml 각 서비스별  rest api url 경로 추가
![image](https://user-images.githubusercontent.com/82005223/122397340-d97ee600-cfb3-11eb-9825-133430083bdd.png)



- 빌드
mvn package -Dmaven.test.skip=true

- Docker 이미지 생성 및 ACR 등록
az acr build --registry user04skccacr --image user04skccacr.azurecr.io/carsharing-gateway:latest --file Dockerfile .

- Deployment 배포
kubectl create deploy gateway --image=user04skccacr.azurecr.io/carsharing-gateway:latest -n ns-carsharing

- Service 배포
kubectl expose deploy gateway --type="LoadBalancer" --port=8080 -n ns-carsharing

- Service External IP 확인
kubectl get svc -n ns-carsharing
![image](https://user-images.githubusercontent.com/84000909/122333701-7ec59a00-cf73-11eb-9209-f6fdde54868c.png)

결과 확인

![image](https://user-images.githubusercontent.com/84000909/122333866-bcc2be00-cf73-11eb-9d94-a477a843e4ee.png)

![image](https://user-images.githubusercontent.com/84000909/122333892-c9471680-cf73-11eb-8050-7a4aed288ca5.png)

![image](https://user-images.githubusercontent.com/84000909/122333931-d82dc900-cf73-11eb-8958-425151ce8230.png)

![image](https://user-images.githubusercontent.com/84000909/122333958-e4198b00-cf73-11eb-8baa-33d89c2ddff3.png)

## 폴리글랏 퍼시스턴스

앱프런트 (app) 는 서비스 특성상 많은 사용자의 유입과 상품 정보의 다양한 콘텐츠를 저장해야 하는 특징으로 인해 H2 DB와 HSQL DB에 부착시켰다.
Reservation : HSQL DB
나머지 Payment/rental/Customer : H2 DB

## Reservation pom.xml

![image](https://user-images.githubusercontent.com/84000909/122356555-df61d080-cf8d-11eb-9a2d-d860e54c5e08.png)


##처리 결과 : 정상
![image](https://user-images.githubusercontent.com/84000909/122358828-facddb00-cf8f-11eb-874e-b53817c2398d.png)




## 동기식 호출 과 Fallback 처리

분석단계에서의 조건 중 하나로 예약(Reservation)->결제(Payment) 간의 호출은 동기식 일관성을 유지하는 트랜잭션으로 처리하기로 하였다. 호출 프로토콜은 이미 앞서 Rest Repository 에 의해 노출되어있는 REST 서비스를 FeignClient 를 이용하여 호출하도록 한다. 

- 결제서비스를 호출하기 위하여 Stub과 (FeignClient) 를 이용하여 Service 대행 인터페이스 (Proxy) 를 구현 

```
# (Reservation) PaymentService.java


package carsharing.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(name="payment", url="${api.payment.url}")  // payment url => http://localhost:8083
public interface PaymentService {

    @RequestMapping(method= RequestMethod.POST, path="/pay")        
    public boolean pay(@RequestParam("reserveId") String reserveId,
                @RequestParam("carId") String carId,
                @RequestParam("amount") String amount,
                @RequestParam("userPhone") String userPhone,
                @RequestParam("payType") String userpayType,
                @RequestParam("payNumber") String payNumber,
                @RequestParam("payCompany") String payCompany);

}
```

- 예약을 받은 직후 1차로 예약 정보를 저장하고, 결제서비스에 결재 요청을 하여 결재 정상이면 예약 상태를 Reserved 로, 실패하면 ReserveFailed 로 상태로 갱신한다.
  ReservationController에서 구현함
  예약 상태가 Reserved 일때만 이벤트가 송신되며, ReserveFailed 일때는 이벤트 송신을 하지 않는다.
```
# ReservationController.java (Controller)
    
    @RequestMapping(value = "/reserve",
    method = RequestMethod.POST,
    produces = "application/json;charset=UTF-8")

    public String reserve(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("##### /reserve  called #####");
     
        String carId = request.getParameter("carId");
        String rentalAddr = request.getParameter("rentalAddr");
        String retrieveAddr = request.getParameter("retriveAddr");
        String userPhone = request.getParameter("userPhone");
        Long amount = Long.parseLong(request.getParameter("amount"));
        String reserveDate = request.getParameter("reserveDate");
        String payType = request.getParameter("payType");
        String payNumber = request.getParameter("payNumber");
        String payCompany = request.getParameter("payCompany");

        System.out.println("##### carId : " + carId);
        System.out.println("##### amount : " + amount);
        System.out.println("##### userPhone : " + userPhone);
        System.out.println("##### payType : " + payType);
        System.out.println("##### payNumber : " + payNumber);
        System.out.println("##### payCompany : " + payCompany);

        Reservation reservation = new Reservation();
        reservation.setCarId(carId);
        reservation.setRentalAddr(rentalAddr);
        reservation.setRetrieveAddr(retrieveAddr);
        reservation.setUserPhone(userPhone);
        reservation.setAmount(amount);
        reservation.setReserveDate(reserveDate);
        reservation.setPayType(payType);
        reservation.setPayNumber(payNumber);
        reservation.setPayCompany(payCompany);
                        
        reservation  = reservationRepository.save(reservation);

        String reserveId = Long.toString(reservation.getId());        
        System.out.println("##### reserveId : " + reserveId);

        boolean ret = false;
        try {
            ret = ReservationApplication.applicationContext.getBean(carsharing.external.PaymentService.class)
                .pay(reserveId, carId, String.valueOf(amount), userPhone, payType, payNumber, payCompany);

            System.out.println("##### /payment/pay  called result : " + ret);
        } catch (Exception e) {
            System.out.println("##### /payment/pay  called exception : " + e);
        }

        String status = "";
        if (ret) {
            status = "Reserved";
        } else {
            status = "ReserveFailed";
        } 

        reservation.setReserveStatus(status);
        reservation  = reservationRepository.save(reservation);

        return status + " ReserveNumber : " + reserveId;          
    }
    
   -- Reservation.java에서 이벤트 송시

    @PostUpdate
    public void onPostUpdate(){
        if ("Reserved".equals(this.getReserveStatus()))
        {
            Reserved reserved = new Reserved();
            BeanUtils.copyProperties(this, reserved);
            reserved.publishAfterCommit();
            System.out.println("##### send event : Reserved  #####");   
        } 
        else if ("ReserveCanceled".equals(this.getReserveStatus()))
        {
            ReserveCanceled reserveCanceled = new ReserveCanceled();
            BeanUtils.copyProperties(this, reserveCanceled);
            reserveCanceled.publishAfterCommit();
        }               
        else if ("ReserveReturned".equals(this.getReserveStatus()) )
        {
            ReserveReturned reserveReturned = new ReserveReturned();
            BeanUtils.copyProperties(this, reserveReturned);
            reserveReturned.publishAfterCommit();
            System.out.println("##### send event : ReserveReturned  #####");  
        }             
    }    
```

- 동기식 호출에서는 호출 시간에 따른 타임 커플링이 발생하며, 결제 시스템이 장애가 나면 예약이 안되는 것을 확인:


```
# 결제 (Payment) 서비스 중지
```
![image](https://user-images.githubusercontent.com/84000909/122337244-06fa6e00-cf79-11eb-984b-a699e14fabbe.png)

```
#예약처리
- 요청
```
![image](https://user-images.githubusercontent.com/84000909/122337365-36a97600-cf79-11eb-864c-5c184daad747.png)
```
- 결과 : 이벤트 없음 & cadId 3번의 ReserveStatus 가 ReserveFailed로 적용됨
```
![image](https://user-images.githubusercontent.com/84000909/122337403-41640b00-cf79-11eb-9261-d5a04f08b59c.png)
```
이벤트 발생 안됨
```
![image](https://user-images.githubusercontent.com/84000909/122337453-52148100-cf79-11eb-910b-a487e6e97920.png)
![image](https://user-images.githubusercontent.com/84000909/122475348-7e72e080-cfff-11eb-9e15-2ba88de132fa.png)


```
#결제서비스 
```
![image](https://user-images.githubusercontent.com/84000909/122337767-be8f8000-cf79-11eb-877e-925717f10fcc.png)
```

#예약처리
- 요청
```
![image](https://user-images.githubusercontent.com/84000909/121798344-af01f580-cc60-11eb-9525-bd2c1a4b8491.png)
```
- 결과
```
![image](https://user-images.githubusercontent.com/84000909/121798394-fa1c0880-cc60-11eb-9d1d-b4ef3826320e.png)



- 또한 과도한 요청시에 서비스 장애가 도미노 처럼 벌어질 수 있다. (서킷브레이커, 폴백 처리는 운영단계에서 설명한다.)





## 비동기식 호출  테스트


예약이 이루어진 후에 대여점으로 이를 알려주는 행위는 동기식이 아니라 비 동기식으로 처리하여 Rental 서비스가 장애시에도 예약/결제가 블로킹 되지 않아도록 처리한다.
 
- 이를 위하여 예약이 되면 예약됨(Reserved) 도메인 이벤트를 카프카로 송출한다(Publish)
 
 <Reservation.java>
```
package carsharing;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Reservation_table")
public class Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String carId;
    private String rentalAddr;
    private String retrieveAddr;
    private String userPhone;
    private Long amount;
    private String payType;
    private String payNumber;
    private String payCompany;
    private String reserveDate;
    private String cancelDate;
    private String returnDate;
    private String reserveStatus;

    @PostUpdate
    public void onPostUpdate(){
        if ("Reserved".equals(this.getReserveStatus()))
        {
            Reserved reserved = new Reserved();
            BeanUtils.copyProperties(this, reserved);
            reserved.publishAfterCommit();
            System.out.println("##### send event : Reserved  #####");   
        } 
        else if ("ReserveCanceled".equals(this.getReserveStatus()))
        {
            ReserveCanceled reserveCanceled = new ReserveCanceled();
            BeanUtils.copyProperties(this, reserveCanceled);
            reserveCanceled.publishAfterCommit();
        }               
        else if ("ReserveReturned".equals(this.getReserveStatus()) )
        {
            ReserveReturned reserveReturned = new ReserveReturned();
            BeanUtils.copyProperties(this, reserveReturned);
            reserveReturned.publishAfterCommit();
            System.out.println("##### send event : ReserveReturned  #####");  
        }             
    }
```
- Rental 서비스에서는 예약됨(Reserved) 이벤트를 수신하여 자신의 정책을 처리하도록(RentalAccepted) PoliyHandler 를 구현한다.
 예약정보를 DB에 RentalAccepted 상태로 저장한 후, 이후 처리는 해당 Aggregate 내에서 한다(이벤트 송출).
 
```
 < Rental PolicyHanlder.java>
package carsharing;

import carsharing.config.kafka.KafkaProcessor;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired RentalRepository rentalRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_AcceptRental(@Payload Reserved reserved){

        if(!reserved.validate()) return;

        System.out.println("\n\n##### listener AcceptRental : " + reserved.toJson() + "\n\n");

        String reserveId = Long.toString(reserved.getId());
        String carId = reserved.getCarId();
        String rentalAddr = reserved.getRentalAddr();
        String retrieveAddr = reserved.getRetrieveAddr();
        String userPhone = reserved.getUserPhone();
        Long amount = reserved.getAmount();
        String payType = reserved.getPayType();
        String payNumber = reserved.getPayNumber();
        String payCompany = reserved.getPayCompany();
        String reserveDate = reserved.getReserveDate();

        Rental rental = new Rental();
        rental.setReserveId(reserveId);
        rental.setCarId(carId);
        rental.setRentalAddr(rentalAddr);
        rental.setRetrieveAddr(retrieveAddr);
        rental.setUserPhone(userPhone);
        rental.setAmount(amount);
        rental.setPayType(payType);
        rental.setPayNumber(payNumber);
        rental.setPayCompany(payCompany);
        rental.setReserveDate(reserveDate);
        LocalDate localDate = LocalDate.now();                
        rental.setRentAcceptDate(localDate.toString());
        rental.setRentalStatus("RentalAccepted");
        rentalRepository.save(rental);           

        System.out.println("##### rental accepted by reservation reserve #####");
        System.out.println("reserveId : " + reserveId);             
    }
    
   
}

```

렌탈 서비스는 예약/결제와 완전히 분리되어 있으며, 이벤트 수신에 따라 처리되기 때문에, Rental서비스가 유지보수로 인해 잠시 내려간 상태라도 예약을 받는데 문제가 없다:

#Rental 서비스 (rental)의 중지
![image](https://user-images.githubusercontent.com/84000909/122337843-d7983100-cf79-11eb-8bac-95d62352d286.png)

#예약서비스에서 예약 처리

![image](https://user-images.githubusercontent.com/84000909/122337932-f1d20f00-cf79-11eb-9c77-acdcf45f064b.png)

```
#예약처리 결과
ㅡ고객서비스에서 예약됨(Reserved) 확인됨
```
![image](https://user-images.githubusercontent.com/84000909/122337961-fb5b7700-cf79-11eb-9632-221112b2ff26.png)

#예약상태 확인 : status = Reserved 이다
![image](https://user-images.githubusercontent.com/84000909/122338051-1c23cc80-cf7a-11eb-8489-27e331dcf01f.png)

## Rental 재기동
status = RentalAccepted 로 변경됨
![image](https://user-images.githubusercontent.com/84000909/122338141-3fe71280-cf7a-11eb-9c60-dd5d55844779.png)
```
재기동 후 고객(Customer)서비스에서 결과 RentalAccepted 확인됨
```
![image](https://user-images.githubusercontent.com/84000909/122338179-4f665b80-cf7a-11eb-8d9d-3344b24e3b59.png)



# 운영

## Deploy : Gateway는 모든 서비스 배포 후에 빌드후 배포해야 함

- azure login 후 azure 클러스터/컨테이너 레지스트리 설정작업 진행
![image](https://user-images.githubusercontent.com/84000909/122336003-1f698900-cf77-11eb-842d-3db2758282ad.png)

```
- 빌드
  서비스 소스 폴더로 이동(Reservation)
  mvn package -Dmaven.test.skip=true

- 도커이미지 생성 및 ACR에 등록
az acr build --registry user04skccacr --image user04skccacr.azurecr.io/carsharing-reservation:latest --file Dockerfile .
  
- Deployment 배포 
  kubernetes 폴더로 이동
kubectl apply -f deployment.yml -n ns-carsharing

- Service 배포 
kubectl apply -f service.yaml -n ns-carsharing

```
kubelctl get all -n ns-carsharing  결과
![image](https://user-images.githubusercontent.com/84000909/122359556-9d865980-cf90-11eb-9b56-be9227efd0c7.png)


## Config Map (Reservation 배포전에 선배포)

* Config Map을 환경변수 등록함
kubectl create configmap cm-carsharing --namespace="ns-carsharing" --from-literal=DB_IP=10.20.30.1 --from-literal=DB_SERVICE_NAME=CARS

kubectl get cm -n ns-carsharing
 
![image](https://user-images.githubusercontent.com/84000909/122322316-3fda1900-cf60-11eb-838d-895ee6df611a.png)


* 등록된 Config Map을 조회함
 
![image](https://user-images.githubusercontent.com/84000909/122325689-01475d00-cf66-11eb-9d44-54d89bfb82db.png)

## 서킷 브레이커 : 장애격리(예약 시스템 장애 발생시)

1. Istio 설치
```
$ curl -L https://istio.io/downloadIstio | ISTIO_VERSION=1.7.1 TARGET_ARCH=x86_64 sh -
$ cd istio-1.7.1
$ export PATH=$PWD/bin:$PATH
$ istioctl install --set profile=demo
1-1.  설치확인
$ kubectl get pod -n istio-system
```
2. Istio 모니터링 툴 설치
```
vi samples/addons/kiali.yaml

4라인의
apiVersion: apiextensions.k8s.io/v1beta1 을
apiVersion: apiextensions.k8s.io/v1으로 수정
kubectl apply -f samples/addons :오류가 날시는 아래의 명령어로 사용함
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.7/samples/addons/kiali.yaml

```

2-2. 모니터링 툴 설정

```
kubectl edit svc kiali -n istio-system
:%s/ClusterIP/LoadBalancer/g
:wq!
```
2-3. 모니터링 시스템 접속
![image](https://user-images.githubusercontent.com/84000909/122344535-df5bd380-cf81-11eb-9a2e-1bfb988cc3ea.png)
![image](https://user-images.githubusercontent.com/84000909/122344703-087c6400-cf82-11eb-9aa7-d3e240e04509.png)

```
http://20.41.97.46:20001/ (admin/admin)
```
3. 네임스페이스 생성
```
kubectl create namespace istio-test-ns
kubectl label namespace istio-test-ns istio-injection=enabled

label에 istio-injection enabled 확인
kubectl get namespace istio-test-ns -o yaml

```
![image](https://user-images.githubusercontent.com/84000909/122345051-61e49300-cf82-11eb-9922-bab4aa353e68.png)




4 namespace로 서비스 재배포
```
kubectl create deploy gateway --image=user04skccacr.azurecr.io/carsharing-gateway:latest -n istio-test-ns
kubectl create deploy reservation --image=user04skccacr.azurecr.io/carsharing-reservation:latest -n istio-test-ns
kubectl create deploy rental --image=user04skccacr.azurecr.io/carsharing-rental:latest -n istio-test-ns
kubectl create deploy payment --image=user04skccacr.azurecr.io/carsharing-payment:latest -n istio-test-ns
kubectl create deploy customer --image=user04skccacr.azurecr.io/carsharing-customer:latest -n istio-test-ns
kubectl expose deploy gatewayn --type="LoadBalancer" --port=8080 -n istio-test-ns
kubectl expose deploy reservation --type="LoadBalancer" --port=8080 -n istio-test-ns
kubectl expose deploy rental --type="LoadBalancer" --port=8080 -n istio-test-ns
kubectl expose deploy payment --type="LoadBalancer" --port=8080 -n istio-test-ns
kubectl expose deploy customer --type="LoadBalancer" --port=8080 -n istio-test-ns

kubectl create deploy siege --image=apexacme/siege-nginx -n  istio-test-ns
```
생성된 Container 확인
![image](https://user-images.githubusercontent.com/84000909/122350024-c9e9a800-cf87-11eb-982d-eaba2cf13c5b.png)



5. Circuit Breaker Destination Rule 생성
```
kubectl apply -f - <<EOF
  apiVersion: networking.istio.io/v1alpha3
  kind: DestinationRule
  metadata:
    name: dr-httpbin
    namespace: istio-test-ns
  spec:
    host: gateway
    trafficPolicy:
      connectionPool:
        http:
          http1MaxPendingRequests: 1
          maxRequestsPerConnection: 1
EOF

```
5-1. Siege Client 접속

```
kubectl exec -it siege-88f7fdd8d-k45jb -n istio-test-ns -- /bin/bash
```

IP주소 확인
![image](https://user-images.githubusercontent.com/84000909/122350982-a8d58700-cf88-11eb-97d2-1847405c0153.png)

정상 동작일떄 확인
siege -c1 -t30S -v --content-type "application/json" 'http://20.194.98.16:8080/reservations POST {"carId": "g90", "amonut": "1"}'
![image](https://user-images.githubusercontent.com/84000909/122351081-c3a7fb80-cf88-11eb-9658-e3675b8da901.png)


Circuit Breaker 동작 확인
siege -c2 -t50S -v --content-type "application/json" 'http://20.194.98.16:8080/reservations POST {"carId": "g80", "amonut": "1"}'
![image](https://user-images.githubusercontent.com/84000909/122351313-fbaf3e80-cf88-11eb-9fd2-f50d019cc7d5.png)


6.모니터링 시스템 (kiali)에서 확인
![image](https://user-images.githubusercontent.com/34739884/122333511-41610c80-cf73-11eb-8cf8-47b16dd2941b.png)
![image](https://user-images.githubusercontent.com/84000909/122505691-d3cae400-d037-11eb-8dc6-ccd7212efd0a.png)


운영시스템은 죽지 않고 지속적으로 CB 에 의하여 적절히 회로가 열림과 닫힘이 벌어지면서 자원을 보호하고 있음을 보여줌. 하지만, 62.45% 가 성공하였고, 37%가 실패했다는 것은 고객 사용성에 있어 좋지 않기 때문에 Retry 설정과 동적 Scale out (replica의 자동적 추가,HPA) 을 통하여 시스템을 확장 해주는 후속처리가 필요.



### 오토스케일
앞서 CB 는 시스템을 안정되게 운영할 수 있게 해줬지만 사용자의 요청을 100% 받아들여주지 못했기 때문에 이에 대한 보완책으로 자동화된 확장 기능을 적용하고자 한다. 

- Reservation Deloypment.yaml 의 오토스케일을 위한 pod 초기 cpu, max cpu 설정 적용
![image](https://user-images.githubusercontent.com/84000909/122338737-0f53a880-cf7b-11eb-8d56-cf07a91f08be.png)

- 예약 서비스 재배포
  kubectl apply -f deployment.yml -n ns-carsharing
- 예약서비스에 대한 replica 를 동적으로 늘려주도록 HPA 를 설정한다. 설정은 CPU 사용량이 50프로를 넘어서면 replica 를 10개까지 늘려준다:
  kubectl autoscale deploy reservation --cpu-percent=50 --min=1 --max=10 -n ns-carsharing

- 부하(siege) 배포 
```
kubectl apply -f - <<EOF
  apiVersion: v1
  kind: Pod
  metadata:
    name: siege
    namespace: ns-carsharing
  spec:
    containers:
    - name: siege
      image: apexacme/siege-nginx
EOF
```
- 부하(siege) 노드 내부로 들어간다.
kubectl exec -it siege -n ns-carsharing -- /bin/bash

- 부하(siege) 노드 내부에서 부하를 발생시킨다.
siege -c200 -t30S -v http://10.0.206.105:8080/reservations

![image](https://user-images.githubusercontent.com/84000909/122342775-f39ed100-cf7f-11eb-908e-814cf910f4f6.png)


- 오토스케일이 어떻게 되고 있는지 새로운 터미널에서 모니터링을 걸어둔다:

watch -n 1 kubectl get pod -n ns-carsharing
![image](https://user-images.githubusercontent.com/84000909/122340681-8b4ef000-cf7d-11eb-8df2-1338c714f787.png)





![image](https://user-images.githubusercontent.com/84000909/122340857-c3563300-cf7d-11eb-9700-7b0f1d6e9d7a.png)

- 어느정도 시간이 흐른 후 (약 30초) 스케일 아웃이 벌어지는 것을 확인할 수 있다:
- ![image](https://user-images.githubusercontent.com/84000909/122339326-e41d8900-cf7b-11eb-9d77-49cadf242c6c.png)

- siege 의 로그를 보아도 전체적인 성공률이 높아진 것을 확인 할 수 있다. 
![image](https://user-images.githubusercontent.com/84000909/122341079-0b755580-cf7e-11eb-9f75-c0548f033594.png)


## 무정지 재배포 - ( Liveness & Readiness )

* 먼저 무정지 재배포가 100% 되는 것인지 확인하기 위해서 Autoscaler 이나 CB 설정을 제거함

- seige 로 배포작업 직전에 모니터링 함.
```
siege -c60 -t60S -r10 -v http get http://reservation:8080/reservations
```

- 새버전으로의 배포 시작
```
kubectl apply -f live_deployment.yml -n ns-carsharing
```
![image](https://user-images.githubusercontent.com/84000909/122495749-8e9db680-d025-11eb-81f4-833b667b76b2.png)

- seige 의 화면으로 넘어가서 Availability 가 100% 미만으로 떨어졌는지 확인
![image](https://user-images.githubusercontent.com/84000909/122493763-31a10100-d023-11eb-8e92-cb0fd2a91a1c.png)
![image](https://user-images.githubusercontent.com/84000909/122493714-1df59a80-d023-11eb-9c12-b5d4c40d61b7.png)

![image](https://user-images.githubusercontent.com/84000909/122493957-85134f00-d023-11eb-982a-b7d7a9fa0348.png)
![image](https://user-images.githubusercontent.com/84000909/122495918-dc1a2380-d025-11eb-9b8c-27f8d5371949.png)


배포기간중 Availability 가 평소 100%에서 39% 대로 떨어지는 것을 확인. 원인은 쿠버네티스가 성급하게 새로 올려진 서비스를 READY 상태로 인식하여 서비스 유입을 진행한 것이기 때문. 이를 막기위해 Readiness Probe 를 설정함:

```
# deployment.yaml 의 readiness probe 의 설정:
```
![image](https://user-images.githubusercontent.com/84000909/122496385-a3c71500-d026-11eb-9141-41f66c097049.png)

```
kubectl apply -f kubernetes/read_deployment.yaml
```

- 동일한 시나리오로 재배포 한 후 Availability 확인:


## Readiness 다시 작성

```
yaml파일 수정 및 배포 완료
```
![image](https://user-images.githubusercontent.com/84000909/122514646-38416f80-d047-11eb-8f4e-d3bd40d94607.png)


```
상태 확인을 위해 소스를 재 빌드함
```
![image](https://user-images.githubusercontent.com/84000909/122515117-ee0cbe00-d047-11eb-91a2-bc6c9bb76be1.png)
![image](https://user-images.githubusercontent.com/84000909/122515208-11376d80-d048-11eb-9d66-527bee6022c7.png)

```
신규 버전을 배포함
```
![image](https://user-images.githubusercontent.com/84000909/122515802-ed285c00-d048-11eb-9756-2b6df549c3e1.png)


```
최종 결과 확인
```
![image](https://user-images.githubusercontent.com/84000909/122516024-34165180-d049-11eb-903c-1153e9f05b07.png)

![image](https://user-images.githubusercontent.com/84000909/122516096-4abca880-d049-11eb-933c-2c25a85590b1.png)


배포기간 동안 Availability 가 변화없기 때문에 무정지 재배포가 성공한 것으로 확인됨.


# 신규 개발 조직의 추가 -- 이하 개인과제로 금번 조별 과제에서는 대상이 아니며, 개인과제 대비하여 문서는 남겨 놓음

  ![image](https://user-images.githubusercontent.com/487999/79684133-1d6c4300-826a-11ea-94a2-602e61814ebf.png)



## 마케팅팀의 추가
    - KPI: 신규 고객의 유입률 증대와 기존 고객의 충성도 향상
    - 구현계획 마이크로 서비스: 기존 customer 마이크로 서비스를 인수하며, 고객에 음식 및 맛집 추천 서비스 등을 제공할 예정

## 이벤트 스토밍 
    ![image](https://user-images.githubusercontent.com/487999/79685356-2b729180-8273-11ea-9361-a434065f2249.png)


## 헥사고날 아키텍처 변화 

![image](https://user-images.githubusercontent.com/487999/79685243-1d704100-8272-11ea-8ef6-f4869c509996.png)

## 구현  

기존의 마이크로 서비스에 수정을 발생시키지 않도록 Inbund 요청을 REST 가 아닌 Event 를 Subscribe 하는 방식으로 구현. 기존 마이크로 서비스에 대하여 아키텍처나 기존 마이크로 서비스들의 데이터베이스 구조와 관계없이 추가됨. 

## 운영과 Retirement

Request/Response 방식으로 구현하지 않았기 때문에 서비스가 더이상 불필요해져도 Deployment 에서 제거되면 기존 마이크로 서비스에 어떤 영향도 주지 않음.

* [비교] 결제 (pay) 마이크로서비스의 경우 API 변화나 Retire 시에 app(주문) 마이크로 서비스의 변경을 초래함:

예) API 변화시
```
# Order.java (Entity)

    @PostPersist
    public void onPostPersist(){

        fooddelivery.external.결제이력 pay = new fooddelivery.external.결제이력();
        pay.setOrderId(getOrderId());
        
        Application.applicationContext.getBean(fooddelivery.external.결제이력Service.class)
                .결제(pay);

                --> 

        Application.applicationContext.getBean(fooddelivery.external.결제이력Service.class)
                .결제2(pay);

    }
```

예) Retire 시
```
# Order.java (Entity)

    @PostPersist
    public void onPostPersist(){

        /**
        fooddelivery.external.결제이력 pay = new fooddelivery.external.결제이력();
        pay.setOrderId(getOrderId());
        
        Application.applicationContext.getBean(fooddelivery.external.결제이력Service.class)
                .결제(pay);

        **/
    }
```




