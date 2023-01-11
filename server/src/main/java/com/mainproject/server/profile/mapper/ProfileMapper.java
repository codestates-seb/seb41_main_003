package com.mainproject.server.profile.mapper;


import com.mainproject.server.profile.dto.ProfileDto;
import com.mainproject.server.profile.dto.ProfileSimpleResponseDto;
import com.mainproject.server.profile.entity.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    default ProfileSimpleResponseDto entityToSimpleResponseDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }
        return ProfileSimpleResponseDto.builder()
                .profileId(profile.getProfileId())
                .name(profile.getName())
                .school(profile.getSchool())
                .url(profile.getProfileImage().getUrl())
                .build();
    }

    Profile dtoToEntity(ProfileDto profileDto);

    List<ProfileSimpleResponseDto> entityListToSimpleResponseDtoList(List<Profile> profiles);

}
