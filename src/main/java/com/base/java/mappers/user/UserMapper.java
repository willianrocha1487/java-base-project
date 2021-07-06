package com.base.java.mappers.user;

import com.base.java.mappers.BaseMapper;
import com.base.java.models.user.UserDTO;
import com.base.java.models.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity, UserDTO> {

	@Override
	@Mapping(target = "password", ignore = true)
	UserDTO toDto(UserEntity userEntity);
}
