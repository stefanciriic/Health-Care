package eng.it.stefan.ciric.contoller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eng.it.stefan.ciric.dto.ServiceTypeDto;
import eng.it.stefan.ciric.service.ServiceTypeService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("service_type")
public class ServiceTypeRestContoller {

	private final ServiceTypeService serviceTypeService;

	public ServiceTypeRestContoller(ServiceTypeService serviceTypeService) {
		super();
		this.serviceTypeService = serviceTypeService;
	}
	
	@GetMapping
	public List<ServiceTypeDto> findAll(){
		return serviceTypeService.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<ServiceTypeDto> examination = serviceTypeService.findById(id);
		if (examination.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(examination.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ServiceType ID is not VALID!");
	}
	
}
