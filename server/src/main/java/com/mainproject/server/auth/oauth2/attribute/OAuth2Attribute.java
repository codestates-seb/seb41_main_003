package com.mainproject.server.auth.oauth2.attribute;


import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {

    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;

    public static OAuth2Attribute of(
            String registrationId,
            String attributeKey,
            Map<String, Object> attributes) {
        switch (registrationId) {
            case "google" :
                return ofGoogle(attributeKey, attributes);
            case "kakao" :
                return ofKakao(attributeKey, attributes);
            default :
                throw new ServiceLogicException(ErrorCode.OAUTH2_ACCESS_ERROR);
        }
    }

    private static OAuth2Attribute ofGoogle(
            String attributeKey,
            Map<String, Object> attributes
    ) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(attributeKey, attributeKey);
        map.put("name", name);
        map.put("email", email);

        return map;
    }
}
