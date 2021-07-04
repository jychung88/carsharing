package carsharing;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Rental_table")
public class Rental {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String reserveId;
    private String carId;
    private String rentalAddr;
    private String retrieveAddr;
    private String userPhone;
    private Long amount;
    private String payType;
    private String payNumber;
    private String payCompany;
    private String reserveDate;
    private String resrvCancelDate;
    private String resrvReturnDate;
    private String rentAcceptDate;
    private String rentalDate;
    private String rentCancelDate;
    private String retAcceptDate;
    private String rentRetrieveDate;
    private String rentalStatus;

    @PostPersist
    public void onPostPersist(){
        if ("RentalAccepted".equals(this.getRentalStatus())) {        
            RentalAccepted rentalAccepted = new RentalAccepted();
            BeanUtils.copyProperties(this, rentalAccepted);
            rentalAccepted.publishAfterCommit();
        }
    }

    @PostUpdate
    public void onPostUpdate(){
        /*
        if ("Rentaled".equals(this.getRentalStatus())) {        
            Rentaled rentaled = new Rentaled();
            BeanUtils.copyProperties(this, rentaled);
            rentaled.publishAfterCommit();
        }
        else */
        if ("RentalCanceled".equals(this.getRentalStatus())) {        
            RentalCanceled rentalCanceled = new RentalCanceled();
            BeanUtils.copyProperties(this, rentalCanceled);
            rentalCanceled.publishAfterCommit(); 
        }        
        else if ("ReturnAccepted".equals(this.getRentalStatus())) {  
            ReturnAccepted returnAccepted = new ReturnAccepted();
            BeanUtils.copyProperties(this, returnAccepted);
            returnAccepted.publishAfterCommit();
        }/*
        else if ("RentalRetrieved".equals(this.getRentalStatus())) {  
            RentalRetrieved rentalRetrieved = new RentalRetrieved();
            BeanUtils.copyProperties(this, rentalRetrieved);
            rentalRetrieved.publishAfterCommit();
        }
        */
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
    public String getRentalAddr() {
        return rentalAddr;
    }

    public void setRentalAddr(String rentalAddr) {
        this.rentalAddr = rentalAddr;
    }
    public String getRetrieveAddr() {
        return retrieveAddr;
    }

    public void setRetrieveAddr(String retriveAddr) {
        this.retrieveAddr = retriveAddr;
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
    public String getResrvCancelDate() {
        return resrvCancelDate;
    }

    public void setResrvCancelDate(String resrvCancelDate) {
        this.resrvCancelDate = resrvCancelDate;
    }
    public String getResrvReturnDate() {
        return resrvReturnDate;
    }

    public void setResrvReturnDate(String resrvReturnDate) {
        this.resrvReturnDate = resrvReturnDate;
    }
    public String getRentAcceptDate() {
        return rentAcceptDate;
    }

    public void setRentAcceptDate(String rentAcceptDate) {
        this.rentAcceptDate = rentAcceptDate;
    }
    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }
    public String getRentCancelDate() {
        return rentCancelDate;
    }

    public void setRentCancelDate(String rentCancelDate) {
        this.rentCancelDate = rentCancelDate;
    }
    public String getRetAcceptDate() {
        return retAcceptDate;
    }

    public void setRetAcceptDate(String retAcceptDate) {
        this.retAcceptDate = retAcceptDate;
    }
    public String getRentRetrieveDate() {
        return rentRetrieveDate;
    }

    public void setRentRetrieveDate(String rentRetrieveDate) {
        this.rentRetrieveDate = rentRetrieveDate;
    }
    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }




}
