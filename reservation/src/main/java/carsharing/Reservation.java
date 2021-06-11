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
    private String reserveDate;    
    private String cancelDate;    
    private String returnDate; 


    @PostPersist
    public void onPostPersist(){

    }


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
}
