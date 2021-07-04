package carsharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.List;

 @RestController
 public class CustomerController {
    @Autowired
    private MyPageRepository myPageRepository;

    @RequestMapping(value = "/mypage",
    method = RequestMethod.GET,
    produces = "application/json;charset=UTF-8")

    public void mypage(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("##### /myPage  called #####");

        String reserveId = request.getParameter("reserveId").toString();
        String userPhone = request.getParameter("userPhone").toString();

        System.out.println("##### reserveId : " + reserveId);
        System.out.println("##### userPhone : " + userPhone);

        List<MyPage> myPageList = null;
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        
        if (reserveId != "") {
            myPageList = myPageRepository.findByReserveId(reserveId);
        }
        else {
            myPageList = myPageRepository.findByUserPhone(userPhone);
        }

        if (myPageList.size() > 0) {

            for(int i = 0; i < myPageList.size(); i++) {
                MyPage myPage = myPageList.get(i);

                reserveId = myPage.getReserveId();
                userPhone = myPage.getUserPhone();
                String statsus = myPage.getStatus();
                String carId = myPage.getCarId();
                String rentalAddr = myPage.getRentalAddr();
                String retrieveAddr = myPage.getRetrieveAddr();
                Long amount = myPage.getAmount();
                String reserveDate = myPage.getReserveDate();
                String cancelDate = myPage.getCancelDate();
                String returnDate = myPage.getReturnDate();
                String rentalDate = myPage.getRentalDate();
                String rentAcceptDate = myPage.getRentAcceptDate();
                String rentCancelDate = myPage.getRentCancelDate();
                String retAcceptDate = myPage.getRetAcceptDate();
                String retRetrieveDate = myPage.getRentRetrieveDate();
                String payCancelDate = myPage.getPayCancelDate();
                String mileageStr =myPage.getMileage();

                writer.write(i + " : ");
                writer.write("reserve Number=" + reserveId);
                writer.write(",userPhone=" + userPhone);
                writer.write(",status=" + statsus);
                writer.write(",carId=" + carId);
                writer.write(",rentalAddr=" + rentalAddr);
                writer.write(",retrieveAddr=" + retrieveAddr);
                writer.write(",amount=" + amount);
                writer.write(",reserveDate=" + reserveDate);
                if (returnDate != null) {
                    writer.write(",returnDate=" + returnDate);
                }
                if (cancelDate != null) {
                    writer.write(",cancelDate=" + cancelDate);
                }
                if (rentAcceptDate != null) {
                    writer.write(",rentAcceptDate=" + rentAcceptDate);
                }
                if (rentalDate != null) {
                    writer.write(",rentalDate=" + rentalDate );
                }
                if (retAcceptDate != null) {
                    writer.write(",retAcceptDate=" + retAcceptDate);
                }
                if (retRetrieveDate != null) {
                    writer.write(",retRetrieveDate=" + retRetrieveDate);
                }
                if (rentCancelDate != null) {
                    writer.write(",rentCancelDate=" + rentCancelDate);
                }
                if (payCancelDate != null) {
                    writer.write(",payCancelDate=" + payCancelDate);
                }
                writer.write(",mileage=" + mileageStr);
                

                writer.write("\r\n");
            }
        }   
        else{
            if (reserveId != "") {
                writer.write("not found ReserveNumber : " + reserveId + "\r\n");
            }
            else {
                writer.write("not found UserPhone : " + userPhone + "\r\n");
            }            
        } 

        writer.flush();
        writer.close();        
    }    
 }
