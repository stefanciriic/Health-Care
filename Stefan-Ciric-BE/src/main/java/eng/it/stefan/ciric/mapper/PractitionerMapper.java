package eng.it.stefan.ciric.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import eng.it.stefan.ciric.dto.PractitionerDto;
import eng.it.stefan.ciric.entity.PractitionerEntity;

@Mapper
public interface PractitionerMapper {

	@Mapping(source="organizationId",target="organization.id")
	PractitionerEntity toEntity(PractitionerDto practitionerDto);
	
	@Mapping(source="practitionerEntity.organization.id",target="organizationId")
	PractitionerDto toDto(PractitionerEntity practitionerEntity);
}
