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

            if (reserved.validate()) {
                // view 객체 생성
                MyPage myPage = new MyPage();
                // view 객체에 이벤트의 Value 를 set 함
                myPage.setReserveId(reserved.getId());
                myPage.setCarId(reserved.getCarId());
                myPage.setAmount(reserved.getAmount());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentaled_then_UPDATE_1(@Payload Rentaled rentaled) {
        try {
            if (rentaled.validate()) {
                // view 객체 조회
                List<MyPage> myPageList = myPageRepository.findByReserveId(rentaled.getreserveId());
                for(MyPage myPage : myPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setRentalId(rentaled.getrentalId());
                    myPage.setReserveStatus(rentaled.getReserveStatus());
                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayCanceled_then_UPDATE_2(@Payload PayCanceled payCanceled) {
        try {
            if (payCanceled.validate()) {
                // view 객체 조회
                List<MyPage> myPageList = myPageRepository.findByReserveId(payCanceled.getReserveId());
                for(MyPage myPage : myPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setReserveStatus(payCanceled.getReserveStatus());
                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}