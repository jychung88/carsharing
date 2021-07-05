
package carsharing.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(name="payment", url="${api.payment.url}")  // payment url => http://localhost:8083
//@FeignClient(name="payment", url= "http://localhost:8083")
public interface PaymentService {

    @RequestMapping(method= RequestMethod.POST, path="/pay")        
    public boolean pay(@RequestParam("reserveId") String reserveId,
                @RequestParam("carId") String carId,
                @RequestParam("amount") String amount,
                @RequestParam("userPhone") String userPhone,
                @RequestParam("payType") String userpayType,
                @RequestParam("payNumber") String payNumber,
                @RequestParam("payCompany") String payCompany);

}
