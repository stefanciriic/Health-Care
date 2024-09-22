package eng.it.stefan.ciric.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import eng.it.stefan.ciric.dao.ExaminationDao;
import eng.it.stefan.ciric.dao.OrganizationDao;
import eng.it.stefan.ciric.dao.PatientDao;
import eng.it.stefan.ciric.dao.PractitionerDao;
import eng.it.stefan.ciric.dto.PatientDto;
import eng.it.stefan.ciric.entity.ExaminationEntity;
import eng.it.stefan.ciric.entity.OrganizationEntity;
import eng.it.stefan.ciric.entity.PatientEntity;
import eng.it.stefan.ciric.entity.PractitionerEntity;
import eng.it.stefan.ciric.entity.util.Qualification;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;
import eng.it.stefan.ciric.mapper.PatientMapper;
import eng.it.stefan.ciric.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService{

	private final PatientDao patientDao;
	private final PractitionerDao practitionerDao;
	private final OrganizationDao organizationDao;
	private final ExaminationDao examinationDao;
	private final PatientMapper patientMapper=Mappers.getMapper(PatientMapper.class);
	
	
	public PatientServiceImpl(PatientDao patientDao, PractitionerDao practitionerDao, OrganizationDao organizationDao,ExaminationDao examinationDao) {
		super();
		this.patientDao = patientDao;
		this.practitionerDao = practitionerDao;
		this.organizationDao = organizationDao;
		this.examinationDao = examinationDao;
	}
	@Override
	public List<PatientDto> findAll() {
		List<PatientEntity> entities = patientDao.findAll();
		return entities.stream().map(entity -> {
			return patientMapper.toDto(entity);
		}).collect(Collectors.toList());
	}
	@Override
	public Optional<PatientDto> findById(Long id) {
		Optional<PatientEntity> entity = patientDao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(patientMapper.toDto(entity.get()));
		}
		return Optional.empty();
	}
	
	@Override
	public void deleteById(Long id) throws InvalidEntityException, ReferenceConstraintException {
		Optional<PatientEntity> entity = patientDao.findById(id);
		if (!entity.isPresent()) {
			throw new InvalidEntityException("Invalid patient entity!");
		}
		
		List<ExaminationEntity> examinations = examinationDao.checkIfPatientExist(id);
		
		if(!examinations.isEmpty()) {
			throw new ReferenceConstraintException("Delete is not possible, patient is under examination!");
		}
		
		patientDao.deleteById(id);
	}

	@Override
	public PatientDto save(PatientDto patientDto) throws EntityExistsException {
		Optional<PatientEntity> entity = patientDao.findById(patientDto.getId());
		Optional<OrganizationEntity> org = organizationDao.findById(patientDto.getOrganizationId());
		Optional<PractitionerEntity> practitioner = practitionerDao.findById(patientDto.getGeneralPractitionerId());
		Optional<PatientEntity> identifer = patientDao.findByIdentifier(patientDto.getIdentifier());
		
		
		if (entity.isPresent()) {
			throw new EntityExistsException(entity.get(), "Patient already exists!");
		} if(identifer.isPresent()) {
			throw new EntityExistsException(identifer.get().getIdentifier(), "Identifer is already taken!");
		}
			PatientEntity entityToSave = patientMapper.toEntity(patientDto);
			
			entityToSave.setOrganization(org.get());
			
			entityToSave.setGeneralPractitioner(practitioner.get());
			
			return patientMapper.toDto(patientDao.save(entityToSave));
		
	}

	
	@Override
	public Page<PatientDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder,String gender,String maritalStatus,String filterWord) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;


		if(gender.isBlank()) {
			gender=null;
		}else {
			gender=gender.toUpperCase();
		}

		if(maritalStatus.isBlank()) {
			maritalStatus=null;
		}else {
			maritalStatus=maritalStatus.toUpperCase().replace(" ","_");
		}
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		
		
		Page<PatientDto> entites = patientDao.findByGenderAndMaritalStatusAndWord(gender,maritalStatus,filterWord,pageable).map(patientMapper::toDto);
		return entites;
	}
	
	
	@Override
	public PatientDto update(PatientDto patientDto) throws EntityExistsException {
		Optional<PatientEntity> entity = patientDao.findById(patientDto.getId());
		Optional<OrganizationEntity> organization = organizationDao.findById(patientDto.getOrganizationId());
		Optional<PractitionerEntity> practitioner = practitionerDao.findById(patientDto.getGeneralPractitionerId());
		Optional<PatientEntity> identifer = patientDao.findByIdentifier(patientDto.getIdentifier());
		
		PatientEntity entityToSave = patientMapper.toEntity(patientDto);
		
		if(!entity.isPresent()) {
			throw new EntityExistsException(entity.get().getIdentifier(), "Organization does not exists!");	
		}
		
		if (practitioner.isPresent()) {
			if(practitioner.get().getQualification().equals(Qualification.DOCTOR_OF_MEDICINE)){
				entityToSave.setGeneralPractitioner(practitioner.get());
			}else {
				throw new RuntimeException("General practitioner must have qualification: Doctor of medicine!");
			}
		}
		
		if(identifer.isPresent()) {
			if(identifer.get().isActive() && !identifer.get().getId().equals(patientDto.getId()))
			throw new EntityExistsException(identifer.get().getIdentifier(), "Identifer is already taken!");
		}
		
		if(organization.isPresent()) {
			entityToSave.setOrganization(organization.get());			
		}else {
			throw new EntityExistsException(organization.get(), "Organization does not exists!");
		}
		
		return patientMapper.toDto(patientDao.save(entityToSave));
	}	

	
	
}
