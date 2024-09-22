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

import eng.it.stefan.ciric.dto.PatientDto;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.exception.ReferenceConstraintException;
import eng.it.stefan.ciric.service.PatientService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("patient")
public class PatientRestContoller {
	private final PatientService patientService;

	public PatientRestContoller(PatientService patientService) {
		super();
		this.patientService = patientService;
	}

	@GetMapping
	public List<PatientDto> findAll(){
		return patientService.findAll();
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<PatientDto> patient = patientService.findById(id);
		if (patient.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(patient.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient ID is not VALID!");
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@Valid @RequestBody PatientDto patientDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(patientService.save(patientDto));
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PatientDto patientDto){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(patientService.update(patientDto));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		try {
			patientService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Patient deleted");
		} catch (InvalidEntityException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (ReferenceConstraintException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("filter")
	public ResponseEntity<Page<PatientDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "ASC") String sortOrder,@RequestParam(defaultValue="")String gender,
			@RequestParam(defaultValue="")String maritalStatus,@RequestParam(defaultValue="")String filterWord) {
		return new ResponseEntity<Page<PatientDto>>(patientService.findAll(pageNo, pageSize, sortBy, sortOrder,gender,maritalStatus,filterWord), new HttpHeaders(),
				HttpStatus.OK);
	}
}
