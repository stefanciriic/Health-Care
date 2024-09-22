package eng.it.stefan.ciric.mapper;

import org.mapstruct.Mapper;

import eng.it.stefan.ciric.dto.ServiceTypeDto;
import eng.it.stefan.ciric.entity.ServiceTypeEntity;

@Mapper
public interface ServiceTypeMapper {

	ServiceTypeEntity toEntity(ServiceTypeDto serviceTypeDto);
	
	ServiceTypeDto toDto(ServiceTypeEntity serviceTypeEntity);
}
