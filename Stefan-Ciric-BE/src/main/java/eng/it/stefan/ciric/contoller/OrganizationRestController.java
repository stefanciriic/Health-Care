package eng.it.stefan.ciric.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eng.it.stefan.ciric.dto.OrganizationDto;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;
import eng.it.stefan.ciric.service.OrganizationService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("organization")
public class OrganizationRestController {
	
	private final OrganizationService organizationService;

	public OrganizationRestController(OrganizationService organizationService) {
		super();
		this.organizationService = organizationService;
	}
	
	@GetMapping
	public List<OrganizationDto> findAll(){
		return organizationService.findAll();
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<OrganizationDto> organization = organizationService.findById(id);
		if (organization.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(organization.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization ID is not VALID!");
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@Valid @RequestBody OrganizationDto organizationDto)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(organizationService.save(organizationDto));
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody OrganizationDto organizationDto)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(organizationService.update(organizationDto));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		try {
			organizationService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Organization deleted");
		} catch (InvalidEntityException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}catch (ReferenceConstraintException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
		
	}
	
	@GetMapping("filter")
	public ResponseEntity<Page<OrganizationDto>> findAll(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, 
			@RequestParam(defaultValue = "ASC") String sortOrder,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue ="") String orgType,
			@RequestParam(defaultValue="") String name)
	{
		return new ResponseEntity<Page<OrganizationDto>>(organizationService.findAll(pageNo, pageSize, sortOrder, sortBy,orgType,name), new HttpHeaders(),
				HttpStatus.OK);
	}
	
	
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	
}
