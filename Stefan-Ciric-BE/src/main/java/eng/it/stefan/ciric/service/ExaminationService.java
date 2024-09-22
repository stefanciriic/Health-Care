package eng.it.stefan.ciric.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import eng.it.stefan.ciric.dto.ExaminationDto;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;

public interface ExaminationService {


	List<ExaminationDto> findAll();
	
	Optional<ExaminationDto> findById(Long id);

	ExaminationDto update(ExaminationDto examinationDto) throws RuntimeException, EntityExistsException;
	
	ExaminationDto save(ExaminationDto examinationDto) throws EntityExistsException;
	
	void deleteById(Long id)throws InvalidEntityException;
	
	Page<ExaminationDto> findAll(Integer pageNo, Integer pageSize, String sortOrder, String sortBy,String status,String priority);
	
	
}
