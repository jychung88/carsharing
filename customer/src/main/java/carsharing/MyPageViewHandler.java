package carsharing;

import carsharing.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MyPageViewHandler {


    @Autowired
    private MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserved_then_CREATE_1 (@Payload Reserved reserved) {
        try {

            if (!reserved.validate()) return;

            // view 객체 생성
            MyPage myPage = new MyPage();
            // view 객체에 이벤트의 Value 를 set 함
            myPage.setReserveId(reserved.getId().toString());
            myPage.setAmount(reserved.getAmount());
            myPage.setReserveDate(reserved.getReserveDate());
            myPage.setRentalAddr(reserved.getRentalAddr());
            myPage.setRetrieveAddr(reserved.getRetrieveAddr());
            myPage.setCarId(reserved.getCarId());
            myPage.setUserPhone(reserved.getUserPhone());
            myPage.setStatus("Reserved");
            // view 레파지 토리에 save
            myPageRepository.save(myPage);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentalAccepted_then_UPDATE_1(@Payload RentalAccepted rentalAccepted) {
        try {
            if (!rentalAccepted.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByReserveId(rentalAccepted.getReserveId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setRentAcceptDate(rentalAccepted.getRentAcceptDate());
                myPage.setStatus("RentalAccepted");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentaled_then_UPDATE_2(@Payload Rentaled rentaled) {
        try {
            if (!rentaled.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByReserveId(rentaled.getReserveId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setRentalDate(rentaled.getRentalDate());
                myPage.setStatus("Rentaled");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserveCanceled_then_UPDATE_3(@Payload ReserveCanceled reserveCanceled) {
        try {
            if (!reserveCanceled.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByReserveId(reserveCanceled.getId().toString());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setCancelDate(reserveCanceled.getCancelDate());
                myPage.setStatus("ReserveCanceled");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentalCanceled_then_UPDATE_4(@Payload RentalCanceled rentalCanceled) {
        try {
            if (!rentalCanceled.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByReserveId(rentalCanceled.getReserveId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setRentCancelDate(rentalCanceled.getRentCancelDate());
                myPage.setStatus("RentalCanceled");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayCanceled_then_UPDATE_5(@Payload PayCanceled payCanceled) {
        try {
            if (!payCanceled.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByReserveId(payCanceled.getReserveId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setPayCancelDate(payCanceled.getPayCancelDate());
                myPage.setStatus("PayCanceled");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserveReturned_then_UPDATE_6(@Payload ReserveReturned reserveReturned) {
        try {
            if (!reserveReturned.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByReserveId(reserveReturned.getId().toString());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setReturnDate(reserveReturned.getReturnDate());
                myPage.setStatus("ReserveReturned");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReturnAccepted_then_UPDATE_7(@Payload ReturnAccepted returnAccepted) {
        try {
            if (!returnAccepted.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByReserveId(returnAccepted.getReserveId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setRetAcceptDate(returnAccepted.getRetAcceptDate());
                myPage.setStatus("ReturnAccepted");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentalRetrieved_then_UPDATE_8(@Payload RentalRetrieved rentalRetrieved) {
        try {
            if (!rentalRetrieved.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByReserveId(rentalRetrieved.getReserveId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setRentRetrieveDate(rentalRetrieved.getRentRetrieveDate());
                myPage.setStatus("RentalRetrieved");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}