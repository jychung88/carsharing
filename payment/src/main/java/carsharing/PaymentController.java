package carsharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 @RestController
 public class PaymentController {

        @Autowired
        PaymentRepository paymentRepository;

@RequestMapping(value = "/pay",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")

public boolean pay(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
                boolean ret = false;

                String reserveId = request.getParameter("reserveId").toString();
                String carId = request.getParameter("carId").toString();
                String amount = request.getParameter("amount").toString();
                String userPhone = request.getParameter("userPhone").toString();
                String payType = request.getParameter("payType").toString();
                String payNumber = request.getParameter("payNumber").toString();
                String payCompany = request.getParameter("payCompany").toString();

                System.out.println("##### /payment/pay  called #####");
                System.out.println("##### reserveId : " + reserveId);
                System.out.println("##### carId : " + carId);
                System.out.println("##### amount : " + amount);
                System.out.println("##### userPhone : " + userPhone);
                System.out.println("##### payType : " + payType);
                System.out.println("##### payNumber : " + payNumber);
                System.out.println("##### payCompany : " + payCompany);

                Payment payment = new Payment();
                payment.setReserveId(reserveId);
                payment.setCarId(carId);
                payment.setAmount(amount);
                payment.setUserPhone(userPhone);
                payment.setPayType(payType);
                payment.setPayNumber(payNumber);
                payment.setPayCompany(payCompany);
                payment.setPayStatus("Payed");
                                
                payment  = paymentRepository.save(payment);

                if (payment != null) {
                        System.out.println("##### /payment/pay  save success #####");
                        ret = true;
                } else {
                        System.out.println("##### /payment/pay  save fail #####");
                        ret = false;
                }
                
                return ret;
        }

 }
