package com.mainproject.server.image.mapper;

import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.image.entity.ProfileImage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageResponseDto entityToImageResponseDto(ProfileImage image);

    List<ImageResponseDto> entityListToImageResponseDtoList(List<ProfileImage> imageList);
}
