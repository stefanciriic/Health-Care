package eng.it.stefan.ciric.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import eng.it.stefan.ciric.dto.OrganizationDto;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;

public interface OrganizationService {

	List<OrganizationDto> findAll();
	
	Optional<OrganizationDto> findById(Long id);

	OrganizationDto update(OrganizationDto organizationDto) throws RuntimeException;
	
	OrganizationDto save(OrganizationDto organizationDto) throws EntityExistsException;
	
	void deleteById(Long id)throws InvalidEntityException, ReferenceConstraintException;
	
	public Page<OrganizationDto> findAll(Integer pageNo, Integer pageSize, String sortOrder, String sortBy,String organizationType,String name);
		
	
	
}
