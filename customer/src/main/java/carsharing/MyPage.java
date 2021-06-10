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
        private String amount;
        private String reserveDate;
        private String lentalAddr;
        private String retriveAddr;
        private String carId;
        private String status;
        private String cancelDate;
        private String returnDate;


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
        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
        public String getReserveDate() {
            return reserveDate;
        }

        public void setReserveDate(String reserveDate) {
            this.reserveDate = reserveDate;
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
        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
