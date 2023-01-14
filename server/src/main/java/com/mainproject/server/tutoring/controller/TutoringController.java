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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tutoring")
public class TutoringController {
    private final DateNoticeMapper dateNoticeMapper;
    private final DateNoticeService dateNoticeService;
    private final TutoringMapper tutoringMapper;
    private final TutoringService tutoringService;


    @PostMapping("/{profileId}")
    public ResponseEntity postTutoring(
            @PathVariable("profileId") Long profileId,
            @RequestBody TutoringPostDto tutoringPostDto
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
            @PathVariable("profileId") Long profileId,
            @PageableDefault(page = 0, size = 10, sort = "profileId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Tutoring> pageTutoring = tutoringService.getAllTutoring(profileId, pageable);
        List<Tutoring> tutoringList = pageTutoring.getContent();
        List<TutoringSimpleResponseDto> tutoringSimpleList = tutoringMapper.tutoringListToTutoringSimpleResponseDtoList(tutoringList);
        Page<TutoringSimpleResponseDto> page = new PageImpl<>(
                tutoringSimpleList,
                pageTutoring.getPageable(),
                pageTutoring.getTotalElements());
        return new ResponseEntity(
                PageResponseDto.of(tutoringSimpleList, page),
                HttpStatus.OK);
    }

    @PostMapping("/details/{tutoringId}")
    public ResponseEntity postTutoringMatch(
            @PathVariable("tutoringId") Long tutoringId,
            @PageableDefault(page = 0, size = 5, sort = "dateNoticeId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        TutoringDto tutoring = tutoringService.setTutoringStatusProgress(tutoringId, pageable);
        Page<DateNoticeResponseDto> dateNotices = tutoring.getDateNotices();
        TutoringResponseDto responseDto = TutoringResponseDto.of(tutoring);
        PageResponseDto response = PageResponseDto.of(responseDto, dateNotices);
        return new ResponseEntity(
                response,
                HttpStatus.OK);
    }


    @GetMapping("/details/{tutoringId}")
    public ResponseEntity getTutoring(
            @PathVariable("tutoringId") Long tutoringId,
            @PageableDefault(page = 0, size = 5, sort = "dateNoticeId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        TutoringDto tutoring = tutoringService.getTutoring(tutoringId, pageable);
        Page<DateNoticeResponseDto> dateNotices = tutoring.getDateNotices();
        TutoringResponseDto responseDto = TutoringResponseDto.of(tutoring);
        PageResponseDto response = PageResponseDto.of(responseDto, dateNotices);
        return new ResponseEntity(
                response,
                HttpStatus.OK);
    }

    @PatchMapping("/details/{tutoringId}")
    public ResponseEntity patchTutoring(
            @PathVariable("tutoringId") Long tutoringId,
            @RequestBody TutoringPatchDto tutoringPatchDto,
            @PageableDefault(page = 0, size = 5, sort = "dateNoticeId", direction = Sort.Direction.DESC)
            Pageable pageable
            ) {
        tutoringPatchDto.setTutoringId(tutoringId);
        TutoringDto tutoring = tutoringService.updateTutoring(
                tutoringMapper.tutoringPatchDtoToTutoring(tutoringPatchDto),
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
            @PathVariable("tutoringId") Long tutoringId
    ) {
        tutoringService.deleteTutoring(tutoringId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/date-notice")
    public ResponseEntity postDateNotice(
            @RequestBody DateNoticePostDto dateNoticePostDto
            ) {
        DateNotice postDateNotice = dateNoticeMapper.dateNoticePostDtoToDateNotice(dateNoticePostDto);
        DateNotice dateNotice = dateNoticeService.createDateNotice(postDateNotice);

        return new ResponseEntity(
                ResponseDto.of(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(dateNotice)),
                HttpStatus.CREATED);
    }

    @GetMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity getDateNotice(
            @PathVariable("dateNoticeId") Long dateNoticeId,
            Principal principal
    ) {
        String email = principal.getName();

        DateNotice dateNotice = dateNoticeService.findDateNotice(dateNoticeId, email);

        return new ResponseEntity(
                ResponseDto.of(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(dateNotice)),
                HttpStatus.OK);
    }

    @PatchMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity patchDateNotice(
            @PathVariable("dateNoticeId") Long dateNoticeId,
            @RequestBody DateNoticePatchDto dateNoticePatchDto
            ) {
        DateNotice patchDateNotice = dateNoticeMapper.dateNoticePatchDtoToDateNotice(dateNoticePatchDto);
        DateNotice dateNotice = dateNoticeService.updateDateNotice(patchDateNotice);

        return new ResponseEntity(
                ResponseDto.of(dateNoticeMapper.dateNoticeToDateNoticeResponseDto(dateNotice)),
                HttpStatus.OK);
    }

    @DeleteMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity deleteDateNotice(
            @PathVariable("dateNoticeId") Long dateNoticeId
    ) {
        dateNoticeService.deleteDateNotice(dateNoticeId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
