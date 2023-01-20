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
import com.mainproject.server.profile.dto.ProfileResponseDto;
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
    @DisplayName("특정 회원 프로필 리스트 조회 TEST")
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
                                        parameterWithName("userId").description("회원 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                fieldWithPath("data[].profileId").type(JsonFieldType.NUMBER).description("프로필 식별자"),
                                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("프로필 이름"),
                                                fieldWithPath("data[].url").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                                fieldWithPath("data[].school").type(JsonFieldType.STRING).description("학교 정보")

                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("특정 프로필 조회 TEST")
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
                                        parameterWithName("profileId").description("프로필 식별자")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.profileId").type(JsonFieldType.NUMBER).description("프로필 식별자"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("프로필 이름"),
                                                fieldWithPath("data.rate").type(JsonFieldType.NUMBER).description("별점 평균"),
                                                fieldWithPath("data.bio").type(JsonFieldType.STRING).description("한줄 소개"),
                                                fieldWithPath("data.school").type(JsonFieldType.STRING).description("학교 정보"),
                                                fieldWithPath("data.wantedStatus").type(JsonFieldType.STRING).description("공고 상태 NONE/BASIC/REQUEST"),
                                                fieldWithPath("data.profileStatus").type(JsonFieldType.STRING).description("프로필 상태 TUTOR/TUTEE"),
                                                fieldWithPath("data.way").type(JsonFieldType.STRING).description("수업 방식 또는 원하는 방식"),
                                                fieldWithPath("data.character").type(JsonFieldType.STRING).description("성격"),
                                                fieldWithPath("data.subjects").type(JsonFieldType.ARRAY).description("과목"),
                                                fieldWithPath("data.subjects[].subjectId").type(JsonFieldType.NUMBER).description("과목 식별자"),
                                                fieldWithPath("data.subjects[].subjectTitle").type(JsonFieldType.STRING).description("과목 타이틀"),
                                                fieldWithPath("data.subjects[].content").type(JsonFieldType.STRING).description("과목 수업 내용"),
                                                fieldWithPath("data.subjects[].createAt").type(JsonFieldType.STRING).description("과목 생성 시각"),
                                                fieldWithPath("data.subjects[].updateAt").type(JsonFieldType.STRING).description("과목 최종 수정 시각"),
                                                fieldWithPath("data.difference").type(JsonFieldType.STRING).description("차별점"),
                                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                                fieldWithPath("data.pay").type(JsonFieldType.STRING).description("수업료 또는 원하는 수업료"),
                                                fieldWithPath("data.wantDate").type(JsonFieldType.STRING).description("원하는 날짜"),
                                                fieldWithPath("data.preTutoring").type(JsonFieldType.STRING).description("사전 과외 가능 여부"),
                                                fieldWithPath("data.profileImage").type(JsonFieldType.OBJECT).description("프로필 이미지 정보"),
                                                fieldWithPath("data.profileImage.profileImageId").type(JsonFieldType.NUMBER).description("프로필 이미지 식별자"),
                                                fieldWithPath("data.profileImage.url").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                                fieldWithPath("data.profileImage.createAt").type(JsonFieldType.STRING).description("프로필 이미지 생성 시각"),
                                                fieldWithPath("data.profileImage.updateAt").type(JsonFieldType.STRING).description("프로필 이미지 최종 수정 시각"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("프로필 생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("프로필 최종 생성 시각"),
                                                fieldWithPath("data.reviews").type(JsonFieldType.ARRAY).description("프로필 리뷰 리스트(튜터일때)"),
                                                fieldWithPath("data.reviews[].reviewId").type(JsonFieldType.NUMBER).description("후기 식별자"),
                                                fieldWithPath("data.reviews[].professional").type(JsonFieldType.NUMBER).description("전문성 별점"),
                                                fieldWithPath("data.reviews[].readiness").type(JsonFieldType.NUMBER).description("준비성 별점"),
                                                fieldWithPath("data.reviews[].explanation").type(JsonFieldType.NUMBER).description("수업 설명 별점"),
                                                fieldWithPath("data.reviews[].punctuality").type(JsonFieldType.NUMBER).description("시간 준수 별점"),
                                                fieldWithPath("data.reviews[].reviewBody").type(JsonFieldType.STRING).description("후기 내용"),
                                                fieldWithPath("data.reviews[].tuteeName").type(JsonFieldType.STRING).description("후기 작성자(튜티)"),
                                                fieldWithPath("data.reviews[].createAt").type(JsonFieldType.STRING).description("후기 생성 시각"),
                                                fieldWithPath("data.reviews[].updateAt").type(JsonFieldType.STRING).description("후기 최종 수정 시각"),
                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("요청 페이지 정보"),
                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("요청 페이지 - 0 = 1 페이지"),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지당 요청 회원"),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 멤버"),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("생성된 총 페이지")


                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("프로필 등록 TEST")
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
                                        parameterWithName("userId").description("회원 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("name").type(JsonFieldType.STRING).description("프로필 이름"),
                                                fieldWithPath("bio").type(JsonFieldType.STRING).description("한줄 소개"),
                                                fieldWithPath("school").type(JsonFieldType.STRING).description("학교 정보"),
                                                fieldWithPath("way").type(JsonFieldType.STRING).description("원하는 수업방식 또는 수업 방식"),
                                                fieldWithPath("subjects").type(JsonFieldType.ARRAY).description("과목 리스트"),
                                                fieldWithPath("subjects[].subjectId").type(JsonFieldType.NUMBER).description("과목 식별자"),
                                                fieldWithPath("subjects[].subjectTitle").type(JsonFieldType.STRING).description("과목 타이틀"),
                                                fieldWithPath("subjects[].content").type(JsonFieldType.STRING).description("과목 수업 내용"),
                                                fieldWithPath("difference").type(JsonFieldType.STRING).description("차별성 - 튜터"),
                                                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                                fieldWithPath("character").type(JsonFieldType.STRING).description("성격"),
                                                fieldWithPath("pay").type(JsonFieldType.STRING).description("수업료 또는 원하는 수업료"),
                                                fieldWithPath("wantDate").type(JsonFieldType.STRING).description("원하는 시간"),
                                                fieldWithPath("preTutoring").type(JsonFieldType.STRING).description("사전 과외 여부")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.profileId").type(JsonFieldType.NUMBER).description("프로필 식별자"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("프로필 이름"),
                                                fieldWithPath("data.rate").type(JsonFieldType.NUMBER).description("별점 평균"),
                                                fieldWithPath("data.bio").type(JsonFieldType.STRING).description("한줄 소개"),
                                                fieldWithPath("data.school").type(JsonFieldType.STRING).description("학교 정보"),
                                                fieldWithPath("data.wantedStatus").type(JsonFieldType.STRING).description("공고 상태 NONE/BASIC/REQUEST"),
                                                fieldWithPath("data.profileStatus").type(JsonFieldType.STRING).description("프로필 상태 TUTOR/TUTEE"),
                                                fieldWithPath("data.way").type(JsonFieldType.STRING).description("수업 방식 또는 원하는 방식"),
                                                fieldWithPath("data.character").type(JsonFieldType.STRING).description("성격"),
                                                fieldWithPath("data.subjects").type(JsonFieldType.ARRAY).description("과목"),
                                                fieldWithPath("data.subjects[].subjectId").type(JsonFieldType.NUMBER).description("과목 식별자"),
                                                fieldWithPath("data.subjects[].subjectTitle").type(JsonFieldType.STRING).description("과목 타이틀"),
                                                fieldWithPath("data.subjects[].content").type(JsonFieldType.STRING).description("과목 수업 내용"),
                                                fieldWithPath("data.subjects[].createAt").type(JsonFieldType.STRING).description("과목 생성 시각"),
                                                fieldWithPath("data.subjects[].updateAt").type(JsonFieldType.STRING).description("과목 최종 수정 시각"),
                                                fieldWithPath("data.difference").type(JsonFieldType.STRING).description("차별점"),
                                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                                fieldWithPath("data.pay").type(JsonFieldType.STRING).description("수업료 또는 원하는 수업료"),
                                                fieldWithPath("data.wantDate").type(JsonFieldType.STRING).description("원하는 날짜"),
                                                fieldWithPath("data.preTutoring").type(JsonFieldType.STRING).description("사전 과외 가능 여부"),
                                                fieldWithPath("data.profileImage").type(JsonFieldType.OBJECT).description("프로필 이미지 정보"),
                                                fieldWithPath("data.profileImage.profileImageId").type(JsonFieldType.NUMBER).description("프로필 이미지 식별자"),
                                                fieldWithPath("data.profileImage.url").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                                fieldWithPath("data.profileImage.createAt").type(JsonFieldType.STRING).description("프로필 이미지 생성 시각"),
                                                fieldWithPath("data.profileImage.updateAt").type(JsonFieldType.STRING).description("프로필 이미지 최종 수정 시각"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("프로필 생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("프로필 최종 생성 시각"),
                                                fieldWithPath("data.reviews").type(JsonFieldType.ARRAY).description("프로필 리뷰 리스트(튜터일때)"),
                                                fieldWithPath("data.reviews[].reviewId").type(JsonFieldType.NUMBER).description("후기 식별자"),
                                                fieldWithPath("data.reviews[].professional").type(JsonFieldType.NUMBER).description("전문성 별점"),
                                                fieldWithPath("data.reviews[].readiness").type(JsonFieldType.NUMBER).description("준비성 별점"),
                                                fieldWithPath("data.reviews[].explanation").type(JsonFieldType.NUMBER).description("수업 설명 별점"),
                                                fieldWithPath("data.reviews[].punctuality").type(JsonFieldType.NUMBER).description("시간 준수 별점"),
                                                fieldWithPath("data.reviews[].reviewBody").type(JsonFieldType.STRING).description("후기 내용"),
                                                fieldWithPath("data.reviews[].tuteeName").type(JsonFieldType.STRING).description("후기 작성자(튜티)"),
                                                fieldWithPath("data.reviews[].createAt").type(JsonFieldType.STRING).description("후기 생성 시각"),
                                                fieldWithPath("data.reviews[].updateAt").type(JsonFieldType.STRING).description("후기 최종 수정 시각"),
                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("요청 페이지 정보"),
                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("요청 페이지 - 0 = 1 페이지"),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지당 요청 회원"),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 멤버"),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("생성된 총 페이지")


                                        )
                                )
                        ));
    }


}