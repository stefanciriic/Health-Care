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

import eng.it.stefan.ciric.dto.ExaminationDto;
import eng.it.stefan.ciric.exception.EntityExistsException;
import eng.it.stefan.ciric.exception.InvalidEntityException;
import eng.it.stefan.ciric.service.ExaminationService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("examination")
public class ExaminationRestContoller {

	private final ExaminationService examinationService;

	public ExaminationRestContoller(ExaminationService examinationService) {
		super();
		this.examinationService = examinationService;
	}
	
	@GetMapping
	public List<ExaminationDto> findAll(){
		return examinationService.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<ExaminationDto> examination = examinationService.findById(id);
		if (examination.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(examination.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Examination ID is not VALID!");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id)throws InvalidEntityException{
		try {
			examinationService.deleteById(id);
			return ResponseEntity.ok("Deleted");
		} catch (InvalidEntityException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@Valid @RequestBody ExaminationDto examinationDto) throws EntityExistsException{
		try {
			return ResponseEntity.status(HttpStatus.OK).body(examinationService.save(examinationDto));
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ExaminationDto examinationDto)throws RuntimeException, EntityExistsException {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(examinationService.update(examinationDto));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	
	@GetMapping("filter")
	public ResponseEntity<Page<ExaminationDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "identifier") String sortBy,
			@RequestParam(defaultValue = "ASC") String sortOrder,@RequestParam(defaultValue = "") String status,@RequestParam(defaultValue = "") String priority) {
		return new ResponseEntity<Page<ExaminationDto>>(examinationService.findAll(pageNo, pageSize, sortBy, sortOrder,status,priority), new HttpHeaders(),
				HttpStatus.OK);
	}

}
