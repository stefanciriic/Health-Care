package eng.it.stefan.ciric.mapper;

import org.mapstruct.Mapper;

import eng.it.stefan.ciric.dto.OrganizationTypeDto;
import eng.it.stefan.ciric.entity.OrganizationTypeEntity;

@Mapper
public interface OrganizationTypeMapper {

	OrganizationTypeEntity toEntity(OrganizationTypeDto organizationTypeDto);
	
	OrganizationTypeDto toDto(OrganizationTypeEntity organizationTypeEntity);
}
