package eng.it.stefan.ciric.contoller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eng.it.stefan.ciric.dto.PractitionerDto;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;
import eng.it.stefan.ciric.service.PractitionerService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("practitioner")
public class PractitionerRestContoller {

	private final PractitionerService practitionerService;

	public PractitionerRestContoller(PractitionerService practitionerService) {
		super();
		this.practitionerService = practitionerService;
	}

	@GetMapping
	public List<PractitionerDto> findAll(){
		return practitionerService.findAll();
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<PractitionerDto> organization = practitionerService.findById(id);
		if (organization.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(organization.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization ID is not VALID!");
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@Valid @RequestBody PractitionerDto practitionerDto){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(practitionerService.save(practitionerDto));
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PractitionerDto practitionerDto){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(practitionerService.update(practitionerDto));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		try {
			practitionerService.deleteById(id);
			return ResponseEntity.ok("Practitioner is deleted");
		} catch (InvalidEntityException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (ReferenceConstraintException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("filter")
	public ResponseEntity<Page<PractitionerDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "ASC") String sortOrder,@RequestParam(defaultValue = "") String gender,
			@RequestParam(defaultValue="") String qualification) {
		return new ResponseEntity<Page<PractitionerDto>>(practitionerService.findAll(pageNo, pageSize, sortBy, sortOrder,gender,qualification), new HttpHeaders(),
				HttpStatus.OK);
	}
	
	
	
}
