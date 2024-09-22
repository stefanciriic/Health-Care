package eng.it.stefan.ciric.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import eng.it.stefan.ciric.dto.PatientDto;
import eng.it.stefan.ciric.entity.PatientEntity;

@Mapper
public interface PatientMapper {

	
	@Mappings({
		@Mapping(source="organizationId",target="organization.id"),
		@Mapping(source="generalPractitionerId",target="generalPractitioner.id")
	})
	PatientEntity toEntity(PatientDto patientDto);
	

	@Mappings({
		@Mapping(source="patientEntity.organization.id",target="organizationId"),
		@Mapping(source="patientEntity.generalPractitioner.id",target="generalPractitionerId")
	})
	PatientDto toDto(PatientEntity patientEntity);
	
}
