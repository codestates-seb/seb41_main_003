package com.mainproject.server.profile.mapper;


import com.mainproject.server.image.entity.ProfileImage;
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
        ProfileImage profileImage = profile.getProfileImage();
        String url = null;
        if (profileImage != null) {
            url = profileImage.getUrl();
        }
        return ProfileSimpleResponseDto.builder()
                .profileId(profile.getProfileId())
                .name(profile.getName())
                .school(profile.getSchool())
                .url(url)
                .build();
    }

    default Profile dtoToEntity(ProfileDto profileDto) {
        if ( profileDto == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setName( profileDto.getName() );
        profile.setBio( profileDto.getBio() );
        profile.setWantDate( profileDto.getWantDate() );
        profile.setPay( profileDto.getPay() );
        profile.setWay( profileDto.getWay() );
        profile.setGender( profileDto.getGender() );
        profile.setSchool( profileDto.getSchool() );
        profile.setCharacter( profileDto.getCharacter() );
        profile.setPreTutoring( profileDto.getPreTutoring() );
        profile.setDifference( profileDto.getDifference() );

        return profile;
    }

    List<ProfileSimpleResponseDto> entityListToSimpleResponseDtoList(List<Profile> profiles);

}
