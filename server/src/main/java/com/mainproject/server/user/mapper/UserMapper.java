package com.mainproject.server.user.mapper;


import com.mainproject.server.user.dto.UserPatchDto;
import com.mainproject.server.user.dto.UserPostDto;
import com.mainproject.server.user.dto.UserResponseDto;
import com.mainproject.server.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userPostDtoToEntity(UserPostDto userPostDto);

    User userPatchDtoToEntity(UserPatchDto userPatchDto);

    UserResponseDto entityToUserResponseDto(User user);
}
