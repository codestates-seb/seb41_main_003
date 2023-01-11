package com.mainproject.server.tutoring.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.repository.TutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class TutoringService {
    private final TutoringRepository tutoringRepository;

    // Todo: ProfileService find Id -> Profile 찾아오기
    public Tutoring createTutoring(Tutoring tutoring, Long tutorId, Long tuteeId, Long profileId) {
        tutoring.addTutor(new Profile());
        tutoring.addTutee(new Profile());

        tutoring.setTutoringStatus(findTutoringStatus(profileId));

        return tutoringRepository.save(tutoring);
    }

    public Page<Tutoring> getAllTutoring(Long profileId, Pageable pageable) {
        Page<Tutoring> tutorings = tutoringRepository.findAllByTutorProfileIdAndTuteeProfileId(profileId, profileId, pageable);
        return tutorings;
    }

    public Tutoring findTutoring(Long tutoringId) {
        return findVerifiedTutoring(tutoringId);
    }

    public Tutoring updateTutoring(Tutoring tutoring) {
        Tutoring findTutoring = findVerifiedTutoring(tutoring.getTutoringId());

        Optional.ofNullable(tutoring.getTutoringTitle())
                .ifPresent(findTutoring::setTutoringTitle);
        Optional.ofNullable(tutoring.getTutoringStatus())
                .ifPresent(findTutoring::setTutoringStatus);

        return tutoringRepository.save(findTutoring);
    }

    public void deleteTutoring(Long tutoringId) {
        Tutoring findTutoring = findVerifiedTutoring(tutoringId);

        tutoringRepository.delete(findTutoring);
    }

    // 리뷰 작성이 되면 과외 종료
    public void setTutoringStatusFinish(Long tutoringId) {
        Tutoring tutoring = findVerifiedTutoring(tutoringId);
        tutoring.setTutoringStatus(TutoringStatus.FINISH);

        tutoringRepository.save(tutoring);
    }

    public void setTutoringStatusUncheck(Long tutoringId) {
        Tutoring tutoring = findVerifiedTutoring(tutoringId);
        tutoring.setTutoringStatus(TutoringStatus.UNCHECK);

        tutoringRepository.save(tutoring);
    }

    // 과외가 성사되면 (매칭 요청이 수락되면) 진행중 상태로 변경
    // Todo: profileId가 receiverId인 사람이 요청했을 때만 되도록 설정
    public Tutoring setTutoringStatusProgress(Long tutoringId) {
        Tutoring tutoring = findVerifiedTutoring(tutoringId);
        tutoring.setTutoringStatus(TutoringStatus.PROGRESS);
        Tutoring progressTutoring = tutoringRepository.save(tutoring);

        return progressTutoring;
    }

    private Tutoring findVerifiedTutoring(Long tutoringId) {
        Optional<Tutoring> optionalTutoring = tutoringRepository.findById(tutoringId);
        Tutoring tutoring = optionalTutoring.orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));

        return tutoring;
    }

    // Todo: profileId 통해 profile 찾고, profile에서 user 찾고, tutoringStatus 정해주기 (User가 Tutor인 경우에 TUTEE_WAITING 등..)
    private TutoringStatus findTutoringStatus(Long profileId) {

        return TutoringStatus.UNCHECK;
    }
}
