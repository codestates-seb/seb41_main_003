package com.mainproject.server.user.controller;

import com.google.gson.Gson;
import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.config.SecurityConfig;
import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.profile.dto.ProfileListResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
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
    @DisplayName("?????? ?????? ?????? TEST")
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
                                        parameterWithName("userId").description("?????? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("?????? ?????? TUTOR/TUTEE/NONE"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                ))));
    }


    @Test
    @DisplayName("?????? ?????? TEST")
    @WithMockUser
    void postUser() throws Exception {
        // Given
        UserPostDto userPostDto = new UserPostDto();
        userPostDto.setNickName("?????????");
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
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("?????? ???????????? ??????+?????? 8?????? ??????")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("?????? ?????? TUTOR/TUTEE/NONE"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                        ))));
    }

    @Test
    @DisplayName("?????? ?????? TEST")
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
                                        parameterWithName("userId").description("?????? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("?????? ???????????? ??????+?????? 8?????? ??????"),
                                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("?????? ????????? ?????? '-'?????? 11?????? ??????"),
                                                fieldWithPath("secondPassword").type(JsonFieldType.STRING).description("?????? 2??? ????????????"),
                                                fieldWithPath("userStatus").type(JsonFieldType.STRING).description("?????? ?????? TUTOR/TUTEE")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("?????? ?????? TUTOR/TUTEE/NONE"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                        ))));
    }

    @Test
    @DisplayName("?????? ?????? TEST")
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
                        MockMvcRestDocumentation.document("deleteUser",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("userId").description("?????? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }

    @Test
    @DisplayName("?????? ????????? ?????? ????????? ?????? TEST")
    @WithMockUser
    void getTutors() throws Exception {
        // Given
        Map<String, String> params = new HashMap<>();
        params.put("key", "TUTOR");
        ProfileListResponseDto responseDto = ResponseStubData.createProfileListResponse();
        List<ProfileListResponseDto> profileListResponse =
                List.of(responseDto);
        Page<ProfileListResponseDto> profileListResponseDto = new PageImpl<>(
                profileListResponse,
                PageRequest.of(0, 20),
                1);

        given(profileService.getTutorOrTuteeList(anyMap(), any(Pageable.class)))
                .willReturn(PageResponseDto.of(profileListResponse, profileListResponseDto));
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/users/tutors?page=0&sort=rate&search=test&subject=??????")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("getSearchTutor",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.requestParameters(
                                parameterWithName("sort").description("?????? ?????? rate/updateAt"),
                                parameterWithName("page").description("?????? ?????????"),
                                parameterWithName("search").description("?????? ?????????(????????? ??????, ???????????? ??? ?????? ?????? ??????)"),
                                parameterWithName("subject").description("??????, ????????? ?????? ex.'??????,??????'")
                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].profileId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data[].rate").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("data[].subjects").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].subjects[].subjectId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].subjects[].subjectTitle").type(JsonFieldType.STRING).description("?????? ???"),
                                        fieldWithPath("data[].school").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].bio").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].profileImage").type(JsonFieldType.OBJECT).description("????????? ?????????"),
                                        fieldWithPath("data[].profileImage.profileImageId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                        fieldWithPath("data[].profileImage.url").type(JsonFieldType.STRING).description("????????? URL"),
                                        fieldWithPath("data[].profileImage.createAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                                        fieldWithPath("data[].profileImage.updateAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                                        fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                        fieldWithPath("data[].updateAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ??????"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("?????? ????????? ??????"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ????????? - 0 = 1 ?????????"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("???????????? ?????? ??????"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("??? ??????"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("????????? ??? ?????????")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("?????? ????????? ?????? ????????? ?????? TEST")
    @WithMockUser
    void getTutees() throws Exception {
        // Given
        Map<String, String> params = new HashMap<>();
        params.put("key", "TUTEE");
        ProfileListResponseDto responseDto = ResponseStubData.createProfileListResponse();
        List<ProfileListResponseDto> profileListResponse =
                List.of(responseDto);
        Page<ProfileListResponseDto> profileListResponseDto = new PageImpl<>(
                profileListResponse,
                PageRequest.of(0, 20),
                1);

        given(profileService.getTutorOrTuteeList(anyMap(), any(Pageable.class)))
                .willReturn(PageResponseDto.of(profileListResponse, profileListResponseDto));
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/users/tutees?page=0&sort=rate&search=test&subject=??????")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("getSearchTutee",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.requestParameters(
                                parameterWithName("sort").description("?????? ?????? rate/updateAt"),
                                parameterWithName("page").description("?????? ?????????"),
                                parameterWithName("search").description("?????? ?????????(????????? ??????, ???????????? ??? ?????? ?????? ??????)"),
                                parameterWithName("subject").description("??????, ????????? ?????? ex.'??????,??????'")
                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].profileId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data[].rate").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("data[].subjects").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].subjects[].subjectId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].subjects[].subjectTitle").type(JsonFieldType.STRING).description("?????? ???"),
                                        fieldWithPath("data[].school").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].bio").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].profileImage").type(JsonFieldType.OBJECT).description("????????? ?????????"),
                                        fieldWithPath("data[].profileImage.profileImageId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                        fieldWithPath("data[].profileImage.url").type(JsonFieldType.STRING).description("????????? URL"),
                                        fieldWithPath("data[].profileImage.createAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                                        fieldWithPath("data[].profileImage.updateAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                                        fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                        fieldWithPath("data[].updateAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ??????"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("?????? ????????? ??????"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ????????? - 0 = 1 ?????????"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("???????????? ?????? ??????"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("??? ??????"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("????????? ??? ?????????")
                                )
                        )
                ));
    }

}