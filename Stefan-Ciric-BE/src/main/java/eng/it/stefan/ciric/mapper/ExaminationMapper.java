package eng.it.stefan.ciric.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import eng.it.stefan.ciric.dto.ExaminationDto;
import eng.it.stefan.ciric.entity.ExaminationEntity;
import eng.it.stefan.ciric.entity.PractitionerEntity;

@Mapper
public interface ExaminationMapper {

	@Mapping(source = "practitionerIds", target = "practitionerEntity", qualifiedByName = "idToPractitioner")
	@Mapping(source = "serviceType", target = "serviceType.name")
	@Mapping(source = "organizationId", target = "organizationEntity.id")
	@Mapping(source = "patientId", target = "patientEntity.id")
	ExaminationEntity toEntity(ExaminationDto examinationDto);

	
	@Mapping(source = "examinationEntity.practitionerEntity", target = "practitionerIds", qualifiedByName = "practitionerToId")
	@Mapping(source = "examinationEntity.serviceType.name", target = "serviceType")
	@Mapping(source = "examinationEntity.organizationEntity.id", target = "organizationId")
	@Mapping(source = "examinationEntity.patientEntity.id", target = "patientId")
	ExaminationDto toDto(ExaminationEntity examinationEntity);
	
	
	//Mapper cannot convert list of entities to list of dtos.
	//Qualifying methods were required. Used just as a mock-up. manually converted in service implementation
	
	@Named("practitionerToId") 
	public static Long practitionerToId(PractitionerEntity practitionerEntity) { 
	    return practitionerEntity.getId(); 
	}
	@Named("idToPractitioner") 
	public static PractitionerEntity idToPractitioner(Long id) { 
	    return new PractitionerEntity();
	}
	
	
}
