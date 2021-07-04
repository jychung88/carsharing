package carsharing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyPageRepository extends CrudRepository<MyPage, Long> {

    List<MyPage> findByReserveId(String reserveId);
    //List<MyPage> findByReserveId(Long reserveId);

    List<MyPage> findByMileage(String mileage);
    List<MyPage> findByUserPhone(String userPhone);

}