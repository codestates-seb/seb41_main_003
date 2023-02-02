package com.mainproject.server.tutoring.repository.custom;

import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.tutoring.dto.TutoringQueryDto;
import com.mainproject.server.tutoring.dto.TutoringSimpleResponseDto;
import com.mainproject.server.tutoring.entity.Tutoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomTutoringRepository {

    Page<TutoringQueryDto> findQueryTutoring(
            Long profileId,
            TutoringStatus status,
            Pageable pageable
    );

    Tutoring findTutoringById(Long tutoringId);

}
