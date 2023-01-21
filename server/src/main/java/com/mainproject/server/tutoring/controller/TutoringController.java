package com.mainproject.server.tutoring.controller;

import com.mainproject.server.dateNotice.dto.DateNoticePatchDto;
import com.mainproject.server.dateNotice.dto.DateNoticePostDto;
import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.mapper.DateNoticeMapper;
import com.mainproject.server.dateNotice.service.DateNoticeService;
import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.tutoring.dto.*;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.mapper.TutoringMapper;
import com.mainproject.server.tutoring.service.TutoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tutoring")
@Validated
public class TutoringController {
    private final DateNoticeMapper dateNoticeMapper;
    private final DateNoticeService dateNoticeService;
    private final TutoringMapper tutoringMapper;
    private final TutoringService tutoringService;

    @PostMapping("/{profileId}")
    public ResponseEntity postTutoring(
            @PathVariable("profileId") @Positive Long profileId,
            @RequestBody @Valid TutoringPostDto tutoringPostDto
            ) {
        Long messageRoomId = tutoringPostDto.getMessageRoomId();

        Tutoring postTutoring = tutoringMapper.tutoringPostDtoToTutoring(tutoringPostDto);
        Tutoring tutoring = tutoringService.createTutoring(postTutoring, profileId, messageRoomId);

        return new ResponseEntity(
                ResponseDto.of(tutoringMapper.tutoringToTutoringSimpleResponseDto(tutoring)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity getAllTutoring(
            @RequestParam Map<String, String> params,
            @PathVariable("profileId") @Positive Long profileId,
            @PageableDefault(page = 0, size = 10, sort = "tutoringId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Tutoring> pageTutoring =
                tutoringService.getAllTutoring(params, profileId, pageable);
        List<Tutoring> tutoringList = pageTutoring.getContent();
        List<TutoringSimpleResponseDto> tutoringSimpleList =
                tutoringMapper.tutoringListToTutoringSimpleResponseDtoList(tutoringList);
        Page<TutoringSimpleResponseDto> page = new PageImpl<>(
                tutoringSimpleList,
                pageTutoring.getPageable(),
                pageTutoring.getTotalElements());
        return new ResponseEntity(
                PageResponseDto.of(tutoringSimpleList, page),
                HttpStatus.OK);
    }

    @PatchMapping("/details/{profileId}/{tutoringId}")
    public ResponseEntity patchTutoringMatch(
            @PathVariable("profileId") @Positive Long profileId,
            @PathVariable("tutoringId") @Positive Long tutoringId,
            @PageableDefault(page = 0, size = 5, sort = "dateNoticeId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        TutoringDto tutoring =
                tutoringService.setTutoringStatusProgress(tutoringId, profileId, pageable);
        Page<DateNoticeResponseDto> dateNotices = tutoring.getDateNotices();
        TutoringResponseDto responseDto = TutoringResponseDto.of(tutoring);
        PageResponseDto response = PageResponseDto.of(responseDto, dateNotices);
        return new ResponseEntity(
                response,
                HttpStatus.OK);
    }


    @GetMapping("/details/{profileId}/{tutoringId}")
    public ResponseEntity getTutoring(
            @PathVariable("profileId") @Positive Long profileId,
            @PathVariable("tutoringId") @Positive Long tutoringId,
            @PageableDefault(page = 0, size = 5, sort = "dateNoticeId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        TutoringDto tutoring =
                tutoringService.getTutoring(tutoringId, profileId, pageable);
        Page<DateNoticeResponseDto> dateNotices = tutoring.getDateNotices();
        TutoringResponseDto responseDto = TutoringResponseDto.of(tutoring);
        PageResponseDto response = PageResponseDto.of(responseDto, dateNotices);
        return new ResponseEntity(
                response,
                HttpStatus.OK);
    }

    @PatchMapping("/details/{tutoringId}")
    public ResponseEntity patchTutoring(
            @PathVariable("tutoringId") @Positive Long tutoringId,
            @RequestBody @Valid TutoringPatchDto tutoringPatchDto,
            @PageableDefault(page = 0, size = 5, sort = "dateNoticeId", direction = Sort.Direction.DESC)
            Pageable pageable
            ) {
        TutoringDto tutoring = tutoringService.updateTutoring(
                tutoringMapper.tutoringPatchDtoToTutoring(tutoringPatchDto),
                tutoringId,
                pageable
        );
        Page<DateNoticeResponseDto> dateNotices = tutoring.getDateNotices();
        TutoringResponseDto responseDto = TutoringResponseDto.of(tutoring);
        PageResponseDto response = PageResponseDto.of(responseDto, dateNotices);
        return new ResponseEntity(
                response,
                HttpStatus.OK);
    }

    @DeleteMapping("/details/{tutoringId}")
    public ResponseEntity deleteTutoring(
            @PathVariable("tutoringId") @Positive Long tutoringId
    ) {
        tutoringService.deleteTutoring(tutoringId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/date-notice/{tutoringId}")
    public ResponseEntity postDateNotice(
            @PathVariable("tutoringId") @Positive Long tutoringId,
            @RequestBody @Valid DateNoticePostDto dateNoticePostDto
            ) {
        DateNotice postDateNotice =
                dateNoticeMapper.dateNoticePostDtoToDateNotice(dateNoticePostDto);
        DateNotice dateNotice =
                dateNoticeService.createDateNotice(postDateNotice, tutoringId);
        return new ResponseEntity(
                ResponseDto.of(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(dateNotice)),
                HttpStatus.CREATED);
    }

    @GetMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity getDateNotice(
            @PathVariable("dateNoticeId") @Positive Long dateNoticeId
    ) {
        DateNotice dateNotice = dateNoticeService.getDateNotice(dateNoticeId);

        return new ResponseEntity(
                ResponseDto.of(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(dateNotice)),
                HttpStatus.OK);
    }

    @PatchMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity patchDateNotice(
            @PathVariable("dateNoticeId") @Positive Long dateNoticeId,
            @RequestBody @Valid DateNoticePatchDto dateNoticePatchDto
            ) {
        dateNoticePatchDto.setDateNoticeId(dateNoticeId);
        DateNotice patchDateNotice = dateNoticeMapper.dateNoticePatchDtoToDateNotice(dateNoticePatchDto);
        DateNotice dateNotice = dateNoticeService.updateDateNotice(patchDateNotice);

        return new ResponseEntity(
                ResponseDto.of(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(dateNotice)),
                HttpStatus.OK);
    }

    @DeleteMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity deleteDateNotice(
            @PathVariable("dateNoticeId") @Positive Long dateNoticeId
    ) {
        dateNoticeService.deleteDateNotice(dateNoticeId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
