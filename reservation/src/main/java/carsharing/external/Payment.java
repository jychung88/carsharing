package carsharing.external;

public class Payment {

    private Long id;
    private String reserveId;
    private String carId;
    private String amount;
    private String userPhone;
    private String payType;
    private String payNumber;
    private String payCompany;

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
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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
