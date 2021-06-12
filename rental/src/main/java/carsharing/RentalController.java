package carsharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

 @RestController
 public class RentalController {

    @RequestMapping(value = "/rentaled",
    method = RequestMethod.POST,
    produces = "application/json;charset=UTF-8")

    public String rentaled(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("##### /rentaled  called #####");
     
        String reserveId = request.getParameter("reserveId");
        String rentalDate = request.getParameter("rentalDate");        
        System.out.println("##### reserveId : " + reserveId);
        System.out.println("##### rentalDate : " + rentalDate);

        Optional<Rental> rentalOptional = RentalApplication.applicationContext.getBean(carsharing.RentalRepository.class)
                  .findById(Long.parseLong(reserveId));

        String status = "Rentaled";                     
        if (rentalOptional.isEmpty() == false) {
            Rental rental = rentalOptional.get();
            String curStatus = rental.getRentalStatus();            
            if ("RentalAccepted".equals(curStatus)) {            
                rental.setRentalDate(rentalDate);
                rental.setRentalStatus(status);

                RentalApplication.applicationContext.getBean(carsharing.RentalRepository.class)
                .save(rental);
            }
            else {
                status = "reserve status is not RentalAccepted(current : " + curStatus + ")"; 
            }
        }   
        else{
            status = "not found reserveId : " + reserveId; 
        } 

        return status; 
    }

    @RequestMapping(value = "/retrieved",
    method = RequestMethod.POST,
    produces = "application/json;charset=UTF-8")

    public String retrieved(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("##### /retrieved  called #####");
     
        String reserveId = request.getParameter("reserveId");
        String retrieveDate = request.getParameter("retrieveDate");        
        System.out.println("##### reserveId : " + reserveId);
        System.out.println("##### retrieveDate : " + retrieveDate);

        Optional<Rental> rentalOptional = RentalApplication.applicationContext.getBean(carsharing.RentalRepository.class)
                  .findById(Long.parseLong(reserveId));

        String status = "RentalRetrieved";                     
        if (rentalOptional.isEmpty() == false) {
            Rental rental = rentalOptional.get();
            String curStatus = rental.getRentalStatus();            
            if ("ReturnAccepted".equals(curStatus)) {              
                rental.setRentRetrieveDate(retrieveDate);
                rental.setRentalStatus(status);

                RentalApplication.applicationContext.getBean(carsharing.RentalRepository.class)
                .save(rental);
            }
            else {
                status = "reserve status is not ReturnAccepted(current : " + curStatus + ")"; 
            }            

        }   
        else{
            status = "not found reserveId : " + reserveId; 
        } 

        return status; 
    }   

 }
