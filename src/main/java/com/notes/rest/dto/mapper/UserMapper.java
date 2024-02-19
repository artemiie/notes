package com.notes.rest.dto.mapper;

import com.notes.model.user.User;
import com.notes.rest.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
