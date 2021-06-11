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
        if (this.getReserveStatus() == "Reserved")
        {
            Reserved reserved = new Reserved();
            BeanUtils.copyProperties(this, reserved);
            reserved.publishAfterCommit();
            System.out.println("##### send event : Reserved  #####");   
        } 
        else if (this.getReserveStatus() == "ReserveCanceled")
        {
            ReserveCanceled reserveCanceled = new ReserveCanceled();
            BeanUtils.copyProperties(this, reserveCanceled);
            reserveCanceled.publishAfterCommit();
        }               
        else if (this.getReserveStatus() == "ReserveReturned")
        {
            ReserveReturned reserveReturned = new ReserveReturned();
            BeanUtils.copyProperties(this, reserveReturned);
            reserveReturned.publishAfterCommit();
            System.out.println("##### send event : ReserveReturned  #####");  
        }             
    }

    @PostRemove
    public void onPostRemove(){

    }

    @PrePersist
    public void onPrePersist(){
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
    public String getRentalAddr() {
        return rentalAddr;
    }

    public void setRentalAddr(String rentalAddr) {
        this.rentalAddr = rentalAddr;
    }
    public String getRetrieveAddr() {
        return retrieveAddr;
    }

    public void setRetrieveAddr(String retrieveAddr) {
        this.retrieveAddr = retrieveAddr;
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
    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }
    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }
    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }




}
