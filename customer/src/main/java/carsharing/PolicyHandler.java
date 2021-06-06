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
    public void wheneverReserveCanceled_ChangeReserveStatus(@Payload ReserveCanceled reserveCanceled){

        if(!reserveCanceled.validate()) return;

        System.out.println("\n\n##### listener ChangeReserveStatus : " + reserveCanceled.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_ChangeReserveStatus(@Payload Reserved reserved){

        if(!reserved.validate()) return;

        System.out.println("\n\n##### listener ChangeReserveStatus : " + reserved.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveReturned_ChangeReserveStatus(@Payload ReserveReturned reserveReturned){

        if(!reserveReturned.validate()) return;

        System.out.println("\n\n##### listener ChangeReserveStatus : " + reserveReturned.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCanceled_ChangeReserveStatus(@Payload PayCanceled payCanceled){

        if(!payCanceled.validate()) return;

        System.out.println("\n\n##### listener ChangeReserveStatus : " + payCanceled.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalRetrieved_ChangeLentalStatus(@Payload RentalRetrieved rentalRetrieved){

        if(!rentalRetrieved.validate()) return;

        System.out.println("\n\n##### listener ChangeLentalStatus : " + rentalRetrieved.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalCanceled_ChangeLentalStatus(@Payload RentalCanceled rentalCanceled){

        if(!rentalCanceled.validate()) return;

        System.out.println("\n\n##### listener ChangeLentalStatus : " + rentalCanceled.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentaled_ChangeLentalStatus(@Payload Rentaled rentaled){

        if(!rentaled.validate()) return;

        System.out.println("\n\n##### listener ChangeLentalStatus : " + rentaled.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
