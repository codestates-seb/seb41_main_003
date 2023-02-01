package com.mainproject.server.review.controller;

import com.google.gson.Gson;
import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.config.SecurityConfig;
import com.mainproject.server.profile.controller.ProfileController;
import com.mainproject.server.review.dto.ReviewPatchDto;
import com.mainproject.server.review.dto.ReviewPostDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.review.mapper.ReviewMapper;
import com.mainproject.server.review.service.ReviewService;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class,
        JwtTokenizer.class,
        WebHookService.class,
        CustomOAuth2UserService.class,
        OAuth2UserSuccessHandler.class,
        SecurityConfig.class})
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshService refreshService;

    @MockBean
    private RefreshRepository refreshRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ReviewMapper reviewMapper;

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("특정 후기 조회 TEST")
    @WithMockUser
    void getReview() throws Exception {
        // Given
        Long reviewId = 1L;
        ReviewResponseDto reviewResponse = ResponseStubData.createReviewResponse();
        given(reviewService.getReview(anyLong())).willReturn(new Review());
        given(reviewMapper.reviewToReviewResponseDto(any(Review.class)))
                .willReturn(reviewResponse);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/reviews/{reviewId}", reviewId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("getReview",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("reviewId").description("후기 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("후기 식별자"),
                                                fieldWithPath("data.professional").type(JsonFieldType.NUMBER).description("전문성 별점"),
                                                fieldWithPath("data.readiness").type(JsonFieldType.NUMBER).description("준비성 별점"),
                                                fieldWithPath("data.explanation").type(JsonFieldType.NUMBER).description("설명 별점"),
                                                fieldWithPath("data.punctuality").type(JsonFieldType.NUMBER).description("시간 준수 별점"),
                                                fieldWithPath("data.reviewBody").type(JsonFieldType.STRING).description("후기 내용"),
                                                fieldWithPath("data.tuteeName").type(JsonFieldType.STRING).description("튜티 이름"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("후기 등록 TEST")
    @WithMockUser
    void postReview() throws Exception {
        // Given
        Long tutoringId = 1L;
        ReviewResponseDto reviewResponse = ResponseStubData.createReviewResponse();
        ReviewPostDto postDto = createReviewPost();
        given(reviewService.createReview(any(Review.class), anyLong())).willReturn(new Review());
        given(reviewMapper.reviewPostDtoToReview(any(ReviewPostDto.class))).willReturn(new Review());
        given(reviewMapper.reviewToReviewResponseDto(any(Review.class)))
                .willReturn(reviewResponse);
        Gson gson = new Gson();
        String content = gson.toJson(postDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/reviews/{tutoringId}", tutoringId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document("postReview",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("tutoringId").description("과외 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("professional").type(JsonFieldType.NUMBER).description("전문성 별점"),
                                                fieldWithPath("readiness").type(JsonFieldType.NUMBER).description("준비성 별점"),
                                                fieldWithPath("explanation").type(JsonFieldType.NUMBER).description("설명력 별점"),
                                                fieldWithPath("punctuality").type(JsonFieldType.NUMBER).description("시간 준수 별점"),
                                                fieldWithPath("reviewBody").type(JsonFieldType.STRING).description("후기 내용")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("후기 식별자"),
                                                fieldWithPath("data.professional").type(JsonFieldType.NUMBER).description("전문성 별점"),
                                                fieldWithPath("data.readiness").type(JsonFieldType.NUMBER).description("준비성 별점"),
                                                fieldWithPath("data.explanation").type(JsonFieldType.NUMBER).description("설명력 별점"),
                                                fieldWithPath("data.punctuality").type(JsonFieldType.NUMBER).description("시간 준수 별점"),
                                                fieldWithPath("data.reviewBody").type(JsonFieldType.STRING).description("후기 내용"),
                                                fieldWithPath("data.tuteeName").type(JsonFieldType.STRING).description("튜티 이름"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("후기 수정 TEST")
    @WithMockUser
    void patchReview() throws Exception {
        // Given
        Long tutoringId = 1L;
        ReviewResponseDto reviewResponse = ResponseStubData.createReviewResponse();
        ReviewPatchDto patchDto = createReviewPatch();
        given(reviewService.updateReview(any(Review.class), anyLong())).willReturn(new Review());
        given(reviewMapper.reviewPatchDtoToReview(any(ReviewPatchDto.class))).willReturn(new Review());
        given(reviewMapper.reviewToReviewResponseDto(any(Review.class)))
                .willReturn(reviewResponse);
        Gson gson = new Gson();
        String content = gson.toJson(patchDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/reviews/{tutoringId}", tutoringId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("patchReview",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("tutoringId").description("과외 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("professional").type(JsonFieldType.NUMBER).description("전문성 별점"),
                                                fieldWithPath("readiness").type(JsonFieldType.NUMBER).description("준비성 별점"),
                                                fieldWithPath("explanation").type(JsonFieldType.NUMBER).description("설명력 별점"),
                                                fieldWithPath("punctuality").type(JsonFieldType.NUMBER).description("시간 준수 별점"),
                                                fieldWithPath("reviewBody").type(JsonFieldType.STRING).description("후기 내용")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("후기 식별자"),
                                                fieldWithPath("data.professional").type(JsonFieldType.NUMBER).description("전문성 별점"),
                                                fieldWithPath("data.readiness").type(JsonFieldType.NUMBER).description("준비성 별점"),
                                                fieldWithPath("data.explanation").type(JsonFieldType.NUMBER).description("설명력 별점"),
                                                fieldWithPath("data.punctuality").type(JsonFieldType.NUMBER).description("시간 준수 별점"),
                                                fieldWithPath("data.reviewBody").type(JsonFieldType.STRING).description("후기 내용"),
                                                fieldWithPath("data.tuteeName").type(JsonFieldType.STRING).description("튜티 이름"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                        )
                                )
                        ));
    }

    public static ReviewPostDto createReviewPost() {
        return ReviewPostDto.builder()
                .professional(4)
                .readiness(4)
                .explanation(5)
                .punctuality(5)
                .reviewBody("TestBody")
                .build();
    }

    public static ReviewPatchDto createReviewPatch() {
        return ReviewPatchDto.builder()
                .professional(4)
                .readiness(4)
                .explanation(5)
                .punctuality(5)
                .reviewBody("TestBody")
                .build();
    }


}