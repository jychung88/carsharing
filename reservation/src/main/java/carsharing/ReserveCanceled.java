
package carsharing;

public class ReserveCanceled extends AbstractEvent {

    private Long id;
    private String cancelDate;     

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 
    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }      
}

