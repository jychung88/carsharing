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
    private Long reserveId;
    private String carId;
    private String lentalAddr;
    private String retriveAddr;
    private String userPhone;
    private Long stock;
    private String rentalStatus;

    @PostPersist
    public void onPostPersist(){
        Rentaled rentaled = new Rentaled();
        BeanUtils.copyProperties(this, rentaled);
        rentaled.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){
        RentalRetrieved rentalRetrieved = new RentalRetrieved();
        BeanUtils.copyProperties(this, rentalRetrieved);
        rentalRetrieved.publishAfterCommit();


    }

    @PostRemove
    public void onPostRemove(){
        RentalCanceled rentalCanceled = new RentalCanceled();
        BeanUtils.copyProperties(this, rentalCanceled);
        rentalCanceled.publishAfterCommit();


    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getreserveId() {
        return reserveId;
    }

    public void setreserveId(Long reserveId) {
        this.reserveId = reserveId;
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

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

}
