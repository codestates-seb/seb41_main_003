package com.mainproject.server.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class WebHookService {

    @Value("${WEBHOOK_URL}")
    private  String url;

    public void callEvent(WebHookDto response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            send(objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            log.error("WebHook 동작하지 않음!!");
        }
    }

    private void send(String content){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(content, headers);
        restTemplate.postForObject(url, entity, String.class);
    }
}