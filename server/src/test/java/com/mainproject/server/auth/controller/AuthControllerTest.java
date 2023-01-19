package com.mainproject.server.auth.controller;

import com.google.gson.Gson;
import com.mainproject.server.auth.dto.PasswordDto;
import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.dto.RefreshDto;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.auth.token.Token;
import com.mainproject.server.config.SecurityConfig;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.service.UserService;
import com.mainproject.server.utils.ApiDocumentUtils;
import com.mainproject.server.utils.StubData;
import com.mainproject.server.webhook.WebHookService;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class,
        JwtTokenizer.class,
        WebHookService.class,
        CustomOAuth2UserService.class,
        OAuth2UserSuccessHandler.class,
        SecurityConfig.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshService refreshService;

    @MockBean
    private RefreshRepository refreshRepository;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private UserService userService;


    @Test
    @DisplayName("로그인 상태 확인 TEST - OK")
    @WithMockUser
    void verifyUserOK() throws Exception {
        // Given
        Token token = jwtTokenizer.delegateToken(StubData.createUser());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/verify-user")
                .header("Authorization", token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("verifyUserOk",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }

    @Test
    @DisplayName("로그인 상태 확인 TEST - AccessToken 만료")
    @WithMockUser
    void verifyUserThrowException() throws Exception {
        // Given
        Token token = createExpiredToken();
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/verify-user")
                .header("Authorization", token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message")
                        .value(ErrorCode.EXPIRED_ACCESS_TOKEN.getMessage()))
                .andDo(
                        MockMvcRestDocumentation.document("verifyUserException",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("ErrorCode"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("ErrorMessage"),
                                                fieldWithPath("fieldErrors").type(JsonFieldType.STRING).description("fieldErrors").optional(),
                                                fieldWithPath("violationErrors").type(JsonFieldType.STRING).description("violationErrors").optional()
                                        ))));
    }


    @Test
    @DisplayName("토큰 재발급 TEST - OK")
    @WithMockUser
    void reissueToken() throws Exception {
        // Given
        Token token = jwtTokenizer.delegateToken(StubData.createUser());
        Long userId = 1L;
        User user = StubData.createUser();
        RefreshDto refreshDto = new RefreshDto(
                user.getEmail(),
                token.getRefreshToken(),
                LocalDateTime.now());
        given(userService.verifiedUserById(userId)).willReturn(user);
        given(refreshService.getRefresh(anyString())).willReturn(refreshDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/reissue-token/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("refreshOk",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("userId").description("회원 식별자")
                                ),
                                HeaderDocumentation.responseHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("userId").description("회원 식별자"),
                                        headerWithName("userStatus").description("UserStatus")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("authorization").type(JsonFieldType.STRING).description("AccessToken"),
                                                fieldWithPath("userId").type(JsonFieldType.STRING).description("회원 식별자"),
                                                fieldWithPath("userStatus").type(JsonFieldType.STRING).description("회원 타입")
                                        ))));
    }

    @Test
    @DisplayName("토큰 재발급 TEST - RefreshToken 만료")
    @WithMockUser
    void reissueTokenThrowException() throws Exception {
        // Given
        Token token = createExpiredToken();
        Long userId = 1L;
        User user = StubData.createUser();
        given(userService.verifiedUserById(userId)).willReturn(user);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/reissue-token/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message")
                        .value(ErrorCode.EXPIRED_REFRESH_TOKEN.getMessage()))
                .andDo(
                        MockMvcRestDocumentation.document("refreshExpired",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("userId").description("회원 식별자")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("ErrorCode"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("ErrorMessage"),
                                                fieldWithPath("fieldErrors").type(JsonFieldType.STRING).description("fieldErrors").optional(),
                                                fieldWithPath("violationErrors").type(JsonFieldType.STRING).description("violationErrors").optional()
                                        ))));
    }

    @Test
    @DisplayName("Logout Controller 동작 TEST")
    @WithMockUser
    void logout() throws Exception {
        // Given
        Token token = jwtTokenizer.delegateToken(StubData.createUser());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/logout")
                .header("Authorization", token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("logoutOk",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }

    @Test
    @DisplayName("Second Password Controller 동작 TEST - OK")
    @WithMockUser
    void verifySecondPassword() throws Exception {
        // Given
        Long userId = 1L;
        Token token = jwtTokenizer.delegateToken(StubData.createUser());
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setSecondPassword("1234");
        Gson gson = new Gson();
        String content = gson.toJson(passwordDto);
        doNothing().when(userService).verifySecondPassword(anyLong(), anyString());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/auth/verify-second-password/{userId}", userId)
                .header("Authorization", token.getAccessToken())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("SecondPasswordOk",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("userId").description("회원 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("secondPassword").type(JsonFieldType.STRING).description("2차 비밀번호")
                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("Second Password Controller 동작 TEST - 잘못된 2차 비밀번호")
    @WithMockUser
    void verifySecondPasswordThrowException() throws Exception {
        // Given
        Long userId = 1L;
        Token token = jwtTokenizer.delegateToken(StubData.createUser());
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setSecondPassword("1234");
        Gson gson = new Gson();
        String content = gson.toJson(passwordDto);
        doThrow(new ServiceLogicException(ErrorCode.WRONG_SECOND_PASSWORD))
                .when(userService).verifySecondPassword(anyLong(), anyString());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/auth/verify-second-password/{userId}", userId)
                .header("Authorization", token.getAccessToken())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().is4xxClientError())
                .andDo(
                        MockMvcRestDocumentation.document("SecondPasswordFail",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("userId").description("회원 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("secondPassword").type(JsonFieldType.STRING).description("2차 비밀번호")
                                        )
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("ErrorCode"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("ErrorMessage"),
                                                fieldWithPath("fieldErrors").type(JsonFieldType.STRING).description("fieldErrors").optional(),
                                                fieldWithPath("violationErrors").type(JsonFieldType.STRING).description("violationErrors").optional()
                                        ))));
    }

    private Token createExpiredToken() {
        Map<String, Object> claims = new HashMap<>();
        User testUser = StubData.createUser();
        claims.put("username", testUser.getEmail());
        claims.put("roles", testUser.getRoles());

        String subject = testUser.getEmail();

        String base64SecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Key key = jwtTokenizer.getKeyFromBase64EncodedSecretKey(base64SecretKey);
        return new Token(
                "Bearer " + Jwts.builder()
                        .setClaims(claims)
                        .setSubject(subject)
                        .setIssuedAt(Calendar.getInstance().getTime())
                        .setExpiration(jwtTokenizer.getTokenExpiration(0))
                        .signWith(key)
                        .compact(),
                Jwts.builder()
                        .setSubject(subject)
                        .setIssuedAt(Calendar.getInstance().getTime())
                        .setExpiration(jwtTokenizer.getTokenExpiration(0))
                        .signWith(key)
                        .compact());
    }

}