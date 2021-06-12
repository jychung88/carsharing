package carsharing;

import carsharing.config.kafka.KafkaProcessor;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired RentalRepository rentalRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_AcceptRental(@Payload Reserved reserved){

        if(!reserved.validate()) return;

        System.out.println("\n\n##### listener AcceptRental : " + reserved.toJson() + "\n\n");

        String reserveId = Long.toString(reserved.getId());
        String carId = reserved.getCarId();
        String rentalAddr = reserved.getRentalAddr();
        String retrieveAddr = reserved.getRetrieveAddr();
        String userPhone = reserved.getUserPhone();
        Long amount = reserved.getAmount();
        String payType = reserved.getPayType();
        String payNumber = reserved.getPayNumber();
        String payCompany = reserved.getPayCompany();
        String reserveDate = reserved.getReserveDate();

        Rental rental = new Rental();
        rental.setReserveId(reserveId);
        rental.setCarId(carId);
        rental.setRentalAddr(rentalAddr);
        rental.setRetrieveAddr(retrieveAddr);
        rental.setUserPhone(userPhone);
        rental.setAmount(amount);
        rental.setPayType(payType);
        rental.setPayNumber(payNumber);
        rental.setPayCompany(payCompany);
        rental.setReserveDate(reserveDate);
        LocalDate localDate = LocalDate.now();                
        rental.setRentAcceptDate(localDate.toString());
        rental.setRentalStatus("RentalAccepted");
        rentalRepository.save(rental);           

        System.out.println("##### rental accepted by reservation reserve #####");
        System.out.println("reserveId : " + reserveId);             
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveCanceled_CancelRental(@Payload ReserveCanceled reserveCanceled){

        if(!reserveCanceled.validate()) return;

        System.out.println("\n\n##### listener CancelRental : " + reserveCanceled.toJson() + "\n\n");


        String reserveId = reserveCanceled.getId().toString();
        Rental rental = rentalRepository.findByReserveId(reserveId);
        if (rental != null) {
            rental.setRentalStatus("RentalCanceled");
            LocalDate localDate = LocalDate.now();                
            rental.setRentCancelDate(localDate.toString());            
            rentalRepository.save(rental); 

            System.out.println("##### lental canceld by reservation cancel #####");
            System.out.println("reserveId : " + reserveId);    
        }
        else{
            System.out.println("not found reserveId : " + reserveId);    
        }                   
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveReturned_AcceptReturn(@Payload ReserveReturned reserveReturned){

        if(!reserveReturned.validate()) return;

        System.out.println("\n\n##### listener AcceptReturn : " + reserveReturned.toJson() + "\n\n");

        String reserveId = reserveReturned.getId().toString();
        Rental rental = rentalRepository.findByReserveId(reserveId);
        if (rental != null) {
            rental.setRentalStatus("ReturnAccepted");
            LocalDate localDate = LocalDate.now();                
            rental.setRetAcceptDate(localDate.toString());            
            rentalRepository.save(rental); 

            System.out.println("##### return accepted by reservation return #####");
            System.out.println("reserveId : " + reserveId);    
        }             
        else{
            System.out.println("not found reserveId : " + reserveId);    
        }                   
   }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
