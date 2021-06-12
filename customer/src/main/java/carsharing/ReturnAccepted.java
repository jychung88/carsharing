package carsharing;

public class ReturnAccepted extends AbstractEvent {

    private Long id;
    private String reserveId;
    private String retAcceptDate;

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
    public String getRetAcceptDate() {
        return retAcceptDate;
    }

    public void setRetAcceptDate(String retAcceptDate) {
        this.retAcceptDate = retAcceptDate;
    }
}