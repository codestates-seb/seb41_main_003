package com.mainproject.server.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WebHookService {

    @Value("${discord.webhookUrl}")
    private  String url;

    public void callEvent(WebHookDto response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        send(objectMapper.writeValueAsString(response));
    }

    private void send(String content){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(content, headers);
        restTemplate.postForObject(url, entity, String.class);
    }
}