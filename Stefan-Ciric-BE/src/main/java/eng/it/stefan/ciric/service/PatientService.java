package eng.it.stefan.ciric.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import eng.it.stefan.ciric.dto.PatientDto;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;

public interface PatientService {

	List<PatientDto> findAll();
	
	Optional<PatientDto> findById(Long id);

	PatientDto update(PatientDto patientDto) throws RuntimeException, EntityExistsException;
	
	PatientDto save(PatientDto patientDto) throws EntityExistsException;
	
	void deleteById(Long id)throws InvalidEntityException, ReferenceConstraintException;
	
	Page<PatientDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder,String gender,String maritalStatus,String filterWord);

	
	
}
