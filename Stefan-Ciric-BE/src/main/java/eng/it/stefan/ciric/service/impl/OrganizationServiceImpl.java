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
import eng.it.stefan.ciric.dao.OrganizationTypeDao;
import eng.it.stefan.ciric.dao.PatientDao;
import eng.it.stefan.ciric.dao.PractitionerDao;
import eng.it.stefan.ciric.dto.OrganizationDto;
import eng.it.stefan.ciric.entity.ExaminationEntity;
import eng.it.stefan.ciric.entity.OrganizationEntity;
import eng.it.stefan.ciric.entity.OrganizationTypeEntity;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;
import eng.it.stefan.ciric.mapper.OrganizationMapper;
import eng.it.stefan.ciric.service.OrganizationService;


@Service
public class OrganizationServiceImpl implements OrganizationService {

	private final OrganizationDao organizationDao;
	private final OrganizationTypeDao organizationTypeDao; 
	private final ExaminationDao examinationDao;
	private final PractitionerDao practitionerDao;
	private final PatientDao patientDao; 

	private final OrganizationMapper organizationMapper = Mappers.getMapper(OrganizationMapper.class);

	
	public OrganizationServiceImpl(OrganizationDao organizationDao, OrganizationTypeDao organizationTypeDao,
			ExaminationDao examinationDao, PractitionerDao practitionerDao, PatientDao patientDao) {
		super();
		this.organizationDao = organizationDao;
		this.organizationTypeDao = organizationTypeDao;
		this.examinationDao = examinationDao;
		this.practitionerDao = practitionerDao;
		this.patientDao = patientDao;
	}

	@Override
	public List<OrganizationDto> findAll() {
		List<OrganizationEntity> entities = organizationDao.findAll();
		return entities.stream().map(entity -> {
			return organizationMapper.toDto(entity);
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<OrganizationDto> findById(Long id) {
		Optional<OrganizationEntity> entity = organizationDao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(organizationMapper.toDto(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public OrganizationDto save(OrganizationDto organizationDto) throws EntityExistsException {
		Optional<OrganizationEntity> entityName = organizationDao.findByName(organizationDto.getName());
		
		Optional<OrganizationTypeEntity> type = organizationTypeDao.findByName(organizationDto.getType());
		Optional<OrganizationEntity> entityIdentifer = organizationDao.findByIdentifier(organizationDto.getIdentifier());
		
		
		if(entityName.isPresent()) {
			throw new EntityExistsException(entityName.get().getName(),"Organization name already exist!");
		}
		if(entityIdentifer.isPresent()) {
			throw new EntityExistsException(entityIdentifer.get().getIdentifier(), "Identifer is already taken!");
		}
		
		
		OrganizationEntity entityToSave = organizationMapper.toEntity(organizationDto);	
			
			if(type.isPresent()){
				entityToSave.setType(type.get());
			}
			OrganizationEntity organization = organizationDao.save(entityToSave);
			return organizationMapper.toDto(organization);
	}

	@Override
	public OrganizationDto update(OrganizationDto organizationDto) throws RuntimeException{
		Optional<OrganizationEntity> entity = organizationDao.findById(organizationDto.getId());
		Optional<OrganizationEntity> entityName = organizationDao.findByName(organizationDto.getName());
		Optional<OrganizationTypeEntity> type = organizationTypeDao.findByName(organizationDto.getType());
		Optional<OrganizationEntity> entityIdentifer = organizationDao.findByIdentifier(organizationDto.getIdentifier());
		
		if (!entity.isPresent()) {
			throw new RuntimeException("Organization does not exist:");
		}
		if(entityIdentifer.isPresent()) {
			if(entityIdentifer.get().isActive() && !entityIdentifer.get().getId().equals(organizationDto.getId()))
			throw new RuntimeException("Identifer is already taken!");
		}
		if(entityName.isPresent()) {
			if(entityIdentifer.get().isActive() && !entityName.get().getName().equals(organizationDto.getName()))
			throw new RuntimeException("Organization name already exist!");
		}
		
		
		
		OrganizationEntity entityToSave = organizationMapper.toEntity(organizationDto);
		
		if(type.isPresent()){
			entityToSave.setType(type.get());
		}
	
		return organizationMapper.toDto(organizationDao.save(entityToSave));
	}		

	@Override
	public void deleteById(Long id) throws InvalidEntityException, ReferenceConstraintException {
		Optional<OrganizationEntity> entity = organizationDao.findById(id);
		List<ExaminationEntity> examinations = examinationDao.checkIfOrganizationExist(id);
		
		if (!entity.isPresent()) {
			throw new InvalidEntityException("Invalid organization entity!");
		}
		
		if(!examinations.isEmpty()) {
			throw new ReferenceConstraintException("Delete is not possible, there are running examinations inside organization");
		}
		
		practitionerDao.setOrganizationNull(id);
		patientDao.setOrganizationNull(id);

	
		organizationDao.deleteById(id);
	}

	@Override
	public Page<OrganizationDto> findAll(Integer pageNo, Integer pageSize, String sortOrder, String sortBy,String organizationType,String name) {
			Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
			
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));

		Page<OrganizationDto> entites = organizationDao.findByTypeNameContainingAndNameContaining(organizationType,name,pageable).map(organizationMapper::toDto);
		return entites;
		}

	

	
}
