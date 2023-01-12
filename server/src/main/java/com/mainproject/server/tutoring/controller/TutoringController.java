package com.mainproject.server.tutoring.controller;

import com.mainproject.server.dateNotice.dto.DateNoticePatchDto;
import com.mainproject.server.dateNotice.dto.DateNoticePostDto;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.mapper.DateNoticeMapper;
import com.mainproject.server.dateNotice.service.DateNoticeService;
import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.tutoring.dto.*;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.mapper.TutoringMapper;
import com.mainproject.server.tutoring.service.TutoringService;
import com.mainproject.server.utils.StubData;
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

//    private final StubData stubData;

    @PostMapping("/{profileId}")
    public ResponseEntity postTutoring(
            @PathVariable("profileId") Long profileId,
            @RequestBody TutoringPostDto tutoringPostDto
            ) {
        Tutoring postTutoring = tutoringMapper.tutoringPostDtoToTutoring(tutoringPostDto);
        Tutoring tutoring = tutoringService.createTutoring(postTutoring, tutoringPostDto.getTutorId(), tutoringPostDto.getTuteeId(), profileId);

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
        Page<Tutoring> pageTutoring = tutoringService.findAllTutoring(profileId, pageable);
        List<Tutoring> tutoringList = pageTutoring.getContent();
        List<TutoringSimpleResponseDto> tutoringSimpleList = tutoringMapper.tutoringListToTutoringSimpleResponseDtoList(tutoringList);
        Page<TutoringSimpleResponseDto> page = new PageImpl<>(tutoringSimpleList, pageable, 10L);
        PageResponseDto response = PageResponseDto.of(tutoringSimpleList, page);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/details/{tutoringId}")
    public ResponseEntity postTutoringMatch(
            @PathVariable("tutoringId") Long tutoringId
    ) {
        tutoringService.setTutoringStatusProgress(tutoringId);

        Tutoring tutoring = tutoringService.findTutoring(tutoringId);

        return new ResponseEntity(
                ResponseDto.of(tutoringMapper.tutoringToTutoringSimpleResponseDto(tutoring)),
                HttpStatus.OK);
    }


    @GetMapping("/details/{tutoringId}")
    public ResponseEntity getTutoring(
            @PathVariable("tutoringId") Long tutoringId
    ) {
        Tutoring tutoring = tutoringService.findTutoring(tutoringId);

        TutoringResponseDto tutoringResponseDto = tutoringMapper.tutoringToTutoringResponseDto(tutoring);


        // Todo: 부분 페이지네이션
//        stubData.createTutoringDto()
//        TutoringDto tutoringDto = stubData.createTutoringDto();
//        TutoringResponseDto response = TutoringResponseDto.of(tutoringDto);
        return new ResponseEntity(
                ResponseDto.of(tutoringResponseDto),
                HttpStatus.OK);
    }

    @PatchMapping("/details/{tutoringId}")
    public ResponseEntity patchTutoring(
            @PathVariable("tutoringId") Long tutoringId,
            @RequestBody TutoringPatchDto tutoringPatchDto
            ) {
        Tutoring tutoring = tutoringMapper.tutoringPatchDtoToTutoring(tutoringPatchDto);

        TutoringResponseDto tutoringResponseDto = tutoringMapper.tutoringToTutoringResponseDto(tutoring);

//        TutoringDto tutoringDto = stubData.createTutoringDto();
//        TutoringResponseDto response = TutoringResponseDto.of(tutoringDto);
        return new ResponseEntity(
                ResponseDto.of(tutoringResponseDto),
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
