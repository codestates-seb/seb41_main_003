package com.mainproject.server.message.repository.custom;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.message.dto.MessageRoomQueryDto;
import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.entity.QMessageRoom;
import com.mainproject.server.profile.entity.QProfile;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class CustomMessageRoomRepositoryImpl
        extends QuerydslRepositorySupport
        implements CustomMessageRoomRepository {

    public CustomMessageRoomRepositoryImpl() {
        super(MessageRoom.class);
    }

    @Override
    public Page<MessageRoomQueryDto> findQueryMessageRoom(
            Long profileId,
            Pageable pageable
    ) {
        QMessageRoom messageRoom = QMessageRoom.messageRoom;
        ProfileStatus profileStatus = getProfileStatus(profileId);
        JPQLQuery<MessageRoomQueryDto> query;
        if (profileStatus.equals(ProfileStatus.TUTOR)) {
            query = from(messageRoom)
                    .select(
                            Projections.constructor(
                                    MessageRoomQueryDto.class,
                                    messageRoom.messageRoomId,
                                    messageRoom.messageStatus,
                                    messageRoom.tutoringId,
                                    messageRoom.tutor,
                                    messageRoom.tutee,
                                    messageRoom.tutor.profileImage.url,
                                    messageRoom.tutee.profileImage.url,
                                    messageRoom.lastMessage,
                                    messageRoom.lastSenderId,
                                    messageRoom.tutor.profileStatus,
                                    messageRoom.createAt,
                                    messageRoom.updateAt
                            ));
        } else {
            query = from(messageRoom)
                    .select(
                            Projections.constructor(
                                    MessageRoomQueryDto.class,
                                    messageRoom.messageRoomId,
                                    messageRoom.messageStatus,
                                    messageRoom.tutoringId,
                                    messageRoom.tutor,
                                    messageRoom.tutee,
                                    messageRoom.tutor.profileImage.url,
                                    messageRoom.tutee.profileImage.url,
                                    messageRoom.lastMessage,
                                    messageRoom.lastSenderId,
                                    messageRoom.tutee.profileStatus,
                                    messageRoom.createAt,
                                    messageRoom.updateAt
                            ));
        }

        if (profileId != null) {
            query.where(messageRoom.tutor.profileId.eq(profileId)
                    .or(messageRoom.tutee.profileId.eq(profileId)));
        }

        List<MessageRoomQueryDto> mrList = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.DATA_ACCESS_ERROR))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(mrList, pageable, query.fetchCount());
    }

    private ProfileStatus getProfileStatus(Long profileId) {
        QProfile profile = QProfile.profile;
        return Optional.ofNullable(
                        from(profile)
                                .select(profile.profileStatus)
                                .where(profile.profileId.eq(profileId))
                                .fetchFirst())
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.PROFILE_NOT_FOUND));
    }
}
