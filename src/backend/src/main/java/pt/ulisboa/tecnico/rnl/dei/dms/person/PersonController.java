package pt.ulisboa.tecnico.rnl.dei.dms.person;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.service.PersonService;

@RestController
public class PersonController {
	@Autowired
	private PersonService personService;

	@GetMapping("/people")
	public List<PersonDto> getPeople() {
		return personService.getPeople();
	}

	@GetMapping("/students")
	public List<PersonDto> getStudents() {
		return personService.getPeopleByType("STUDENT");
	}

	@GetMapping("/teachers")
	public List<PersonDto> getTeachers() {
		return personService.getPeopleByType("TEACHER");
	}

	@GetMapping("/statistics")
	public ResponseEntity<Map<String, Long>> getStatistics() {
		return ResponseEntity.ok(personService.getStatistics());
	}

	@PostMapping("/people")
	public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
		PersonDto createdPerson = personService.createPerson(personDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
	}

	@GetMapping("/people/{id}")
	public PersonDto getPerson(@PathVariable long id) {
		return personService.getPerson(id);
	}

	@GetMapping("/people/type/{type}")
	public List<PersonDto> getPeopleByType(@PathVariable String type) {
		return personService.getPeopleByType(type);
	}

	@PutMapping("/people/{id}")
	public PersonDto updatePerson(@PathVariable long id, @RequestBody PersonDto personDto) {
		return personService.updatePerson(id, personDto);
	}

	@DeleteMapping("/people/{id}")
	public void deletePerson(@PathVariable long id) {
		personService.deletePerson(id);
	}

	@PatchMapping("/{id}/thesis-state/{state}")
	public ResponseEntity<Void> updateThesisWorkflowState(@PathVariable long id, @PathVariable String state) {
		personService.updateThesisWorkflowState(id, state);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/defense-state/{state}")
	public ResponseEntity<Void> updateDefenseWorkflowState(@PathVariable long id, @PathVariable String state) {
		personService.updateDefenseWorkflowState(id, state);
		return ResponseEntity.noContent().build();
	}

}