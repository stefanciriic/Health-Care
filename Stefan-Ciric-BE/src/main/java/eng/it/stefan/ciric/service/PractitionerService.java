package eng.it.stefan.ciric.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import eng.it.stefan.ciric.dto.PractitionerDto;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;

public interface PractitionerService {

	List<PractitionerDto> findAll();
	
	Optional<PractitionerDto> findById(Long id);

	PractitionerDto update(PractitionerDto practitionerDto) throws RuntimeException, EntityExistsException;
	
	PractitionerDto save(PractitionerDto practitionerDto) throws EntityExistsException,RuntimeException;
	
	void deleteById(Long id)throws InvalidEntityException, ReferenceConstraintException;
	
	Page<PractitionerDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder,String gender,String qualification);

	
}
