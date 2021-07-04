package carsharing;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Mileage_table")
public class Mileage {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String reserveId;
    private String mileage;

    @PostPersist
    public void onPostPersist(){
        MileageAccepted mileageAccepted = new MileageAccepted();
        BeanUtils.copyProperties(this, mileageAccepted);
        mileageAccepted.publishAfterCommit();

    }

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
    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }




}
