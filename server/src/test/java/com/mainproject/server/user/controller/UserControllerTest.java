package com.mainproject.server.user.controller;

import com.google.gson.Gson;
import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.config.SecurityConfig;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.user.dto.UserPatchDto;
import com.mainproject.server.user.dto.UserPostDto;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.mapper.UserMapper;
import com.mainproject.server.user.service.UserService;
import com.mainproject.server.utils.ApiDocumentUtils;
import com.mainproject.server.utils.ResponseStubData;
import com.mainproject.server.utils.StubData;
import com.mainproject.server.webhook.WebHookService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class,
        JwtTokenizer.class,
        WebHookService.class,
        CustomOAuth2UserService.class,
        OAuth2UserSuccessHandler.class,
        SecurityConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private RefreshService refreshService;

    @MockBean
    private RefreshRepository refreshRepository;

    @Test
    @DisplayName("회원 정보 조회 TEST")
    @WithMockUser
    void getUser() throws Exception {
        // Given
        Long userId = 1L;
        given(userService.getUser(anyLong())).willReturn(StubData.createUser());
        given(userMapper.entityToUserResponseDto(any(User.class)))
                .willReturn(ResponseStubData.createUserResponse());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/users/{userId}", userId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("test@test.com"))
                .andDo(
                        MockMvcRestDocumentation.document("getUser",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("userId").description("회원 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                        fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("회원 폰번호"),
                                        fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("로그인 타입"),
                                        fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("회원 상태 TUTOR/TUTEE/NONE"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시간")
                                ))));
    }


    @Test
    @DisplayName("회원 생성 TEST")
    @WithMockUser
    void postUser() throws Exception {
        // Given
        UserPostDto userPostDto = new UserPostDto();
        userPostDto.setNickName("수포자");
        userPostDto.setEmail("test@test.com");
        userPostDto.setPassword("aaaa1234");
        Gson gson = new Gson();
        String content = gson.toJson(userPostDto);
        given(userService.createUser(any(User.class))).willReturn(StubData.createUser());
        given(userMapper.userPostDtoToEntity(any(UserPostDto.class)))
                .willReturn(StubData.createUser());
        given(userMapper.entityToUserResponseDto(any(User.class)))
                .willReturn(ResponseStubData.createUserResponse());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/users")
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value("test@test.com"))
                .andDo(
                        MockMvcRestDocumentation.document("postUser",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호 문자+숫자 8자리 이상")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("회원 폰번호"),
                                                fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("로그인 타입"),
                                                fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("회원 상태 TUTOR/TUTEE/NONE"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시간")
                                        ))));
    }

    @Test
    @DisplayName("회원 생성 TEST")
    @WithMockUser
    void patchUser() throws Exception {
        // Given
        UserPatchDto userPatchDto = new UserPatchDto();
        userPatchDto.setNickName("test");
        userPatchDto.setPassword("aaaa1234");
        userPatchDto.setPhoneNumber("01055550000");
        userPatchDto.setSecondPassword("1234");
        userPatchDto.setUserStatus("TUTOR");
        Long userId = 1L;
        Gson gson = new Gson();
        String content = gson.toJson(userPatchDto);
        given(userService.updateUser(any(User.class)))
                .willReturn(StubData.createUser());
        given(userMapper.userPatchDtoToEntity(any(UserPatchDto.class)))
                .willReturn(StubData.createUser());
        given(userMapper.entityToUserResponseDto(any(User.class)))
                .willReturn(ResponseStubData.createUserResponse());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/users/{userId}",userId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("test@test.com"))
                .andDo(
                        MockMvcRestDocumentation.document("patchUser",
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
                                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호 문자+숫자 8자리 이상"),
                                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("회원 휴대폰 번호 '-'없이 11자리 숫자"),
                                                fieldWithPath("secondPassword").type(JsonFieldType.STRING).description("회원 2차 비밀번호"),
                                                fieldWithPath("userStatus").type(JsonFieldType.STRING).description("회원 상태 TUTOR/TUTEE")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("회원 폰번호"),
                                                fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("로그인 타입"),
                                                fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("회원 상태 TUTOR/TUTEE/NONE"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시간")
                                        ))));
    }

    @Test
    @DisplayName("회원 삭제 TEST")
    @WithMockUser
    void deleteUser() throws Exception {
        // Given
        Long userId = 1L;
        doNothing().when(userService).deleteUser(anyLong());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/users/{userId}", userId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("patchUser",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("userId").description("회원 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }


}