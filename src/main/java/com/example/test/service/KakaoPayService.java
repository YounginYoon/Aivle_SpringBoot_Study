package com.example.test.service;

import com.example.test.KakaoPay.PayInfoDto;
import com.example.test.KakaoPay.request.MakePayRequest;
import com.example.test.KakaoPay.request.PayRequest;
import com.example.test.KakaoPay.response.PayApproveResDto;
import com.example.test.KakaoPay.response.PayReadyResDto;
import com.example.test.entity.Member;
import com.example.test.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RequiredArgsConstructor
@Service
@Slf4j
public class KakaoPayService {
    private final MakePayRequest makePayRequest;
    private final MemberRepository memberRepository;

    @Value("${pay.admin-key}")
    private String adminKey;

    /** 카카오페이 결제를 시작하기 위해 상세 정보를 카카오페이 서버에 전달, 결제 고유 번호(TID)를 받는 단계
     * 어드민 키를 헤더에 담아 파라미터 값들과 함께 POST로 요청
     * 테스트 가맹점 코드로 'TC0ONETIME'를 사용
     * */
    @Transactional
    public PayReadyResDto getRedirectUrl(PayInfoDto payInfoDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        System.out.println("pay ready " + authentication + " " + name);

        Member member = memberRepository.findByEmail(name);
        System.out.println("find member " + member.getEmail() + member.getUsername());

        Long id = member.getId();

        HttpHeaders headers = new HttpHeaders();

        // 요청 헤더
        String auth = "SECRET_KEY " + adminKey;
        headers.set("Content-type", "application/json;charset=UTF-8");
        headers.set("Authorization", auth);

        System.out.println(headers);

        // 요청 Body
        PayRequest payRequest = makePayRequest.getReadyRequest(id, payInfoDto);

        // Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업
        HttpEntity<Map<String, String>> urlRequest = new HttpEntity<>(payRequest.getMap(), headers);
        System.out.println("get Body " + urlRequest.getBody());
        System.out.println("get Headers " + urlRequest.getHeaders());
        // RestTemplate로 Response 받아와서 DTO 변환 후 return
        RestTemplate rt = new RestTemplate();
        try {
            PayReadyResDto payReadyResDto = rt.postForObject(payRequest.getUrl(), urlRequest, PayReadyResDto.class);

            System.out.println("pay ready " + payReadyResDto.getTid());
            member.setTid(payReadyResDto.getTid());
            return payReadyResDto;
        } catch (RestClientException e) {
            throw e;
        }
    }

    @Transactional
    public PayApproveResDto getApprove(String pgToken, Long id) throws Exception {

        System.out.println(pgToken + " " + id);
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null) return null;

        String tid = member.getTid();
        HttpHeaders headers = new HttpHeaders();
        String auth = "SECRET_KEY " + adminKey;

        // 요청 헤더
        headers.set("Content-type","application/json;charset=UTF-8");
        headers.set("Authorization",auth);

        // 요청 Body
        PayRequest payRequest = makePayRequest.getApproveRequest(tid,id,pgToken);

        System.out.println("approve " + payRequest.getUrl() + " " + payRequest.getMap());
        // Header와 Body를 합쳐서 RestTemplate로 보내기 위한 밑작업
         HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payRequest.getMap(), headers);

         // 요청 보내기
        RestTemplate rt = new RestTemplate();
        PayApproveResDto payApproveResDto = rt.postForObject(payRequest.getUrl(), requestEntity, PayApproveResDto.class);

        System.out.println("pay Approved at " + payApproveResDto.getApproved_at());
        return payApproveResDto;
    }
}
