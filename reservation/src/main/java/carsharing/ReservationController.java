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
import java.util.Optional;

 @RestController
 public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @RequestMapping(value = "/env",
    method = RequestMethod.GET,
    produces = "application/json;charset=UTF-8")

    public void env(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("##### /env  called #####");

        String env_DB_IP = System.getenv("DB_IP");
        String env_DB_SERVICE_NAME = System.getenv("DB_SERVICE_NAME");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write("DB IP : " + env_DB_IP +"\r\n");
        writer.write("DB SERVICE_NAME : " + env_DB_SERVICE_NAME);
        writer.flush();
        writer.close();
    }

    @RequestMapping(value = "/reserve",
    method = RequestMethod.POST,
    produces = "application/json;charset=UTF-8")

    public String reserve(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("##### /reserve  called #####");
     
        String carId = request.getParameter("carId");
        String rentalAddr = request.getParameter("rentalAddr");
        String retrieveAddr = request.getParameter("retriveAddr");
        String userPhone = request.getParameter("userPhone");
        Long amount = Long.parseLong(request.getParameter("amount"));
        String reserveDate = request.getParameter("reserveDate");
        String payType = request.getParameter("payType");
        String payNumber = request.getParameter("payNumber");
        String payCompany = request.getParameter("payCompany");

        System.out.println("##### carId : " + carId);
        System.out.println("##### amount : " + amount);
        System.out.println("##### userPhone : " + userPhone);
        System.out.println("##### payType : " + payType);
        System.out.println("##### payNumber : " + payNumber);
        System.out.println("##### payCompany : " + payCompany);

        Reservation reservation = new Reservation();
        reservation.setCarId(carId);
        reservation.setRentalAddr(rentalAddr);
        reservation.setRetrieveAddr(retrieveAddr);
        reservation.setUserPhone(userPhone);
        reservation.setAmount(amount);
        reservation.setReserveDate(reserveDate);
        reservation.setPayType(payType);
        reservation.setPayNumber(payNumber);
        reservation.setPayCompany(payCompany);
                        
        reservation  = reservationRepository.save(reservation);

        String reserveId = Long.toString(reservation.getId());        
        System.out.println("##### reserveId : " + reserveId);

        boolean ret = false;
        try {
            ret = ReservationApplication.applicationContext.getBean(carsharing.external.PaymentService.class)
                .pay(reserveId, carId, String.valueOf(amount), userPhone, payType, payNumber, payCompany);

            System.out.println("##### /payment/pay  called result : " + ret);
        } catch (Exception e) {
            System.out.println("##### /payment/pay  called exception : " + e);
        }

        String status = "";
        if (ret) {
            status = "Reserved";
        } else {
            status = "ReserveFailed";
        } 

        reservation.setReserveStatus(status);
        reservation  = reservationRepository.save(reservation);

        return status + " ReserveNumber : " + reserveId;          
    }

    @RequestMapping(value = "/cancel",
    method = RequestMethod.POST,
    produces = "application/json;charset=UTF-8")

    public String cancel(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("##### /cancel  called #####");
     
        String reserveId = request.getParameter("reserveId");
        String cancelDate = request.getParameter("cancelDate");        
        System.out.println("##### reserveId : " + reserveId);
        System.out.println("##### cancelDate : " + cancelDate);        


        Optional<Reservation> reservationOptional = ReservationApplication.applicationContext.getBean(carsharing.ReservationRepository.class)
                  .findById(Long.parseLong(reserveId));

        String status = "ReserveCanceled";          

        if (reservationOptional.isEmpty() == false) {
            Reservation reservation = reservationOptional.get();
            String curStatus = reservation.getReserveStatus();
            if ("Reserved".equals(curStatus)) {
                reservation.setCancelDate(cancelDate);
                reservation.setReserveStatus(status);
                ReservationApplication.applicationContext.getBean(carsharing.ReservationRepository.class)
                .save(reservation);
                }
            else {
                status = "reserve status is not Reserved(current : " + curStatus + ")"; 
            }
        }
        else{
            status = "not found"; 
        }     

        return status + " ReserveNumber : " + reserveId;          
    }

    @RequestMapping(value = "/resreturn",
    method = RequestMethod.POST,
    produces = "application/json;charset=UTF-8")

    public String resreturn(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("##### /resreturn  called #####");
     
        String reserveId = request.getParameter("reserveId");
        String returnDate = request.getParameter("returnDate");        
        System.out.println("##### reserveId : " + reserveId);
        System.out.println("##### returnDate : " + returnDate);

        Optional<Reservation> reservationOptional = ReservationApplication.applicationContext.getBean(carsharing.ReservationRepository.class)
                  .findById(Long.parseLong(reserveId));

        String status = "ReserveReturned";                     
        if (reservationOptional.isEmpty() == false) {
            Reservation reservation = reservationOptional.get();
            String curStatus = reservation.getReserveStatus();
            if ("Reserved".equals(curStatus)) {            
                reservation.setReturnDate(returnDate);
                reservation.setReserveStatus(status);

                ReservationApplication.applicationContext.getBean(carsharing.ReservationRepository.class)
                .save(reservation);
            }
            else {
                status = "reserve status is not Reserved(current : " + curStatus + ")"; 
            }            
        }   
        else{
            status = "not found"; 
        } 

        return status + " ReserveNumber : " + reserveId;          
    }   
}

