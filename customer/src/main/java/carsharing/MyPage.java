package carsharing;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="MyPage_table")
public class MyPage {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long rentalId;
        private Long reserveId;
        private String carId;
        private String lentalAddr;
        private String retriveAddr;
        private String userPhone;
        private Long amount;
        private Long stock;
        private String reserveStatus;
        private String payType;
        private String payNumber;
        private String payCompany;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        
        public Long getRentalId() {
            return rentalId;
        }
    
        public void setRentalId(Long rentalId) {
            this.rentalId = rentalId;
        }
        
        public Long getReserveId() {
            return reserveId;
        }
    
        public void setReserveId(Long reserveId) {
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
    
        public Long getAmount() {
            return amount;
        }
    
        public void setAmount(Long amount) {
            this.amount = amount;
        }
    
        public String getReserveStatus() {
            return reserveStatus;
        }
    
        public void setReserveStatus(String reserveStatus) {
            this.reserveStatus = reserveStatus;
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
