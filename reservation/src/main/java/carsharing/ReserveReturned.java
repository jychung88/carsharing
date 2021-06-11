
package carsharing;

public class ReserveReturned extends AbstractEvent {

    private Long id;
    private String returnDate;     

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }      
}

