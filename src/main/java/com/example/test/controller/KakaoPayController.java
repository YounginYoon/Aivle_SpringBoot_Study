package com.example.test.controller;

import com.example.test.KakaoPay.PayInfoDto;
import com.example.test.KakaoPay.response.PayApproveResDto;
import com.example.test.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;

    // 결제 준비 redirect url 받기 --> 상품명과 가격을 같이 보내줘야 함
    @GetMapping("/ready")
    public ResponseEntity<?> getRedirectUrl(@RequestBody PayInfoDto payInfoDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(kakaoPayService.getRedirectUrl(payInfoDto));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("");
        }
    }

    // 결제 성공 pid를 받기 위해 request를 받고
    // pgToken은 redirect url에 뒤에 붙어오는 걸 떼서 쓰기 위함
    @GetMapping("/success/{id}")
    public ResponseEntity<?> afterGetRedirectUrl(@PathVariable("id") Long id,
                                                 @RequestParam("pg_token") String pgToken) {
        try {
            System.out.println("afterGetRedirectUrl " + id + " " + pgToken);
            PayApproveResDto kakaoApprove = kakaoPayService.getApprove(pgToken, id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(kakaoApprove);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }

    // 결제 진행 중 취소
    @GetMapping("/cancel")
    public ResponseEntity<?> cancel() {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body("결제 취소");
    }

    // 결제 실패
    @GetMapping("/fail")
    public ResponseEntity<?> fail() {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body("결제 실패");
    }
}
