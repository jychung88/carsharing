package carsharing;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="MyPage_table")
public class MyPage {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String reserveId;
        private Long amount;
        private String reserveDate;
        private String rentalAddr;
        private String retrieveAddr;
        private String carId;
        private String cancelDate;
        private String returnDate;
        private String rentalDate;
        private String rentAcceptDate;
        private String rentCancelDate;
        private String retAcceptDate;
        private String rentRetrieveDate;
        private String payCancelDate;
        private String status;
        private String userPhone;


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
        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
        public String getReserveDate() {
            return reserveDate;
        }

        public void setReserveDate(String reserveDate) {
            this.reserveDate = reserveDate;
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
        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
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
        public String getRentalDate() {
            return rentalDate;
        }

        public void setRentalDate(String rentalDate) {
            this.rentalDate = rentalDate;
        }
        public String getRentAcceptDate() {
            return rentAcceptDate;
        }

        public void setRentAcceptDate(String rentAcceptDate) {
            this.rentAcceptDate = rentAcceptDate;
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
        public String getPayCancelDate() {
            return payCancelDate;
        }

        public void setPayCancelDate(String payCancelDate) {
            this.payCancelDate = payCancelDate;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        
        public String getUserPhone() {
            return userPhone;
        }
    
        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }
}
