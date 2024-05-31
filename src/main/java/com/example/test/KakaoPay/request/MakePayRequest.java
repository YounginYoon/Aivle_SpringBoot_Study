package com.example.test.KakaoPay.request;

import com.example.test.KakaoPay.PayInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MakePayRequest {
    public PayRequest getReadyRequest(Long id, PayInfoDto payInfoDto) {
        Map<String, String> map = new LinkedHashMap<>();

        String memberId = id + "";
        String orderId = "point" + id;

        // 가맹점 코드 테스트 코드는 TC0ONETIME
        map.put("cid", "TC0ONETIME");

        map.put("partner_order_id", orderId);
        map.put("partner_user_id", "스프링부트_웹");

        // 프론트 단에서 받아온 payInfoDto로 결제 주문서의 item 이름을
        // 지어주는 과정
        map.put("item_name", payInfoDto.getItemName());

        // 수량
        map.put("quantity", "1");

        // 가격
        map.put("total_amount", "1");

        // 비과세 금액
        map.put("tax_free_amount", "0");

        map.put("approval_url", "http://localhost:8080/payment/success" + "/" + id); // 성공시 redirect url
        map.put("cancel_url", "http://localhost:8080/payment/cancel"); // 취소시 redirect url
        map.put("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url

        return new PayRequest("https://open-api.kakaopay.com/online/v1/payment/ready",map);
    }

    public PayRequest getApproveRequest(String tid, Long id, String pgToken) {
        Map<String, String> map = new LinkedHashMap<>();

        String orderId = "point" + id;

        map.put("cid", "TC0ONETIME");

        // getReadyRequest에서 받아온 tid
        map.put("tid", tid);
        map.put("partner_order_id", orderId);
        map.put("partner_user_id", "스프링부트_웹");

        // getReadyRequest에서 받아온 redirect url에
        // 클라이언트가 접속하여 결제를 성공하면 아래의 url로 redirect 됨
        // "http://localhost:8080/payment/success"+"/"+id
        // 여기에 &pg_token = 토큰값 이 붙어서  redirect 됨
        // 해당 내용을 뽑아서 사용
        map.put("pg_token", pgToken);

        return new PayRequest("https://open-api.kakaopay.com/online/v1/payment/approve",map);
    }
}
