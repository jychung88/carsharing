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

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveReturned_ChangeStatus(@Payload ReserveReturned reserveReturned){

        if(!reserveReturned.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + reserveReturned.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_ChangeStatus(@Payload Reserved reserved){

        if(!reserved.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + reserved.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveCanceled_ChangeStatus(@Payload ReserveCanceled reserveCanceled){

        if(!reserveCanceled.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + reserveCanceled.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalCanceled_ChangeStatus(@Payload RentalCanceled rentalCanceled){

        if(!rentalCanceled.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + rentalCanceled.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalRetrieved_ChangeStatus(@Payload RentalRetrieved rentalRetrieved){

        if(!rentalRetrieved.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + rentalRetrieved.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentaled_ChangeStatus(@Payload Rentaled rentaled){

        if(!rentaled.validate()) return;

        System.out.println("\n\n##### listener ChangeStatus : " + rentaled.toJson() + "\n\n");

        // Sample Logic //
        Customer customer = new Customer();
        customerRepository.save(customer);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
