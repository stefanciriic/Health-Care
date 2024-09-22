package eng.it.stefan.ciric.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import eng.it.stefan.ciric.dto.OrganizationDto;
import eng.it.stefan.ciric.entity.OrganizationEntity;

@Mapper
public interface OrganizationMapper {

	@Mapping(source="type",target="type.name")
	OrganizationEntity toEntity(OrganizationDto organizationDto);
	
	@Mapping(source="organizationEntity.type.name",target="type")
	OrganizationDto toDto(OrganizationEntity organizationEntity);
	
}
