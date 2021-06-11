package carsharing;

public class RentalRetrieved extends AbstractEvent {

    private Long id;
    private String reserveId;
    private String rentRetriveDate;

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
    public String getRentRetriveDate() {
        return rentRetriveDate;
    }

    public void setRentRetriveDate(String rentRetriveDate) {
        this.rentRetriveDate = rentRetriveDate;
    }
}