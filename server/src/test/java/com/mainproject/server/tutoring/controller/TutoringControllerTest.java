package com.mainproject.server.tutoring.controller;

import com.google.gson.Gson;
import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.config.SecurityConfig;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.dateNotice.dto.DateNoticePatchDto;
import com.mainproject.server.dateNotice.dto.DateNoticePostDto;
import com.mainproject.server.dateNotice.dto.HomeworkPatchDto;
import com.mainproject.server.dateNotice.dto.HomeworkPostDto;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.mapper.DateNoticeMapper;
import com.mainproject.server.dateNotice.service.DateNoticeService;
import com.mainproject.server.tutoring.dto.TutoringDto;
import com.mainproject.server.tutoring.dto.TutoringPatchDto;
import com.mainproject.server.tutoring.dto.TutoringPostDto;
import com.mainproject.server.tutoring.dto.TutoringSimpleResponseDto;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.mapper.TutoringMapper;
import com.mainproject.server.tutoring.service.TutoringService;
import com.mainproject.server.user.service.UserService;
import com.mainproject.server.utils.ApiDocumentUtils;
import com.mainproject.server.utils.ResponseStubData;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TutoringController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class,
        JwtTokenizer.class,
        WebHookService.class,
        CustomOAuth2UserService.class,
        OAuth2UserSuccessHandler.class,
        SecurityConfig.class})
class TutoringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshService refreshService;

    @MockBean
    private RefreshRepository refreshRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private DateNoticeMapper dateNoticeMapper;

    @MockBean
    private DateNoticeService dateNoticeService;

    @MockBean
    private TutoringMapper tutoringMapper;

    @MockBean
    private TutoringService tutoringService;

    @Test
    @DisplayName("특정 프로필의 과외 생성 TEST")
    @WithMockUser
    void postTutoring() throws Exception {
        // Given
        Long profileId = 1L;
        TutoringSimpleResponseDto tutoringSimpleResponse =
                ResponseStubData.createTutoringSimpleResponse();
        TutoringPostDto postDto = new TutoringPostDto();
        postDto.setTutorId(1L);
        postDto.setTuteeId(2L);
        postDto.setTutoringTitle("test");
        postDto.setMessageRoomId(1L);
        Gson gson = new Gson();
        String content = gson.toJson(postDto);
        given(tutoringMapper.tutoringPostDtoToTutoring(any(TutoringPostDto.class)))
                .willReturn(new Tutoring());
        given(tutoringService.createTutoring(any(Tutoring.class), anyLong(), anyLong()))
                .willReturn(new Tutoring());
        given(tutoringMapper.tutoringToTutoringSimpleResponseDto(any(Tutoring.class)))
                .willReturn(tutoringSimpleResponse);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/tutoring/{profileId}", profileId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document("postTutoring",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("프로필 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.tutoringId").type(JsonFieldType.NUMBER).description("과외 식별자"),
                                                fieldWithPath("data.tutorName").type(JsonFieldType.STRING).description("튜터 이름"),
                                                fieldWithPath("data.tuteeName").type(JsonFieldType.STRING).description("튜티 이름"),
                                                fieldWithPath("data.tutoringTitle").type(JsonFieldType.STRING).description("과외 타이틀"),
                                                fieldWithPath("data.tutoringStatus").type(JsonFieldType.STRING).description("과외 상태"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")

                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("특정 프로필의 전체 과외 리스트 조회 TEST")
    @WithMockUser
    void getAllTutoring() throws Exception {
        // Given
        Long profileId = 1L;
        Pageable pageable = PageRequest.of(1, 10);
        TutoringSimpleResponseDto tutoringSimpleResponse =
                ResponseStubData.createTutoringSimpleResponse();
        Page<TutoringSimpleResponseDto> pageProfile =
                new PageImpl<>(List.of(
                        tutoringSimpleResponse,
                        tutoringSimpleResponse),
                        pageable, 2);
        given(tutoringService.getAllTutoring(anyMap(), anyLong(), any(Pageable.class)))
                .willReturn(pageProfile);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/tutoring/{profileId}?page=0", profileId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("getAllTutoring",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("프로필 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                RequestDocumentation.requestParameters(
                                        parameterWithName("page").description("요청 페이지 정보")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                fieldWithPath("data[].tutoringId").type(JsonFieldType.NUMBER).description("과외 식별자"),
                                                fieldWithPath("data[].tutorName").type(JsonFieldType.STRING).description("튜터 이름"),
                                                fieldWithPath("data[].tuteeName").type(JsonFieldType.STRING).description("튜티 이름"),
                                                fieldWithPath("data[].tutoringTitle").type(JsonFieldType.STRING).description("과외 타이틀"),
                                                fieldWithPath("data[].tutoringStatus").type(JsonFieldType.STRING).description("과외 상태"),
                                                fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
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
    @DisplayName("특정 과외 매칭 요청 승인 TEST")
    @WithMockUser
    void patchTutoringMatch() throws Exception {
        // Given
        Long profileId = 1L;
        Long tutoringId = 1L;
        TutoringDto tutoringDto = ResponseStubData.createTutoringDto();
        given(tutoringService.setTutoringStatusProgress(anyLong(), anyLong(), any(Pageable.class)))
                .willReturn(tutoringDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/tutoring/details/{profileId}/{tutoringId}", profileId, tutoringId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("patchTutoringMatch",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("프로필 식별자"),
                                        parameterWithName("tutoringId").description("과외 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.tutoringId").type(JsonFieldType.NUMBER).description("과외 식별자"),
                                                fieldWithPath("data.tutoringTitle").type(JsonFieldType.STRING).description("튜터 타이틀"),
                                                fieldWithPath("data.tutoringStatus").type(JsonFieldType.STRING).description("과외 상태 PROGRESS/WAIT_FINISH/TUTOR_DELETE/TUTEE_DELETE"),
                                                fieldWithPath("data.latestNoticeId").type(JsonFieldType.NUMBER).description("마지막 공지 식별자"),
                                                fieldWithPath("data.latestNoticeBody").type(JsonFieldType.STRING).description("마지막 공지 내용"),
                                                fieldWithPath("data.tuteeId").type(JsonFieldType.NUMBER).description("튜티 식별자"),
                                                fieldWithPath("data.tuteeName").type(JsonFieldType.STRING).description("튜티 이름"),
                                                fieldWithPath("data.tutorId").type(JsonFieldType.NUMBER).description("튜터 식별자"),
                                                fieldWithPath("data.tutorName").type(JsonFieldType.STRING).description("튜터 이름"),
                                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("과외에 대한 후기 식별자"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                                fieldWithPath("data.dateNotices").type(JsonFieldType.ARRAY).description("일지 목록"),
                                                fieldWithPath("data.dateNotices[].dateNoticeId").type(JsonFieldType.NUMBER).description("일지 식별자"),
                                                fieldWithPath("data.dateNotices[].dateNoticeTitle").type(JsonFieldType.STRING).description("일지 타이틀"),
                                                fieldWithPath("data.dateNotices[].startTime").type(JsonFieldType.STRING).description("시작 시간"),
                                                fieldWithPath("data.dateNotices[].endTime").type(JsonFieldType.STRING).description("종료 시간"),
                                                fieldWithPath("data.dateNotices[].homeworkCount").type(JsonFieldType.NUMBER).description("과제 갯수"),
                                                fieldWithPath("data.dateNotices[].finishHomeworkCount").type(JsonFieldType.NUMBER).description("종료된 과제 갯수"),
                                                fieldWithPath("data.dateNotices[].noticeStatus").type(JsonFieldType.STRING).description("공지 등록 여부"),
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
    @DisplayName("특정 과외 상세 조회 TEST")
    @WithMockUser
    void getTutoring() throws Exception {
        // Given
        Long profileId = 1L;
        Long tutoringId = 1L;
        TutoringDto tutoringDto = ResponseStubData.createTutoringDto();
        given(tutoringService.getTutoring(anyLong(), anyLong(), any(Pageable.class)))
                .willReturn(tutoringDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/tutoring/details/{profileId}/{tutoringId}", profileId, tutoringId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("getTutoring",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("프로필 식별자"),
                                        parameterWithName("tutoringId").description("과외 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.tutoringId").type(JsonFieldType.NUMBER).description("과외 식별자"),
                                                fieldWithPath("data.tutoringTitle").type(JsonFieldType.STRING).description("튜터 타이틀"),
                                                fieldWithPath("data.tutoringStatus").type(JsonFieldType.STRING).description("과외 상태 TUTEE_WAITING/TUTOR_WAITING/PROGRESS/UNCHECK/WAIT_FINISH/FINISH"),
                                                fieldWithPath("data.latestNoticeId").type(JsonFieldType.NUMBER).description("마지막 공지 식별자"),
                                                fieldWithPath("data.latestNoticeBody").type(JsonFieldType.STRING).description("마지막 공지 내용"),
                                                fieldWithPath("data.tuteeId").type(JsonFieldType.NUMBER).description("튜티 식별자"),
                                                fieldWithPath("data.tuteeName").type(JsonFieldType.STRING).description("튜티 이름"),
                                                fieldWithPath("data.tutorId").type(JsonFieldType.NUMBER).description("튜터 식별자"),
                                                fieldWithPath("data.tutorName").type(JsonFieldType.STRING).description("튜터 이름"),
                                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("과외에 대한 후기 식별자"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                                fieldWithPath("data.dateNotices").type(JsonFieldType.ARRAY).description("일지 목록"),
                                                fieldWithPath("data.dateNotices[].dateNoticeId").type(JsonFieldType.NUMBER).description("일지 식별자"),
                                                fieldWithPath("data.dateNotices[].dateNoticeTitle").type(JsonFieldType.STRING).description("일지 타이틀"),
                                                fieldWithPath("data.dateNotices[].startTime").type(JsonFieldType.STRING).description("시작 시간"),
                                                fieldWithPath("data.dateNotices[].endTime").type(JsonFieldType.STRING).description("종료 시간"),
                                                fieldWithPath("data.dateNotices[].homeworkCount").type(JsonFieldType.NUMBER).description("과제 갯수"),
                                                fieldWithPath("data.dateNotices[].finishHomeworkCount").type(JsonFieldType.NUMBER).description("종료된 과제 갯수"),
                                                fieldWithPath("data.dateNotices[].noticeStatus").type(JsonFieldType.STRING).description("공지 등록 여부"),
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
    @DisplayName("특정 과외 상태 및 타이틀 수정 TEST")
    @WithMockUser
    void patchTutoring() throws Exception {
        // Given
        Long tutoringId = 1L;
        Pageable pageable = PageRequest.of(1, 6);
        TutoringPatchDto patchDto = new TutoringPatchDto();
        TutoringDto tutoringDto = ResponseStubData.createTutoringDto();
        patchDto.setTutoringStatus(TutoringStatus.PROGRESS.name());
        patchDto.setTutoringTitle("타이틀");
        given(tutoringService.updateTutoring(any(Tutoring.class), anyLong(), any(Pageable.class)))
                .willReturn(tutoringDto);
        given(tutoringMapper.tutoringPatchDtoToTutoring(any(TutoringPatchDto.class)))
                .willReturn(new Tutoring());
        Gson gson = new Gson();
        String content = gson.toJson(patchDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/tutoring/details/{tutoringId}", tutoringId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("patchTutoring",
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
                                                fieldWithPath("tutoringTitle").type(JsonFieldType.STRING).description("과외 타이틀"),
                                                fieldWithPath("tutoringStatus").type(JsonFieldType.STRING).description("과외 상태 FINISH")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.tutoringId").type(JsonFieldType.NUMBER).description("과외 식별자"),
                                                fieldWithPath("data.tutoringTitle").type(JsonFieldType.STRING).description("튜터 타이틀"),
                                                fieldWithPath("data.tutoringStatus").type(JsonFieldType.STRING).description("과외 상태 TUTEE_WAITING/TUTOR_WAITING/PROGRESS/UNCHECK/WAIT_FINISH/FINISH"),
                                                fieldWithPath("data.latestNoticeId").type(JsonFieldType.NUMBER).description("마지막 공지 식별자"),
                                                fieldWithPath("data.latestNoticeBody").type(JsonFieldType.STRING).description("마지막 공지 내용"),
                                                fieldWithPath("data.tuteeId").type(JsonFieldType.NUMBER).description("튜티 식별자"),
                                                fieldWithPath("data.tuteeName").type(JsonFieldType.STRING).description("튜티 이름"),
                                                fieldWithPath("data.tutorId").type(JsonFieldType.NUMBER).description("튜터 식별자"),
                                                fieldWithPath("data.tutorName").type(JsonFieldType.STRING).description("튜터 이름"),
                                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("과외에 대한 후기 식별자"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                                fieldWithPath("data.dateNotices").type(JsonFieldType.ARRAY).description("일지 목록"),
                                                fieldWithPath("data.dateNotices[].dateNoticeId").type(JsonFieldType.NUMBER).description("일지 식별자"),
                                                fieldWithPath("data.dateNotices[].dateNoticeTitle").type(JsonFieldType.STRING).description("일지 타이틀"),
                                                fieldWithPath("data.dateNotices[].startTime").type(JsonFieldType.STRING).description("시작 시간"),
                                                fieldWithPath("data.dateNotices[].endTime").type(JsonFieldType.STRING).description("종료 시간"),
                                                fieldWithPath("data.dateNotices[].homeworkCount").type(JsonFieldType.NUMBER).description("과제 갯수"),
                                                fieldWithPath("data.dateNotices[].finishHomeworkCount").type(JsonFieldType.NUMBER).description("종료된 과제 갯수"),
                                                fieldWithPath("data.dateNotices[].noticeStatus").type(JsonFieldType.STRING).description("공지 등록 여부"),
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
    @DisplayName("과외 삭제 TEST")
    @WithMockUser
    void deleteTutoring() throws Exception {
        // Given
        Long tutoringId = 1L;
        doNothing().when(tutoringService).deleteTutoring(anyLong());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/tutoring/details/{tutoringId}", tutoringId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteTutoring",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("tutoringId").description("과외 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }

    @Test
    @DisplayName("일지 등록 TEST")
    @WithMockUser
    void postDateNotice() throws Exception {
        // Given
        Long tutoringId = 1L;
        DateNoticePostDto dateNoticePostDto = createDateNoticePostDto();
        given(dateNoticeMapper.dateNoticePostDtoToDateNotice(any(DateNoticePostDto.class)))
                .willReturn(new DateNotice());
        given(dateNoticeService.createDateNotice(any(DateNotice.class), anyLong()))
                .willReturn(new DateNotice());
        given(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(any(DateNotice.class)))
                .willReturn(ResponseStubData.createDateNoticeResponse());
        Gson gson = new Gson();
        String content = gson.toJson(dateNoticePostDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/tutoring/date-notice/{tutoringId}", tutoringId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document("postDateNotice",
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
                                                fieldWithPath("dateNoticeTitle").type(JsonFieldType.STRING).description("일지 타이틀"),
                                                fieldWithPath("startTime").type(JsonFieldType.STRING).description("시작 시간"),
                                                fieldWithPath("endTime").type(JsonFieldType.STRING).description("종료 시각"),
                                                fieldWithPath("scheduleBody").type(JsonFieldType.STRING).description("일정 내용"),
                                                fieldWithPath("noticeBody").type(JsonFieldType.STRING).description("공지 내용"),
                                                fieldWithPath("homeworks").type(JsonFieldType.ARRAY).description("과제"),
                                                fieldWithPath("homeworks[].homeworkBody").type(JsonFieldType.STRING).description("과제 내용"),
                                                fieldWithPath("homeworks[].homeworkStatus").type(JsonFieldType.STRING).description("과제 상태 PROGRESS/FINISH")
                                        )

                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("dateNoticeTitle").type(JsonFieldType.STRING).description("일지 타이틀"),
                                                fieldWithPath("startTime").type(JsonFieldType.STRING).description("시작 시각"),
                                                fieldWithPath("endTime").type(JsonFieldType.STRING).description("종료 시각"),
                                                fieldWithPath("scheduleBody").type(JsonFieldType.STRING).description("일정 내용"),
                                                fieldWithPath("noticeBody").type(JsonFieldType.STRING).description("공지글 내용"),
                                                fieldWithPath("homeworks").type(JsonFieldType.ARRAY).description("과제 리스트"),
                                                fieldWithPath("homeworks[].homeworkBody").type(JsonFieldType.STRING).description("과제 내용"),
                                                fieldWithPath("homeworks[].homeworkStatus").type(JsonFieldType.STRING).description("과제 상태")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.dateNoticeId").type(JsonFieldType.NUMBER).description("일지 식별자"),
                                                fieldWithPath("data.dateNoticeTitle").type(JsonFieldType.STRING).description("일지 제목"),
                                                fieldWithPath("data.startTime").type(JsonFieldType.STRING).description("시작 시간"),
                                                fieldWithPath("data.endTime").type(JsonFieldType.STRING).description("종료 시간"),
                                                fieldWithPath("data.schedule").type(JsonFieldType.OBJECT).description("일정"),
                                                fieldWithPath("data.schedule.scheduleId").type(JsonFieldType.NUMBER).description("일정 식별자"),
                                                fieldWithPath("data.schedule.scheduleBody").type(JsonFieldType.STRING).description("일정 내용"),
                                                fieldWithPath("data.notice").type(JsonFieldType.OBJECT).description("공지"),
                                                fieldWithPath("data.notice.noticeId").type(JsonFieldType.NUMBER).description("공지 식별자"),
                                                fieldWithPath("data.notice.noticeBody").type(JsonFieldType.STRING).description("공지 내용"),
                                                fieldWithPath("data.homeworks").type(JsonFieldType.ARRAY).description("과제"),
                                                fieldWithPath("data.homeworks[].homeworkId").type(JsonFieldType.NUMBER).description("과제 식별자"),
                                                fieldWithPath("data.homeworks[].homeworkBody").type(JsonFieldType.STRING).description("과제 내용"),
                                                fieldWithPath("data.homeworks[].homeworkStatus").type(JsonFieldType.STRING).description("과제 상태"),
                                                fieldWithPath("data.noticeStatus").type(JsonFieldType.STRING).description("공지 글 유무 NOTICE/NONE")
                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("일지 상세 조회 TEST")
    @WithMockUser
    void getDateNotice() throws Exception {
        // Given
        Long dateNoticeId = 1L;
        given(dateNoticeService.getDateNotice(anyLong())).willReturn(new DateNotice());
        given(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(any(DateNotice.class)))
                .willReturn(ResponseStubData.createDateNoticeResponse());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/tutoring/date-notice/{dateNoticeId}", dateNoticeId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("getDateNotice",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("dateNoticeId").description("일지 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.dateNoticeId").type(JsonFieldType.NUMBER).description("일지 식별자"),
                                                fieldWithPath("data.dateNoticeTitle").type(JsonFieldType.STRING).description("일지 제목"),
                                                fieldWithPath("data.startTime").type(JsonFieldType.STRING).description("일정 시작 시각"),
                                                fieldWithPath("data.endTime").type(JsonFieldType.STRING).description("일정 종료 시각"),
                                                fieldWithPath("data.schedule").type(JsonFieldType.OBJECT).description("일정"),
                                                fieldWithPath("data.schedule.scheduleId").type(JsonFieldType.NUMBER).description("일정 식별자"),
                                                fieldWithPath("data.schedule.scheduleBody").type(JsonFieldType.STRING).description("일정 내용"),
                                                fieldWithPath("data.notice").type(JsonFieldType.OBJECT).description("공지"),
                                                fieldWithPath("data.notice.noticeId").type(JsonFieldType.NUMBER).description("공지 식별자"),
                                                fieldWithPath("data.notice.noticeBody").type(JsonFieldType.STRING).description("공지 내용"),
                                                fieldWithPath("data.homeworks").type(JsonFieldType.ARRAY).description("과제"),
                                                fieldWithPath("data.homeworks[].homeworkId").type(JsonFieldType.NUMBER).description("과제 식별자"),
                                                fieldWithPath("data.homeworks[].homeworkBody").type(JsonFieldType.STRING).description("과제 내용"),
                                                fieldWithPath("data.homeworks[].homeworkStatus").type(JsonFieldType.STRING).description("과제 상태"),
                                                fieldWithPath("data.noticeStatus").type(JsonFieldType.STRING).description("공지 글 유무 NOTICE/NONE")
                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("일지 수정 TEST")
    @WithMockUser
    void patchDateNotice() throws Exception {
        // Given
        Long dateNoticeId = 1L;
        DateNoticePatchDto patchDto = createDateNoticePatchDto();
        given(dateNoticeMapper.dateNoticePatchDtoToDateNotice(any(DateNoticePatchDto.class)))
                .willReturn(new DateNotice());
        given(dateNoticeService.updateDateNotice(any(DateNotice.class)))
                .willReturn(new DateNotice());
        given(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(any(DateNotice.class)))
                .willReturn(ResponseStubData.createDateNoticeResponse());
        Gson gson = new Gson();
        String content = gson.toJson(patchDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/tutoring/date-notice/{dateNoticeId}", dateNoticeId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("patchDateNotice",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("dateNoticeId").description("일지 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("dateNoticeTitle").type(JsonFieldType.STRING).description("일지 타이틀"),
                                                fieldWithPath("dateNoticeTitle").type(JsonFieldType.STRING).description("일지 타이틀"),
                                                fieldWithPath("startTime").type(JsonFieldType.STRING).description("시작 시간"),
                                                fieldWithPath("endTime").type(JsonFieldType.STRING).description("종료 시각"),
                                                fieldWithPath("scheduleBody").type(JsonFieldType.STRING).description("일정 내용"),
                                                fieldWithPath("noticeBody").type(JsonFieldType.STRING).description("공지 내용"),
                                                fieldWithPath("homeworks").type(JsonFieldType.ARRAY).description("과제"),
                                                fieldWithPath("homeworks[].homeworkBody").type(JsonFieldType.STRING).description("과제 내용"),
                                                fieldWithPath("homeworks[].homeworkStatus").type(JsonFieldType.STRING).description("과제 상태 PROGRESS/FINISH")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.dateNoticeId").type(JsonFieldType.NUMBER).description("일지 식별자"),
                                                fieldWithPath("data.dateNoticeTitle").type(JsonFieldType.STRING).description("일지 제목"),
                                                fieldWithPath("data.startTime").type(JsonFieldType.STRING).description("일정 시작 시각"),
                                                fieldWithPath("data.endTime").type(JsonFieldType.STRING).description("일정 종료 시각"),
                                                fieldWithPath("data.schedule").type(JsonFieldType.OBJECT).description("일정"),
                                                fieldWithPath("data.schedule.scheduleId").type(JsonFieldType.NUMBER).description("일정 식별자"),
                                                fieldWithPath("data.schedule.scheduleBody").type(JsonFieldType.STRING).description("일정 내용"),
                                                fieldWithPath("data.notice").type(JsonFieldType.OBJECT).description("공지"),
                                                fieldWithPath("data.notice.noticeId").type(JsonFieldType.NUMBER).description("공지 식별자"),
                                                fieldWithPath("data.notice.noticeBody").type(JsonFieldType.STRING).description("공지 내용"),
                                                fieldWithPath("data.homeworks").type(JsonFieldType.ARRAY).description("과제"),
                                                fieldWithPath("data.homeworks[].homeworkId").type(JsonFieldType.NUMBER).description("과제 식별자"),
                                                fieldWithPath("data.homeworks[].homeworkBody").type(JsonFieldType.STRING).description("과제 내용"),
                                                fieldWithPath("data.homeworks[].homeworkStatus").type(JsonFieldType.STRING).description("과제 상태"),
                                                fieldWithPath("data.noticeStatus").type(JsonFieldType.STRING).description("공지 글 유무 NOTICE/NONE")
                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("일지 삭제 TEST")
    @WithMockUser
    void deleteDateNotice() throws Exception {
        // Given
        Long dateNoticeId = 1L;
        doNothing().when(dateNoticeService).deleteDateNotice(anyLong());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/tutoring/date-notice/{dateNoticeId}", dateNoticeId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteDateNotice",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("dateNoticeId").description("일지 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }

    private DateNoticePostDto createDateNoticePostDto() {
        DateNoticePostDto get = new DateNoticePostDto();
        get.setDateNoticeTitle("test");
        get.setStartTime(LocalDateTime.now().toString());
        get.setEndTime(LocalDateTime.now().toString());
        get.setScheduleBody("test");
        get.setNoticeBody("test");
        HomeworkPostDto dto = new HomeworkPostDto();
        dto.setHomeworkBody("test");
        dto.setHomeworkStatus("PROGRESS");
        get.setHomeworks(List.of(dto));
        return get;
    }


    private DateNoticePatchDto createDateNoticePatchDto() {
        DateNoticePatchDto get = new DateNoticePatchDto();
        get.setDateNoticeTitle("test");
        get.setStartTime(LocalDateTime.now().toString());
        get.setEndTime(LocalDateTime.now().toString());
        get.setScheduleBody("test");
        get.setNoticeBody("test");
        HomeworkPatchDto dto = new HomeworkPatchDto();
        dto.setHomeworkBody("test");
        dto.setHomeworkStatus("PROGRESS");
        get.setHomeworks(List.of(dto));
        return get;
    }

}