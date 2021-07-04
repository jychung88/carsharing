package carsharing;

public class DeliveryRetrieved extends AbstractEvent {

    private Long id;
    private String reserveId;
    private String rentRetrieveDate;

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
    public String getRentRetrieveDate() {
        return rentRetrieveDate;
    }

    public void setRentRetrieveDate(String rentRetrieveDate) {
        this.rentRetrieveDate = rentRetrieveDate;
    }
}