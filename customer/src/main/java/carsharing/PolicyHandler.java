package carsharing;

import carsharing.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired CustomerRepository customerRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCanceled_ChangeStatus(@Payload PayCanceled payCanceled){

        if(!payCanceled.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + payCanceled.toJson() + "\n\n");

        String reserveId = payCanceled.getReserveId();
        Customer customer = customerRepository.findByReserveId(reserveId);
        if (customer != null) {
            customer.setStatus("PayCanceled");
            customer.setPayCancelDate(payCanceled.getPayCancelDate());            
            customerRepository.save(customer); 

            System.out.println("##### pay status changed by 'payCanceled' #####");
            System.out.println("reserveId : " + reserveId); 
        }          
        else{
            System.out.println("not found reserveId : " + reserveId);    
        } 
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveReturned_ChangeStatus(@Payload ReserveReturned reserveReturned){

        if(!reserveReturned.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + reserveReturned.toJson() + "\n\n");

        String reserveId = reserveReturned.getId().toString();
        Customer customer = customerRepository.findByReserveId(reserveId);
        if (customer != null) {
            customer.setStatus("ReserveReturned");
            customer.setReturnDate(reserveReturned.getReturnDate());            
            customerRepository.save(customer); 

            System.out.println("##### reserve status changed by 'reserveReturned' #####");
            System.out.println("reserveId : " + reserveId); 
        }          
        else{
            System.out.println("not found reserveId : " + reserveId);    
        }  
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_ChangeStatus(@Payload Reserved reserved){

        if(!reserved.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + reserved.toJson() + "\n\n");

        String reserveId = Long.toString(reserved.getId());
        String carId = reserved.getCarId();
        String rentalAddr = reserved.getRentalAddr();
        String retrieveAddr = reserved.getRetrieveAddr();
        String userPhone = reserved.getUserPhone();
        Long amount = reserved.getAmount();
        String reserveDate = reserved.getReserveDate();

        Customer customer = new Customer();
        customer.setReserveId(reserveId);
        customer.setCarId(carId);
        customer.setRentalAddr(rentalAddr);
        customer.setRetrieveAddr(retrieveAddr);
        customer.setUserPhone(userPhone);
        customer.setAmount(amount);
        customer.setReserveDate(reserveDate);
        customer.setStatus("Reserved");
        customerRepository.save(customer);         

        System.out.println("##### customer saved by 'reserved' #####");
        System.out.println("reserveId : " + reserveId); 
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveCanceled_ChangeStatus(@Payload ReserveCanceled reserveCanceled){

        if(!reserveCanceled.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + reserveCanceled.toJson() + "\n\n");

        String reserveId = reserveCanceled.getId().toString();
        Customer customer = customerRepository.findByReserveId(reserveId);
        if (customer != null) {
            customer.setStatus("ReserveCanceled");
            customer.setCancelDate(reserveCanceled.getCancelDate());            
            customerRepository.save(customer); 

            System.out.println("##### reserve status changed by 'reserveCanceled' #####");
            System.out.println("reserveId : " + reserveId); 
        }          
        else{
            System.out.println("not found reserveId : " + reserveId);    
        } 
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalCanceled_ChangeStatus(@Payload RentalCanceled rentalCanceled){

        if(!rentalCanceled.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + rentalCanceled.toJson() + "\n\n");

        String reserveId = rentalCanceled.getReserveId();
        Customer customer = customerRepository.findByReserveId(reserveId);
        if (customer != null) {
            customer.setStatus("RentalCanceled");
            customer.setRentCancelDate(rentalCanceled.getRentCancelDate());            
            customerRepository.save(customer); 

            System.out.println("##### rental status changed by 'rentalCanceled' #####");
            System.out.println("reserveId : " + reserveId); 
        }          
        else{
            System.out.println("not found reserveId : " + reserveId);    
        } 
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalRetrieved_ChangeStatus(@Payload RentalRetrieved rentalRetrieved){

        if(!rentalRetrieved.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + rentalRetrieved.toJson() + "\n\n");

        String reserveId = rentalRetrieved.getReserveId();
        Customer customer = customerRepository.findByReserveId(reserveId);
        if (customer != null) {
            customer.setStatus("RentalRetrieved");
            customer.setRentRetrieveDate(rentalRetrieved.getRentRetrieveDate());            
            customerRepository.save(customer); 

            System.out.println("##### rental status changed by 'rentalRetrieved' #####");
            System.out.println("reserveId : " + reserveId); 
        }          
        else{
            System.out.println("not found reserveId : " + reserveId);    
        } 
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalAccepted_ChangeStatus(@Payload RentalAccepted rentalAccepted){

        if(!rentalAccepted.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + rentalAccepted.toJson() + "\n\n");

        String reserveId = rentalAccepted.getReserveId();
        Customer customer = customerRepository.findByReserveId(reserveId);
        if (customer != null) {
            customer.setStatus("RentalAccepted");
            customer.setRentAcceptDate(rentalAccepted.getRentAcceptDate());            
            customerRepository.save(customer); 

            System.out.println("##### rental status changed by rental 'rentalAccepted' #####");
            System.out.println("reserveId : " + reserveId); 
        }          
        else{
            System.out.println("not found reserveId : " + reserveId);    
        }
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentaled_ChangeStatus(@Payload Rentaled rentaled){

        if(!rentaled.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + rentaled.toJson() + "\n\n");

        String reserveId = rentaled.getReserveId();
        Customer customer = customerRepository.findByReserveId(reserveId);
        if (customer != null) {
            customer.setStatus("Rentaled");            
            customer.setRentalDate(rentaled.getRentalDate());            
            customerRepository.save(customer); 

            System.out.println("##### rental status changed by 'rentaled'' #####");
            System.out.println("reserveId : " + reserveId); 
        }          
        else{
            System.out.println("not found reserveId : " + reserveId);    
        } 
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReturnAccepted_ChangeStatus(@Payload ReturnAccepted returnAccepted){

        if(!returnAccepted.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + returnAccepted.toJson() + "\n\n");

        String reserveId = returnAccepted.getReserveId();
        Customer customer = customerRepository.findByReserveId(reserveId);
        if (customer != null) {
            customer.setStatus("ReturnAccepted");             
            customer.setRetAcceptDate(returnAccepted.getRetAcceptDate());            
            customerRepository.save(customer); 

            System.out.println("##### rental status changed by 'returnAccepted' #####");
            System.out.println("reserveId : " + reserveId); 
        }          
        else{
            System.out.println("not found reserveId : " + reserveId);    
        } 
            
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
