package eng.it.stefan.ciric.mapper;

import org.mapstruct.Mapper;

import eng.it.stefan.ciric.dto.UserDto;
import eng.it.stefan.ciric.entity.UserEntity;

@Mapper
public interface UserMapper {
	UserEntity toEntity(UserDto userDto);

	UserDto toDto(UserEntity userEntity);
}
