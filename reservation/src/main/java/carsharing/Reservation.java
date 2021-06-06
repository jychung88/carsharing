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
    private String lentalAddr;
    private String retriveAddr;
    private String userPhone;
    private Long amount;
    private String payType;
    private String payNumber;
    private String payCompany;

    @PostPersist
    public void onPostPersist(){
        Reserved reserved = new Reserved();
        BeanUtils.copyProperties(this, reserved);
        reserved.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        //carsharing.external.Payment payment = new carsharing.external.Payment();
        // mappings goes here
        ////Application.applicationContext.getBean(carsharing.external.PaymentService.class)
        //    .pay(payment);


    }

    @PostUpdate
    public void onPostUpdate(){
        ReserveReturned reserveReturned = new ReserveReturned();
        BeanUtils.copyProperties(this, reserveReturned);
        reserveReturned.publishAfterCommit();


    }

    @PostRemove
    public void onPostRemove(){
        ReserveCanceled reserveCanceled = new ReserveCanceled();
        BeanUtils.copyProperties(this, reserveCanceled);
        reserveCanceled.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
    public String getLentalAddr() {
        return lentalAddr;
    }

    public void setLentalAddr(String lentalAddr) {
        this.lentalAddr = lentalAddr;
    }
    public String getRetriveAddr() {
        return retriveAddr;
    }

    public void setRetriveAddr(String retriveAddr) {
        this.retriveAddr = retriveAddr;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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




}
