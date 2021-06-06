package carsharing;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Optional;
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
    private String reserveStatus;

    @PostPersist
    public void onPostPersist(){

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        String reserveId = this.getId().toString();
        String carId = this.getCarId();
        String amount = this.getAmount().toString();
        String userPhone = this.getUserPhone();
        String payType = this.getPayType();
        String payNumber = this.getPayNumber();
        String payCompany = this.getPayCompany();

        System.out.println("##### onPostPersist  called #####");
        System.out.println("##### reserveId : " + reserveId);
        System.out.println("##### carId : " + carId);
        System.out.println("##### amount : " + amount);
        System.out.println("##### userPhone : " + userPhone);
        System.out.println("##### payType : " + payType);
        System.out.println("##### payNumber : " + payNumber);
        System.out.println("##### payCompany : " + payCompany);

        boolean ret = false;
        try {
            ret = ReservationApplication.applicationContext.getBean(carsharing.external.PaymentService.class)
                .pay(reserveId, carId, amount, userPhone, payType, payNumber, payCompany);

                System.out.println("##### /payment/pay  called result : " + ret);
            } catch (Exception e) {
            System.out.println("##### /payment/pay  called exception : " + e);
        }

        if (ret) {
            Reserved reserved = new Reserved();
            BeanUtils.copyProperties(this, reserved);
            reserved.publishAfterCommit();
            System.out.println("##### reservation suceess, send event  #####");    
        } else {
            System.out.println("##### reservation fail caused by pay fail #####");    
        }        

        Optional<Reservation> reservationOptional = ReservationApplication.applicationContext.getBean(carsharing.ReservationRepository.class)
            .findById(this.getId());

        if (reservationOptional.isEmpty() == false) {
            Reservation reservation = reservationOptional.get();

            if (ret) {
                reservation.setReserveStatus("Success");
            }
            else {
                reservation.setReserveStatus("Fail(PayFail)");
            }

            ReservationApplication.applicationContext.getBean(carsharing.ReservationRepository.class)
            .save(reservation);
        }
    }

    @PostUpdate
    public void onPostUpdate(){
        ReserveReturned reserveReturned = new ReserveReturned();
        BeanUtils.copyProperties(this, reserveReturned);
        reserveReturned.publishAfterCommit();


    }

    @PostRemove
    public void onPostRemove(){
        String reserveStatus = this.getReserveStatus();

        if (reserveStatus == null || "Success".equals(this.getReserveStatus())) {
            ReserveCanceled reserveCanceled = new ReserveCanceled();
            BeanUtils.copyProperties(this, reserveCanceled);
            reserveCanceled.publishAfterCommit();
        }
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
    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }




}
