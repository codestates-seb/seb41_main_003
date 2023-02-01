package com.mainproject.server.image.controller;

import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.config.SecurityConfig;
import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.image.mapper.ImageMapper;
import com.mainproject.server.image.service.ImageService;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ImageController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class,
        JwtTokenizer.class,
        WebHookService.class,
        CustomOAuth2UserService.class,
        OAuth2UserSuccessHandler.class,
        SecurityConfig.class})
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader loader;

    @MockBean
    private RefreshService refreshService;

    @MockBean
    private RefreshRepository refreshRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ImageMapper imageMapper;

    @MockBean
    private ImageService imageService;

    @Test
    @DisplayName("프로필 이미지 등록 TEST")
    @WithMockUser
    void postProfileImage() throws Exception {
        // Given
        Long profileId = 1L;
        String filename = "test.jpg";
        Resource resource = loader.getResource("classpath:/static/image/" + filename);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "image",
                filename,
        null,
                resource.getInputStream());
        ImageResponseDto imageResponse = ResponseStubData.createImageResponse();
        given(imageService.uploadProfileImage(any(), any(MultipartFile[].class)))
                .willReturn(List.of());
        given(imageMapper.entityListToImageResponseDtoList(anyList()))
                .willReturn(List.of(imageResponse));
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .multipart("/upload/profile-image/{profileId}", profileId)
                .file(mockMultipartFile)
                .header("Authorization", "Access Token Value")
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document("postProfileImage",
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
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                fieldWithPath("data.[]profileImageId").type(JsonFieldType.NUMBER).description("프로필 이미지 식별자"),
                                                fieldWithPath("data.[]url").type(JsonFieldType.STRING).description("이미지 URL"),
                                                fieldWithPath("data.[]createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.[]updateAt").type(JsonFieldType.STRING).description("수정 시각")

                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("프로필 이미지 수정 TEST")
    @WithMockUser
    void patchProfileImage() throws Exception {
        // Given
        Long profileId = 1L;
        String filename = "test.jpg";
        Resource resource = loader.getResource("classpath:/static/image/" + filename);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "image",
                filename,
                null,
                resource.getInputStream());
        ImageResponseDto imageResponse = ResponseStubData.createImageResponse();
        given(imageService.uploadProfileImage(any(), any(MultipartFile[].class)))
                .willReturn(List.of());
        given(imageMapper.entityListToImageResponseDtoList(anyList()))
                .willReturn(List.of(imageResponse));
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .multipart("/upload/profile-image/{profileId}", profileId)
                .file(mockMultipartFile)
                .header("Authorization", "Access Token Value")
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document("patchProfileImage",
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
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                fieldWithPath("data.[]profileImageId").type(JsonFieldType.NUMBER).description("프로필 이미지 식별자"),
                                                fieldWithPath("data.[]url").type(JsonFieldType.STRING).description("이미지 URL"),
                                                fieldWithPath("data.[]createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data.[]updateAt").type(JsonFieldType.STRING).description("수정 시각")

                                        )
                                )
                        ));
    }

    @Test
    @DisplayName("프로필 이미지 삭제 TEST")
    @WithMockUser
    void deleteProfileImage() throws Exception {
        // Given
        Long profileId = 1L;
        doNothing().when(imageService).deleteProfileImage(anyLong());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/upload/profile-image/{profileId}", profileId)
                .header("Authorization", "Access Token Value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteProfileImage",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("profileId").description("프로필 식별자")
                                ),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken")
                                )));
    }

}