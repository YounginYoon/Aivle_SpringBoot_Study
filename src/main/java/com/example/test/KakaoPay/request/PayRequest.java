package com.example.test.KakaoPay.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Getter
@AllArgsConstructor
public class PayRequest {
    private String url;
    private Map<String, String> map;
}
