
package carsharing;

public class DeliveryCanceled extends AbstractEvent {

    private Long id;
    private String reserveId;
    private String rentalCancelDate;

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
    public String getRentalCancelDate() {
        return rentalCancelDate;
    }

    public void setRentalCancelDate(String rentalCancelDate) {
        this.rentalCancelDate = rentalCancelDate;
    }
}

