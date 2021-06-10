package carsharing;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Customer_table")
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String reserveid;
    private String amount;
    private String lentalAddr;
    private String retriveAddr;
    private String carid;
    private String returnDate;
    private String userPhone;
    private String status;
    private String reserveDate;

    @PostPersist
    public void onPostPersist(){
        StateChanged stateChanged = new StateChanged();
        BeanUtils.copyProperties(this, stateChanged);
        stateChanged.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getReserveid() {
        return reserveid;
    }

    public void setReserveid(String reserveid) {
        this.reserveid = reserveid;
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }
    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }




}
