package carsharing;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Delivery_table")
public class Delivery {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String reserveId;
    private String rentalAcceptDate;
    private String rentalCancelDate;
    private String rentalRetrieveDate;
    private String deleveryStatus;

    @PostPersist
    public void onPostPersist(){
        
        DeliveryStarted deliveryStarted = new DeliveryStarted();
        BeanUtils.copyProperties(this, deliveryStarted);
        deliveryStarted.publishAfterCommit();
    }
    @PostUpdate
    public void onPostUpdate(){
        

        if ("DeleveryRetrieved".equals(this.getDeleveryStatus())) {        
            DeliveryRetrieved deliveryRetrieved = new DeliveryRetrieved();
            BeanUtils.copyProperties(this, deliveryRetrieved);
            deliveryRetrieved.publishAfterCommit();
        }        
        else if ("DeleveryCancled".equals(this.getDeleveryStatus())) {  
            DeliveryCanceled deliveryCanceled = new DeliveryCanceled();
            BeanUtils.copyProperties(this, deliveryCanceled);
            deliveryCanceled.publishAfterCommit();
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
    public String getRentalAcceptDate() {
        return rentalAcceptDate;
    }

    public void setRentalAcceptDate(String rentalAcceptDate) {
        this.rentalAcceptDate = rentalAcceptDate;
    }
    public String getRentalCancelDate() {
        return rentalCancelDate;
    }

    public void setRentalCancelDate(String rentalCancelDate) {
        this.rentalCancelDate = rentalCancelDate;
    }

    public void setRentalRetrieveDate(String rentalRetrieveDate){
        this.rentalRetrieveDate = rentalRetrieveDate;
    }
    public String getRentalRetrieveDate(){
        return rentalRetrieveDate;
    }


    
    public void setDeleveryStatus(String deleveryStatus){
        this.deleveryStatus = deleveryStatus;
    }
    public String getDeleveryStatus(){
        return deleveryStatus;
    }
}
