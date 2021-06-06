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
    @Autowired PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveCanceled_CancelPay(@Payload ReserveCanceled reserveCanceled){

        if(!reserveCanceled.validate()) return;

        System.out.println("\n\n##### listener Cancel : " + reserveCanceled.toJson() + "\n\n");

        String reserveId = reserveCanceled.getId().toString();
        Payment payment = paymentRepository.findByReserveId(reserveId);
        if (payment != null) {
            paymentRepository.delete(payment); 

            System.out.println("##### delete payment caused by reservation cancel #####");
            System.out.println("reserveId : " + reserveId);    
        }
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
