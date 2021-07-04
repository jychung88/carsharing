package carsharing;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import carsharing.config.kafka.KafkaProcessor;

@Service
public class PolicyHandler{
    @Autowired DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalAccepted_StartDelivery(@Payload RentalAccepted rentalAccepted){

        if(!rentalAccepted.validate()) return;
        // Get Methods
                
        //String Id = Long.toString(rentalAccepted.getId());
        //Long Id = rentalAccepted.getId();
        String reserveId = Long.toString(rentalAccepted.getId());
        //String reserveId = rentalAccepted.getReserveId();
        String rentAcceptDate = rentalAccepted.getRentAcceptDate();
        
        

        Delivery delivery = new Delivery();
        
        //delivery.setId(Id);
        delivery.setReserveId(reserveId);
        
        delivery.setRentalAcceptDate(rentAcceptDate);
        delivery.setDeleveryStatus("DeleveryStarted");
  
        deliveryRepository.save(delivery);           


        // Sample Logic //
        System.out.println("\n\n##### listener StartDelivery : " + rentalAccepted.toJson() + "\n\n");
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalCanceled_CancletDelivery(@Payload RentalCanceled rentalCanceled){

        if(!rentalCanceled.validate()) return;
        // Get Methods


        String reserveId = Long.toString(rentalCanceled.getId());

        Delivery delivery = deliveryRepository.findByReserveId(reserveId);
        if (delivery != null) {
            delivery.setDeleveryStatus("DeleveryCancled");
            LocalDate localDate = LocalDate.now();         
            delivery.setRentalCancelDate(localDate.toString());       
            
            deliveryRepository.save(delivery); 

            System.out.println("\n\n##### listener CancletDelivery : " + rentalCanceled.toJson() + "\n\n");
            System.out.println("reserveId : " + reserveId);    
        }
        else{
            System.out.println("not found reserveId : " + reserveId);    
        }          
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReturnAccepted_RetrieveDelivery(@Payload ReturnAccepted returnAccepted){

        if(!returnAccepted.validate()) return;
        // Get Methods

        String reserveId = Long.toString(returnAccepted.getId());

        Delivery delivery = deliveryRepository.findByReserveId(reserveId);
        if (delivery != null) {
            delivery.setDeleveryStatus("DeleveryRetrieved");
            LocalDate localDate = LocalDate.now();         
            delivery.setRentalRetrieveDate(localDate.toString());       
            
            deliveryRepository.save(delivery); 

            System.out.println("\n\n##### listener RetrieveDelivery : " + returnAccepted.toJson() + "\n\n");
        }
        else{
            System.out.println("not found reserveId : " + reserveId);    
        }         
/*
        //String Id = Long.toString(returnAccepted.getId());
        //Long Id = returnAccepted.getId();
        //String reserveId = returnAccepted.getReserveId();
        String reserveId = Long.toString(returnAccepted.getId());
        String retAcceptDate = returnAccepted.getRetAcceptDate();


        Delivery delivery = new Delivery();
        
        //delivery.setId(Id);
        delivery.setReserveId(reserveId);
        delivery.setRentalRetrieveDate(retAcceptDate);
        delivery.setDeleveryStatus("DeleveryRetrieved");
  
        deliveryRepository.save(delivery);   
        // Sample Logic //
        System.out.println("\n\n##### listener RetrieveDelivery : " + returnAccepted.toJson() + "\n\n");
        */
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
