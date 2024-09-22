package eng.it.stefan.ciric.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import eng.it.stefan.ciric.dao.ExaminationDao;
import eng.it.stefan.ciric.dao.OrganizationDao;
import eng.it.stefan.ciric.dao.PatientDao;
import eng.it.stefan.ciric.dao.PractitionerDao;
import eng.it.stefan.ciric.dao.ServiceTypeDao;
import eng.it.stefan.ciric.dto.ExaminationDto;
import eng.it.stefan.ciric.entity.ExaminationEntity;
import eng.it.stefan.ciric.entity.OrganizationEntity;
import eng.it.stefan.ciric.entity.PatientEntity;
import eng.it.stefan.ciric.entity.PractitionerEntity;
import eng.it.stefan.ciric.entity.ServiceTypeEntity;
import eng.it.stefan.ciric.entity.util.Priority;
import eng.it.stefan.ciric.entity.util.Status;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.mapper.ExaminationMapper;
import eng.it.stefan.ciric.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {

	
	private final ExaminationDao examinationDao;
	private final OrganizationDao organizationDao;
	private final PractitionerDao practitionerDao;
	private final PatientDao patientDao;
	private final ServiceTypeDao serviceTypeDao;
	private final ExaminationMapper examinationMapper=Mappers.getMapper(ExaminationMapper.class);
	
	public ExaminationServiceImpl(ExaminationDao examinationDao, OrganizationDao organizationDao,
			PractitionerDao practitionerDao, PatientDao patientDao,ServiceTypeDao serviceTypeDao) {
		super();
		this.examinationDao = examinationDao;
		this.organizationDao = organizationDao;
		this.practitionerDao = practitionerDao;
		this.patientDao = patientDao;
		this.serviceTypeDao = serviceTypeDao;
	}

	@Override
	public List<ExaminationDto> findAll() {
		List<ExaminationEntity> entities = examinationDao.findAll();
		
		return entities.stream().map(entity -> {
			return examinationMapper.toDto(entity);
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<ExaminationDto> findById(Long id) {
		Optional<ExaminationEntity> entity = examinationDao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(examinationMapper.toDto(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public ExaminationDto update(ExaminationDto examinationDto) throws RuntimeException, EntityExistsException {
		Optional<ExaminationEntity> entity = examinationDao.findById(examinationDto.getId());
		Optional<ExaminationEntity> entityIdentifer = examinationDao.findByIdentifier(examinationDto.getIdentifier());
		
		if (!entity.isPresent()) {
			throw new EntityExistsException(entity.get(), "Examination does not exists!");
		}
		if(entityIdentifer.isPresent()) {
			if(!entityIdentifer.get().getId().equals(examinationDto.getId()))
				throw new EntityExistsException(entityIdentifer.get().getIdentifier(), "Identifer is already taken!");
		}
		
		ExaminationEntity entityToSave = new ExaminationEntity();
		
		
		Optional<ServiceTypeEntity> type = serviceTypeDao.findByName(examinationDto.getServiceType());
		
		if (type.isPresent()) {
			entityToSave.setServiceType(type.get());
		}
		else {
			throw new EntityExistsException(type.get().getName(), "No service type with this name!");
		}

		
		List<Long> practitionerIds = examinationDto.getPractitionerIds();

		List<PractitionerEntity> practitioners = new ArrayList<PractitionerEntity>();

		for (Long practitionerId : practitionerIds) {
			Optional<PractitionerEntity> currentPract = practitionerDao.findById(practitionerId);
			
			if (currentPract.isPresent()) {
				practitioners.add(currentPract.get());
			}
			else {
				throw new EntityExistsException(currentPract.get().getName(), "Practitioner does not exist!");
			}
		}

		Optional<OrganizationEntity> org = organizationDao.findById(examinationDto.getOrganizationId());
		if(org.isPresent()) {
			entityToSave.setOrganizationEntity(org.get());
		}else {
			throw new RuntimeException("Organization does not exists!");
		}
		
		
		Optional<PatientEntity> patient = patientDao.findById(examinationDto.getPatientId());
		if(patient.isPresent()) {
			entityToSave.setPatientEntity(patient.get());
		}else {
			throw new RuntimeException("Patient does not exists!");
		}
		
		//Mapstruct's generic mapper was unable to properly convert list of practitioner entities into practitioner dtos Entitiy fields added manually, instead
		entityToSave.setId(examinationDto.getId());
		entityToSave.setIdentifier(examinationDto.getIdentifier());
		entityToSave.setStatus(Status.valueOf(examinationDto.getStatus()));
		entityToSave.setPriority(Priority.valueOf(examinationDto.getPriority()));
		entityToSave.setStartDate(examinationDto.getStartDate());
		entityToSave.setEndDate(examinationDto.getEndDate());
		entityToSave.setDiagnosis(examinationDto.getDiagnosis());
		entityToSave.setPractitionerEntity(practitioners);
		
		return examinationMapper.toDto(examinationDao.save(entityToSave));
	}

	@Override
	public ExaminationDto save(ExaminationDto examinationDto) throws EntityExistsException {
		Optional<ExaminationEntity> entity = examinationDao.findById(examinationDto.getId());
		Optional<ExaminationEntity> entityIdentifer = examinationDao.findByIdentifier(examinationDto.getIdentifier());
		
		
		if (entity.isPresent()) {
			throw new EntityExistsException(entity.get(), "Examination already exists!");
		}
		if(entityIdentifer.isPresent()) {
			throw new EntityExistsException(entityIdentifer.get().getIdentifier(), "Identifer is already taken!");
		}
		
		ExaminationEntity entityToSave = new ExaminationEntity();
		
		
		Optional<ServiceTypeEntity> type = serviceTypeDao.findByName(examinationDto.getServiceType());
		
		if (type.isPresent()) {
			entityToSave.setServiceType(type.get());
		}
		else {
			throw new EntityExistsException(type.get().getName(), " No service type with this name!");
		}

		List<Long> practitionerIds = examinationDto.getPractitionerIds();
		

		List<PractitionerEntity> practitioners = new ArrayList<PractitionerEntity>();

		for (Long practitionerId : practitionerIds) {
			Optional<PractitionerEntity> currentPract = practitionerDao.findById(practitionerId);
			if (currentPract.isPresent()) {
				practitioners.add(currentPract.get());
			}
			else {
				throw new EntityExistsException(currentPract.get().getName(), "Practitioner does not exist!");
			}
		}

		Optional<OrganizationEntity> org = organizationDao.findById(examinationDto.getOrganizationId());
		if(org.isPresent()) {
			entityToSave.setOrganizationEntity(org.get());
		}else {
			throw new RuntimeException("Organization does not exists!");
		}
		
		
		Optional<PatientEntity> patient = patientDao.findById(examinationDto.getPatientId());
		if(patient.isPresent()) {
			entityToSave.setPatientEntity(patient.get());
		}else {
			throw new RuntimeException("Patient does not exists!");
		}
		
		
		entityToSave.setId(examinationDto.getId());
		entityToSave.setIdentifier(examinationDto.getIdentifier());
		entityToSave.setStatus(Status.valueOf(examinationDto.getStatus()));
		entityToSave.setPriority(Priority.valueOf(examinationDto.getPriority()));
		entityToSave.setStartDate(examinationDto.getStartDate());
		entityToSave.setEndDate(examinationDto.getEndDate());
		entityToSave.setDiagnosis(examinationDto.getDiagnosis());
		
		
		entityToSave.setPractitionerEntity(practitioners);
		
		return examinationMapper.toDto(examinationDao.save(entityToSave));
	}

	@Override
	public void deleteById(Long id) throws InvalidEntityException {
		Optional<ExaminationEntity> entity = examinationDao.findById(id);
		if (!entity.isPresent()) {
			throw new InvalidEntityException("Invalid examination entity!");
		}

		examinationDao.deleteById(id);
	}

	@Override
	public Page<ExaminationDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder,String status,String priority) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));

		if(status.isBlank()) {
			status=null;
		}else {
			status=status.toUpperCase().replace(" ","_");
		}
		if(priority.isBlank()) {
			priority=null;
		}else {
			priority=priority.toUpperCase().replace(" ","_");
		}

		Page<ExaminationDto> entites = examinationDao.findByStatusAndPriorityAndWord(status,priority,pageable).map(examinationMapper::toDto);
		
		
		return entites;
	}
}
