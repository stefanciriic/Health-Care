package eng.it.stefan.ciric.service;

import java.util.List;
import java.util.Optional;
import eng.it.stefan.ciric.dto.ServiceTypeDto;

public interface ServiceTypeService {
	
	List<ServiceTypeDto> findAll();
	
	Optional<ServiceTypeDto> findById(Long id);
}
