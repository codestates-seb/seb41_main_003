package com.mainproject.server.profile.controller;

import com.google.gson.Gson;
import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.config.SecurityConfig;
import com.mainproject.server.profile.dto.ProfileDto;
import com.mainproject.server.profile.dto.ProfilePageDto;
import com.mainproject.server.profile.dto.WantedDto;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.mapper.ProfileMapper;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.review.entity.Review;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProfileController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class,
        JwtTokenizer.class,
        WebHookService.class,
        CustomOAuth2UserService.class,
        OAuth2UserSuccessHandler.class,
        SecurityConfig.class})
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshService refreshService;

    @MockBean
    private RefreshRepository refreshRepository;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private UserService userService;

    @MockBean
    private ProfileMapper profileMapper;

    @Test
    @DisplayName("?????? ?????? ????????? ????????? ?????? TEST")
    @WithMockUser
    void getProfiles() throws Exception {
        // Given
        Long userId = 1L;
        given(profileService.getProfiles(anyLong())).willReturn(List.of(new Profile()));
        given(profileMapper.entityListToSimpleResponseDtoList(anyList()))
                .willReturn(List.of(ResponseStubData.createProfileSimpleResponse()));
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/profiles/{userId}", userId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("getProfileList",
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
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                                fieldWithPath("data[].profileId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data[].url").type(JsonFieldType.STRING).description("????????? ????????? URL"),
                                                fieldWithPath("data[].school").type(JsonFieldType.STRING).description("?????? ??????")

                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("?????? ????????? ?????? TEST")
    @WithMockUser
    void getProfile() throws Exception {
        // Given
        Long profileId = 1L;
        Profile profile = StubData.createProfile(profileId);
        Page<Review> reviews = new PageImpl<>(new ArrayList<>(
                profile.getReviews()),
                PageRequest.of(1, 5),
                2);
        given(profileService.getProfile(anyLong(), any(Pageable.class)))
                .willReturn((ProfilePageDto.of(profile, reviews)));
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/profiles/details/{profileId}", profileId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("getProfile",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("????????? ?????????")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.profileId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.rate").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.bio").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.school").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.wantedStatus").type(JsonFieldType.STRING).description("?????? ?????? NONE/BASIC/REQUEST"),
                                                fieldWithPath("data.profileStatus").type(JsonFieldType.STRING).description("????????? ?????? TUTOR/TUTEE"),
                                                fieldWithPath("data.way").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????? ??????"),
                                                fieldWithPath("data.character").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data.subjects").type(JsonFieldType.ARRAY).description("??????"),
                                                fieldWithPath("data.subjects[].subjectId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.subjects[].subjectTitle").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.subjects[].content").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.subjects[].createAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.subjects[].updateAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.difference").type(JsonFieldType.STRING).description("?????????"),
                                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data.pay").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                                fieldWithPath("data.wantDate").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.preTutoring").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.profileImage").type(JsonFieldType.OBJECT).description("????????? ????????? ??????"),
                                                fieldWithPath("data.profileImage.profileImageId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                                fieldWithPath("data.profileImage.url").type(JsonFieldType.STRING).description("????????? ????????? URL"),
                                                fieldWithPath("data.profileImage.createAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                                                fieldWithPath("data.profileImage.updateAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ?????? ??????"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ??????"),
                                                fieldWithPath("data.reviews").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????(????????????)"),
                                                fieldWithPath("data.reviews[].reviewId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.reviews[].professional").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                fieldWithPath("data.reviews[].readiness").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                fieldWithPath("data.reviews[].explanation").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].punctuality").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].reviewBody").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.reviews[].tuteeName").type(JsonFieldType.STRING).description("?????? ?????????(??????)"),
                                                fieldWithPath("data.reviews[].createAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].updateAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
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
    @DisplayName("????????? ?????? TEST")
    @WithMockUser
    void postProfile() throws Exception {
        // Given
        Long userId = 1L;
        Profile profile = StubData.createProfile(userId);
        Page<Review> reviews = new PageImpl<>(new ArrayList<>(
                profile.getReviews()),
                PageRequest.of(1, 5),
                2);
        ProfileDto profileDto = StubData.createProfileDto();
        given(profileService.createProfile(anyLong(), any(Profile.class), anyList(), any(Pageable.class)))
                .willReturn(ProfilePageDto.of(profile, reviews));
        given(profileMapper.dtoToEntity(any(ProfileDto.class)))
                .willReturn(profile);
        // When
        Gson gson = new Gson();
        String content = gson.toJson(profileDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/profiles/{userId}", userId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document("postProfile",
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
                                                fieldWithPath("name").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("bio").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("school").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("way").type(JsonFieldType.STRING).description("????????? ???????????? ?????? ?????? ??????"),
                                                fieldWithPath("subjects").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                                fieldWithPath("subjects[].subjectId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("subjects[].subjectTitle").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("subjects[].content").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("difference").type(JsonFieldType.STRING).description("????????? - ??????"),
                                                fieldWithPath("gender").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("character").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("pay").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                                fieldWithPath("wantDate").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("preTutoring").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.profileId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.rate").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.bio").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.school").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.wantedStatus").type(JsonFieldType.STRING).description("?????? ?????? NONE/BASIC/REQUEST"),
                                                fieldWithPath("data.profileStatus").type(JsonFieldType.STRING).description("????????? ?????? TUTOR/TUTEE"),
                                                fieldWithPath("data.way").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????? ??????"),
                                                fieldWithPath("data.character").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data.subjects").type(JsonFieldType.ARRAY).description("??????"),
                                                fieldWithPath("data.subjects[].subjectId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.subjects[].subjectTitle").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.subjects[].content").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.subjects[].createAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.subjects[].updateAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.difference").type(JsonFieldType.STRING).description("?????????"),
                                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data.pay").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                                fieldWithPath("data.wantDate").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.preTutoring").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.profileImage").type(JsonFieldType.OBJECT).description("????????? ????????? ??????"),
                                                fieldWithPath("data.profileImage.profileImageId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                                fieldWithPath("data.profileImage.url").type(JsonFieldType.STRING).description("????????? ????????? URL"),
                                                fieldWithPath("data.profileImage.createAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                                                fieldWithPath("data.profileImage.updateAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ?????? ??????"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ??????"),
                                                fieldWithPath("data.reviews").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????(????????????)"),
                                                fieldWithPath("data.reviews[].reviewId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.reviews[].professional").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                fieldWithPath("data.reviews[].readiness").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                fieldWithPath("data.reviews[].explanation").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].punctuality").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].reviewBody").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.reviews[].tuteeName").type(JsonFieldType.STRING).description("?????? ?????????(??????)"),
                                                fieldWithPath("data.reviews[].createAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].updateAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
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
    @DisplayName("????????? ?????? TEST")
    @WithMockUser
    void patchProfile() throws Exception {
        // Given
        Long profileId = 1L;
        Profile profile = StubData.createProfile(profileId);
        Page<Review> reviews = new PageImpl<>(new ArrayList<>(
                profile.getReviews()),
                PageRequest.of(1, 5),
                2);
        ProfileDto profileDto = StubData.createProfileDto();
        given(profileService.updateProfile(anyLong(), any(Profile.class), anyList(), any(Pageable.class)))
                .willReturn(ProfilePageDto.of(profile, reviews));
        given(profileMapper.dtoToEntity(any(ProfileDto.class)))
                .willReturn(profile);
        // When
        Gson gson = new Gson();
        String content = gson.toJson(profileDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/profiles/details/{profileId}", profileId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("patchProfile",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("????????? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("name").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("bio").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("school").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("way").type(JsonFieldType.STRING).description("????????? ???????????? ?????? ?????? ??????"),
                                                fieldWithPath("subjects").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                                fieldWithPath("subjects[].subjectId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("subjects[].subjectTitle").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("subjects[].content").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("difference").type(JsonFieldType.STRING).description("????????? - ??????"),
                                                fieldWithPath("gender").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("character").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("pay").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                                fieldWithPath("wantDate").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("preTutoring").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.profileId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.rate").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.bio").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.school").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.wantedStatus").type(JsonFieldType.STRING).description("?????? ?????? NONE/BASIC/REQUEST"),
                                                fieldWithPath("data.profileStatus").type(JsonFieldType.STRING).description("????????? ?????? TUTOR/TUTEE"),
                                                fieldWithPath("data.way").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????? ??????"),
                                                fieldWithPath("data.character").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data.subjects").type(JsonFieldType.ARRAY).description("??????"),
                                                fieldWithPath("data.subjects[].subjectId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.subjects[].subjectTitle").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.subjects[].content").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.subjects[].createAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.subjects[].updateAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.difference").type(JsonFieldType.STRING).description("?????????"),
                                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data.pay").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                                fieldWithPath("data.wantDate").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.preTutoring").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.profileImage").type(JsonFieldType.OBJECT).description("????????? ????????? ??????"),
                                                fieldWithPath("data.profileImage.profileImageId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                                fieldWithPath("data.profileImage.url").type(JsonFieldType.STRING).description("????????? ????????? URL"),
                                                fieldWithPath("data.profileImage.createAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                                                fieldWithPath("data.profileImage.updateAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ?????? ??????"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ??????"),
                                                fieldWithPath("data.reviews").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????(????????????)"),
                                                fieldWithPath("data.reviews[].reviewId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.reviews[].professional").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                fieldWithPath("data.reviews[].readiness").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                fieldWithPath("data.reviews[].explanation").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].punctuality").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].reviewBody").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.reviews[].tuteeName").type(JsonFieldType.STRING).description("?????? ?????????(??????)"),
                                                fieldWithPath("data.reviews[].createAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].updateAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
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
    @DisplayName("????????? ?????? ?????? TEST - ?????? ?????? ?????? API")
    @WithMockUser
    void patchProfileStatus() throws Exception {
        // Given
        Long profileId = 1L;
        Profile profile = StubData.createProfile(profileId);
        Page<Review> reviews = new PageImpl<>(new ArrayList<>(
                profile.getReviews()),
                PageRequest.of(1, 5),
                2);
        WantedDto wantedDto = new WantedDto();
        wantedDto.setWantedStatus("REQUEST");
        given(profileService.updateWantedStatus(anyLong(), any(WantedDto.class), any(Pageable.class)))
                .willReturn(ProfilePageDto.of(profile, reviews));
        given(profileMapper.dtoToEntity(any(ProfileDto.class)))
                .willReturn(profile);
        // When
        Gson gson = new Gson();
        String content = gson.toJson(wantedDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/profiles/status/{profileId}", profileId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("patchProfileStatus",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("????????? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("wantedStatus").type(JsonFieldType.STRING).description("?????? ?????? REQUEST/NONE")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.profileId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.rate").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.bio").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.school").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.wantedStatus").type(JsonFieldType.STRING).description("?????? ?????? NONE/BASIC/REQUEST"),
                                                fieldWithPath("data.profileStatus").type(JsonFieldType.STRING).description("????????? ?????? TUTOR/TUTEE"),
                                                fieldWithPath("data.way").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????? ??????"),
                                                fieldWithPath("data.character").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data.subjects").type(JsonFieldType.ARRAY).description("??????"),
                                                fieldWithPath("data.subjects[].subjectId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.subjects[].subjectTitle").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.subjects[].content").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.subjects[].createAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.subjects[].updateAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.difference").type(JsonFieldType.STRING).description("?????????"),
                                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data.pay").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                                fieldWithPath("data.wantDate").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.preTutoring").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.profileImage").type(JsonFieldType.OBJECT).description("????????? ????????? ??????"),
                                                fieldWithPath("data.profileImage.profileImageId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                                fieldWithPath("data.profileImage.url").type(JsonFieldType.STRING).description("????????? ????????? URL"),
                                                fieldWithPath("data.profileImage.createAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                                                fieldWithPath("data.profileImage.updateAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ?????? ??????"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ??????"),
                                                fieldWithPath("data.reviews").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????(????????????)"),
                                                fieldWithPath("data.reviews[].reviewId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.reviews[].professional").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                fieldWithPath("data.reviews[].readiness").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                fieldWithPath("data.reviews[].explanation").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].punctuality").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].reviewBody").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.reviews[].tuteeName").type(JsonFieldType.STRING).description("?????? ?????????(??????)"),
                                                fieldWithPath("data.reviews[].createAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                fieldWithPath("data.reviews[].updateAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
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
    @DisplayName("????????? ?????? TEST")
    @WithMockUser
    void deleteProfile() throws Exception {
        // Given
        Long profileId = 1L;
        doNothing().when(profileService).deleteProfile(anyLong());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/profiles/details/{profileId}", profileId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteProfile",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("????????? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }


}