package com.mainproject.server.message.controller;

import com.google.gson.Gson;
import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.config.SecurityConfig;
import com.mainproject.server.constant.MessageStatus;
import com.mainproject.server.message.dto.*;
import com.mainproject.server.message.service.MessageService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class,
        JwtTokenizer.class,
        WebHookService.class,
        CustomOAuth2UserService.class,
        OAuth2UserSuccessHandler.class,
        SecurityConfig.class})
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshService refreshService;

    @MockBean
    private RefreshRepository refreshRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private MessageService messageService;


    @Test
    @DisplayName("????????? ?????? TEST")
    @WithMockUser
    void postMessage() throws Exception {
        // Given
        MessagePostDto postDto = MessagePostDto.builder()
                .senderId(1L)
                .receiverId(2L)
                .messageRoomId(1L)
                .messageContent("test")
                .build();
        MessageResponseDto responseDto = createMessageResponseDto();
        given(messageService.createMessage(any(MessagePostDto.class)))
                .willReturn(responseDto);
        Gson gson = new Gson();
        String content = gson.toJson(postDto);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/messages")
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document("postMessage",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.requestFields(
                                        List.of(
                                                fieldWithPath("senderId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                                fieldWithPath("receiverId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                                fieldWithPath("messageRoomId").type(JsonFieldType.NUMBER).description("????????? ??? ?????????"),
                                                fieldWithPath("messageContent").type(JsonFieldType.STRING).description("????????? ??????")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.messageRoomId").type(JsonFieldType.NUMBER).description("????????? ??? ?????????"),
                                                fieldWithPath("data.messageId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                                fieldWithPath("data.senderId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                                fieldWithPath("data.senderName").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                                fieldWithPath("data.receiverId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                                fieldWithPath("data.receiverName").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                                fieldWithPath("data.messageContent").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("????????? ?????? ??????")

                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("????????? ??? ?????? TEST")
    @WithMockUser
    void postMessageRoom() throws Exception {
        // Given
        Long profileId = 1L;
        MessageRoomPostDto postDto = MessageRoomPostDto.builder()
                .tuteeId(1L)
                .tutorId(2L)
                .build();
        MessageRoomSimpleResponseDto response = createRoomSimpleResponseDto();
        Gson gson = new Gson();
        String content = gson.toJson(postDto);
        given(messageService.createMessageRoom(any(MessageRoomPostDto.class), anyLong()))
                .willReturn(response);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/messages/{profileId}", profileId)
                .header("Authorization", "Access Token Value")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document("postMessageRoom",
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
                                                fieldWithPath("tutorId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                                fieldWithPath("tuteeId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????")
                                        )

                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.messageRoomId").type(JsonFieldType.NUMBER).description("????????? ??? ?????????"),
                                                fieldWithPath("data.messageStatus").type(JsonFieldType.STRING).description("????????? ??? ??????"),
                                                fieldWithPath("data.lastMessage").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                                fieldWithPath("data.lastSenderId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????? ????????? ?????????"),
                                                fieldWithPath("data.targetName").type(JsonFieldType.STRING).description("????????? ??? ????????? ????????? ??????"),
                                                fieldWithPath("data.targetProfileUrl").type(JsonFieldType.STRING).description("????????? ????????? ?????? URL"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("????????? ??? ?????? ??????")

                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("?????? ????????? ????????? ??? ????????? ?????? TEST")
    @WithMockUser
    void getMessageRooms() throws Exception {
        // Given
        Long profileId = 1L;
        Pageable pageable = PageRequest.of(0, 7);
        MessageRoomSimpleResponseDto response = createRoomSimpleResponseDto();
        Page<MessageRoomSimpleResponseDto> responseDtoPage =
                new PageImpl<>(List.of(response, response));
        given(messageService.getMessageRooms(anyLong(), any(Pageable.class)))
                .willReturn(responseDtoPage);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/messages/{profileId}", profileId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("getMessageRooms",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("????????? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                                fieldWithPath("data.[]messageRoomId").type(JsonFieldType.NUMBER).description("????????? ??? ?????????"),
                                                fieldWithPath("data.[]messageStatus").type(JsonFieldType.STRING).description("????????? ??? ??????"),
                                                fieldWithPath("data.[]lastMessage").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                                fieldWithPath("data.[]lastSenderId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????? ????????? ?????????"),
                                                fieldWithPath("data.[]targetName").type(JsonFieldType.STRING).description("????????? ??? ????????? ????????? ??????"),
                                                fieldWithPath("data.[]targetProfileUrl").type(JsonFieldType.STRING).description("????????? ????????? ?????? URL"),
                                                fieldWithPath("data.[]createAt").type(JsonFieldType.STRING).description("????????? ??? ?????? ??????"),
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
    @DisplayName("?????? ????????? ??? ????????? ????????? ?????? TEST")
    @WithMockUser
    void getMessages() throws Exception {
        // Given
        Long profileId = 1L;
        Long messageRoomId = 1L;
        MessageRoomResponseDto response = ResponseStubData.createMessageRoomResponse();
        given(messageService.getMessageRoom(anyLong(), anyLong()))
                .willReturn(response);
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/messages/rooms/{profileId}/{messageRoomId}", profileId, messageRoomId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("getMessages",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("????????? ?????????"),
                                        parameterWithName("messageRoomId").description("????????? ??? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.messageRoomId").type(JsonFieldType.NUMBER).description("????????? ??? ?????????"),
                                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("????????? ??? ?????? ??????"),
                                                fieldWithPath("data.tutorId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                                fieldWithPath("data.tutorName").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.tuteeId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                                fieldWithPath("data.tuteeName").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.tutoringId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.messages").type(JsonFieldType.ARRAY).description("????????? ?????????"),
                                                fieldWithPath("data.messages.[]messageRoomId").type(JsonFieldType.NUMBER).description("????????? ??? ?????????"),
                                                fieldWithPath("data.messages.[]messageId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                                fieldWithPath("data.messages.[]senderId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????"),
                                                fieldWithPath("data.messages.[]senderName").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                                fieldWithPath("data.messages.[]receiverId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                                fieldWithPath("data.messages.[]receiverName").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                                fieldWithPath("data.messages.[]messageContent").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data.messages.[]createAt").type(JsonFieldType.STRING).description("????????? ?????? ??????")

                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("????????? ??? ?????? TEST")
    @WithMockUser
    void deleteMessageRoom() throws Exception {
        // Given
        Long messageRoomId = 1L;
        doNothing().when(messageService).deleteMessageRoom(anyLong());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/messages/rooms/{messageRoomId}", messageRoomId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteMessageRoom",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("messageRoomId").description("????????? ??? ?????????")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }


    private MessageRoomSimpleResponseDto createRoomSimpleResponseDto() {
        return MessageRoomSimpleResponseDto.builder()
                .messageRoomId(1L)
                .messageStatus(MessageStatus.UNCHECK.name())
                .lastMessage("test")
                .lastSenderId(1L)
                .targetName("test")
                .targetProfileUrl("test")
                .createAt(LocalDateTime.now())
                .build();
    }

    private MessageResponseDto createMessageResponseDto() {
        return MessageResponseDto.builder()
                .messageRoomId(1L)
                .messageId(1L)
                .senderId(1L)
                .receiverId(2L)
                .senderName("test1")
                .receiverName("test2")
                .messageContent("test")
                .createAt(LocalDateTime.now())
                .build();
    }
}