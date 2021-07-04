
package carsharing;

public class RentalCanceled extends AbstractEvent {

    private Long id;
    private String reserveId;
    private String rentCancelDate;

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
    public String getRentCancelDate() {
        return rentCancelDate;
    }

    public void setRentCancelDate(String rentCancelDate) {
        this.rentCancelDate = rentCancelDate;
    }
}

