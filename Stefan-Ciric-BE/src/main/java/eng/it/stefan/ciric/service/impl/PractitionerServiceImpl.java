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
import eng.it.stefan.ciric.dao.PractitionerDao;
import eng.it.stefan.ciric.dto.PractitionerDto;
import eng.it.stefan.ciric.entity.ExaminationEntity;
import eng.it.stefan.ciric.entity.OrganizationEntity;
import eng.it.stefan.ciric.entity.PractitionerEntity;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;
import eng.it.stefan.ciric.mapper.PractitionerMapper;
import eng.it.stefan.ciric.service.PractitionerService;

@Service
public class PractitionerServiceImpl implements PractitionerService {
	
	private final PractitionerDao practitionerDao;
	private final ExaminationDao examinationDao;

	private final PractitionerMapper practitionerMapper = Mappers.getMapper(PractitionerMapper.class);
	private final OrganizationDao organizationDao;

	public PractitionerServiceImpl(PractitionerDao practitionerDao, OrganizationDao organizationDao,ExaminationDao examinationDao) {
		super();
		this.practitionerDao = practitionerDao;
		this.organizationDao = organizationDao;
		this.examinationDao=examinationDao;
	}

	@Override
	public List<PractitionerDto> findAll() {
		List<PractitionerEntity> entities = practitionerDao.findAll();
		return entities.stream().map(entity -> {
			return practitionerMapper.toDto(entity);
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<PractitionerDto> findById(Long id) {
		Optional<PractitionerEntity> entity = practitionerDao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(practitionerMapper.toDto(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public PractitionerDto save(PractitionerDto practitionerDto) throws RuntimeException, EntityExistsException {
		Optional<PractitionerEntity> entity = practitionerDao.findById(practitionerDto.getId());
		Optional<PractitionerEntity> identifer=practitionerDao.findByIdentifier(practitionerDto.getIdentifier()); 
		
		if (entity.isPresent()) {
			throw new RuntimeException("Practitioner  already exist!");
		}if(identifer.isPresent()) {
			throw new EntityExistsException(identifer.get().getIdentifier(), "Identifer is already taken!");
		}
		PractitionerEntity entityToSave = practitionerMapper.toEntity(practitionerDto);
		Optional<OrganizationEntity> organization = organizationDao.findById(practitionerDto.getOrganizationId());
		

		if(organization.isPresent()) {
			entityToSave.setOrganization(organization.get());
		}else {
			throw new RuntimeException("Organization does not exists!");
		}
		PractitionerEntity practitioner = practitionerDao.save(entityToSave);
		return practitionerMapper.toDto(practitioner);
	}

	@Override
	public PractitionerDto update(PractitionerDto practitionerDto) throws EntityExistsException,RuntimeException {
		Optional<PractitionerEntity> entity = practitionerDao.findById(practitionerDto.getId());
		Optional<PractitionerEntity> entityIdentifer = practitionerDao.findByIdentifier(practitionerDto.getIdentifier());

			if(entityIdentifer.isPresent()) {
				if(entityIdentifer.get().isActive() && !entityIdentifer.get().getId().equals(practitionerDto.getId()))
				throw new EntityExistsException(entityIdentifer.get().getIdentifier(), "Identifer is already taken!");
			}
			
			if(!entity.isPresent()) {
				throw new EntityExistsException(entityIdentifer.get().getIdentifier(), "Practitioner does not exists!");
			}
			
			Optional<OrganizationEntity> organization = organizationDao.findById(practitionerDto.getOrganizationId());
			PractitionerEntity entityToSave = practitionerMapper.toEntity(practitionerDto);
			
			if(organization.isPresent()) {
				entityToSave.setOrganization(organization.get());
			}else {
				throw new RuntimeException("Organization does not exists!");
			}

			entityToSave.setOrganization(organizationDao.findById(practitionerDto.getOrganizationId()).get());
			PractitionerEntity practitioner = practitionerDao.save(entityToSave);
			return practitionerMapper.toDto(practitioner);
		
	}

	@Override
	public void deleteById(Long id) throws InvalidEntityException, ReferenceConstraintException {
		Optional<PractitionerEntity> entity = practitionerDao.findById(id);
		if (!entity.isPresent()) {
			throw new InvalidEntityException("Invalid organization entity!");
		}
		
		List<ExaminationEntity> examinations = examinationDao.findAll();
		
		for(ExaminationEntity e :examinations) {
			if(e.getPractitionerEntity().contains(entity.get())) {
				throw new ReferenceConstraintException("Delete is not possible, practitioner is on duty!");
			}
		}
				
		practitionerDao.deleteById(id);
	}

	@Override
	public Page<PractitionerDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder,String gender,String qualification) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		

		if(gender.isBlank()) {
			gender=null;
		}else {
			gender=gender.toUpperCase();
		}
		
		if(qualification.isBlank()) {
			qualification=null;
		}else {
			qualification=qualification.toUpperCase().replace(" ","_");
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		
		
		Page<PractitionerDto> entites = practitionerDao.findByGenderAndQualification(gender,qualification,pageable).map(practitionerMapper::toDto);
		return entites;
	}


}
