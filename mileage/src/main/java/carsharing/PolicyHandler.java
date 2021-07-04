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
    @Autowired MileageRepository mileageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryRetrieved_AcceptMiaeage(@Payload DeliveryRetrieved deliveryRetrieved){

        if(!deliveryRetrieved.validate()) return;
        // Get Methods
        Long Id = deliveryRetrieved.getId();
        String reserveId = deliveryRetrieved.getReserveId();
        


        Mileage mileage = new Mileage();
        
        //mileage.setId(Id);
        mileage.setReserveId(reserveId);
        mileage.setMileage("Milaged");
        //milage.setDeleveryStatus("DeleveryCancled");
  
        mileageRepository.save(mileage);    


        // Sample Logic //
        System.out.println("\n\n##### listener AcceptMiaeage : " + deliveryRetrieved.toJson() + "\n\n");
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
