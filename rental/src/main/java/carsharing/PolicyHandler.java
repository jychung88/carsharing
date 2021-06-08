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
    @Autowired RentalRepository rentalRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_Lental(@Payload Reserved reserved){

        // if(!reserved.validate()) return;

        // System.out.println("\n\n##### listener Lental : " + reserved.toJson() + "\n\n");

        // // Sample Logic //
        // Rental rental = new Rental();
        // rentalRepository.save(rental);
            
        if(reserved.isMe()){

            Rental rental = rentalRepository.findByCarId(String.valueOf(reserved.getCarId()));
            rental.setStock(rental.getStock() - reserved.getAmount());
  
            rentalRepository.save(rental);

        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveCanceled_CancelRental(@Payload ReserveCanceled reserveCanceled){

        // if(!reserveCanceled.isMe()) return;

        // System.out.println("\n\n##### listener CancelRental : " + reserveCanceled.toJson() + "\n\n");

        // // Sample Logic //
        // Rental rental = new Rental();
        // rentalRepository.save(rental);
            
        if(reserveCanceled.isMe()){
            System.out.println("###################### Carid" + reserveCanceled.getCarId());
            Rental rental = rentalRepository.findByCarId(reserveCanceled.getCarId());
            System.out.println("###################### Rental" + rental);
            rental.setStock(rental.getStock() + reserveCanceled.getAmount());
  
            rentalRepository.save(rental);

        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveReturned_RetriveRental(@Payload ReserveReturned reserveReturned){

        // if(!reserveReturned.isMe()) return;

        // System.out.println("\n\n##### listener RetriveRental : " + reserveReturned.toJson() + "\n\n");

        // // Sample Logic //
        // Rental rental = new Rental();
        // rentalRepository.save(rental);
            
        if(reserveReturned.isMe()){

            Rental rental = rentalRepository.findByCarId(String.valueOf(reserveReturned.getCarId()));
            rental.setStock(rental.getStock() + reserveReturned.getAmount());
  
            rentalRepository.save(rental);

        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
