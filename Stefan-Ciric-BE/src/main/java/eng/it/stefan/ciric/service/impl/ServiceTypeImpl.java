package eng.it.stefan.ciric.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import eng.it.stefan.ciric.dao.ServiceTypeDao;
import eng.it.stefan.ciric.dto.ServiceTypeDto;
import eng.it.stefan.ciric.entity.ServiceTypeEntity;
import eng.it.stefan.ciric.mapper.ServiceTypeMapper;
import eng.it.stefan.ciric.service.ServiceTypeService;

@Service
public class ServiceTypeImpl implements ServiceTypeService{

	
	private final ServiceTypeDao serviceTypeDao;
	private final ServiceTypeMapper serviceTypeMapper=Mappers.getMapper(ServiceTypeMapper.class);
	
	public ServiceTypeImpl(ServiceTypeDao serviceTypeDao) {
		super();
		this.serviceTypeDao = serviceTypeDao;
	}

	@Override
	public List<ServiceTypeDto> findAll() {
		List<ServiceTypeEntity> entities = serviceTypeDao.findAll();
		return entities.stream().map(entity -> {
			return serviceTypeMapper.toDto(entity);
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<ServiceTypeDto> findById(Long id) {
		Optional<ServiceTypeEntity> entity = serviceTypeDao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(serviceTypeMapper.toDto(entity.get()));
		}
		return Optional.empty();
	}

	
}
