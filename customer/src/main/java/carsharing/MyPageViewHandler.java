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
            myPage.setCarId(reserved.getCarId());
            myPage.setLentalAddr(reserved.getLentalAddr());
            myPage.setRetriveAddr(reserved.getRetriveAddr());
            myPage.setAmount(reserved.getAmount());
            myPage.setStatus("예약");
            myPage.setReserveDate(reserved.getReservedate());
            myPage.setId(reserved.getId());
            // view 레파지 토리에 save
            myPageRepository.save(myPage);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserved_then_UPDATE_1(@Payload Reserved reserved) {
        try {
            if (!reserved.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByCarId(reserved.getCarId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setReserveDate(reserved.getReservedate());
                myPage.setLentalAddr(reserved.getLentalAddr());
                myPage.setRetriveAddr(reserved.getRetriveAddr());
                myPage.setAmount(reserved.getAmount());
                myPage.setReservestatus("예약");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            Optional<MyPage> myPageOptional = myPageRepository.findById(reserved.getId());
            if( myPageOptional.isPresent()) {
                MyPage myPage = myPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setReserveDate(reserved.getReservedate());
                    myPage.setLentalAddr(reserved.getLentalAddr());
                    myPage.setRetriveAddr(reserved.getRetriveAddr());
                    myPage.setAmount(reserved.getAmount());
                    myPage.setReservestatus("예약");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserveReturned_then_UPDATE_2(@Payload ReserveReturned reserveReturned) {
        try {
            if (!reserveReturned.validate()) return;
                // view 객체 조회
            Optional<MyPage> myPageOptional = myPageRepository.findById(reserveReturned.getId());
            if( myPageOptional.isPresent()) {
                MyPage myPage = myPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setRetrunDate(reserveReturned.getReturnDate());
                    myPage.setReservestatus("반납");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentaled_then_UPDATE_3(@Payload Rentaled rentaled) {
        try {
            if (!rentaled.validate()) return;
                // view 객체 조회
            Optional<MyPage> myPageOptional = myPageRepository.findById(rentaled.getId());
            if( myPageOptional.isPresent()) {
                MyPage myPage = myPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setReservestatus("대여");
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
            Optional<MyPage> myPageOptional = myPageRepository.findById(rentalCanceled.getId());
            if( myPageOptional.isPresent()) {
                MyPage myPage = myPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setReservestatus("대여취소");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentalRetrieved_then_UPDATE_5(@Payload RentalRetrieved rentalRetrieved) {
        try {
            if (!rentalRetrieved.validate()) return;
                // view 객체 조회
            Optional<MyPage> myPageOptional = myPageRepository.findById(rentalRetrieved.getId());
            if( myPageOptional.isPresent()) {
                MyPage myPage = myPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setReservestatus("대여반납");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserveCanceled_then_UPDATE_6(@Payload ReserveCanceled reserveCanceled) {
        try {
            if (!reserveCanceled.validate()) return;
                // view 객체 조회
            Optional<MyPage> myPageOptional = myPageRepository.findById(reserveCanceled.getId());
            if( myPageOptional.isPresent()) {
                MyPage myPage = myPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setReservestatus("예약취소");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}