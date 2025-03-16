package pt.ulisboa.tecnico.rnl.dei.dms.person.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.log.LogEntry.LogType;
import pt.ulisboa.tecnico.rnl.dei.dms.log.LogService;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;

import org.apache.commons.validator.routines.EmailValidator;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber;

@Service
@Transactional
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private LogService logService;

	private Person fetchPersonOrThrow(long id) {
		return personRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PERSON, Long.toString(id)));
	}


	@Transactional
	public List<PersonDto> getPeople() {
		return personRepository.findAll().stream()
				.map(PersonDto::new)
				.toList();
	}


	public boolean isValidEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
	}


	public static boolean isValidPhoneNumber(String phoneNumber, String region) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(phoneNumber, region);
            return phoneUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }


	private void validatePerson(PersonDto personDto) {
		if (personDto.name() == null || personDto.name().isBlank()) {
			throw new DEIException(ErrorMessage.PERSON_NAME_NOT_VALID);
		}

		if (personDto.istId() == null || personDto.istId().isBlank()) {
			throw new DEIException(ErrorMessage.IST_ID_NOT_VALID);
		}
		
		if (personRepository.findByIstId(personDto.istId()).isPresent()) {
			throw new DEIException(ErrorMessage.DUPLICATE_IST_ID, personDto.istId());
		}

		try {
			Person.PersonType.valueOf(personDto.type().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new DEIException(ErrorMessage.PERSON_TYPE_NOT_VALID, personDto.type());
		}

		if (personDto.email() == null || personDto.email().isBlank() || !isValidEmail(personDto.email())) {
			throw new DEIException(ErrorMessage.EMAIL_NOT_VALID);
		}

		if (personRepository.findByEmail(personDto.email()).isPresent()) {
			throw new DEIException(ErrorMessage.DUPLICATE_EMAIL, personDto.email());
		}

		if (personDto.phoneNumber() == null || personDto.phoneNumber().isBlank() || !isValidPhoneNumber(personDto.phoneNumber(), "PT")) {
			throw new DEIException(ErrorMessage.PHONE_NUMBER_NOT_VALID);
		}		

		if (personRepository.findByPhoneNumber(personDto.phoneNumber()).isPresent()) {
			throw new DEIException(ErrorMessage.DUPLICATE_PHONE_NUMBER, personDto.phoneNumber());
		}

	}


	@Transactional
	public PersonDto createPerson(PersonDto personDto) {
		validatePerson(personDto);

		Person person = new Person(personDto);
		PersonDto savedPerson = new PersonDto(personRepository.save(person));
        logService.log(LogType.CREATE_PERSON, "<b>Pessoa criada:</b> " + person.getDetails());

		return savedPerson;
	}


	@Transactional
	public PersonDto getPerson(long id) {
		return new PersonDto(fetchPersonOrThrow(id));
	}


	public List<PersonDto> getPeopleByType(String type) {
		return personRepository.findByType(Person.PersonType.valueOf(type.toUpperCase())).stream()
				.map(PersonDto::new)
				.toList();
	}


	@Transactional
	public PersonDto updatePerson(long id, PersonDto personDto) {
		Person person = fetchPersonOrThrow(id);
	
		String beforeUpdate = person.getDetails();
	
		if (personRepository.findByIstId(personDto.istId())
				.filter(existingPerson -> !existingPerson.getId().equals(id))
				.isPresent()) {
			throw new DEIException(ErrorMessage.DUPLICATE_IST_ID, personDto.istId());
		}
	
		if (personRepository.findByEmail(personDto.email())
				.filter(existingPerson -> !existingPerson.getId().equals(id))
				.isPresent()) {
			throw new DEIException(ErrorMessage.DUPLICATE_EMAIL, personDto.email());
		}
	
		if (personRepository.findByPhoneNumber(personDto.phoneNumber())
				.filter(existingPerson -> !existingPerson.getId().equals(id))
				.isPresent()) {
			throw new DEIException(ErrorMessage.DUPLICATE_PHONE_NUMBER, personDto.phoneNumber());
		}
	
		person.updateFromDto(personDto);
		PersonDto updatedPerson = new PersonDto(personRepository.save(person));
	
		String afterUpdate = person.getDetails();

		logService.log(LogType.EDIT_PERSON, "<b>Antes:</b> " + beforeUpdate + "\n <b>Depois:</b> " + afterUpdate);
	
		return updatedPerson;
	}
	


	@Transactional
	public void deletePerson(long id) {
		Person person = fetchPersonOrThrow(id);
		personRepository.deleteById(id);
		logService.log(LogType.DELETE_PERSON, "<b>Pessoa removida:</b> " + person.getDetails());
	}


	@Transactional
	public void updateThesisWorkflowState(long id, String newState) {
		Person person = fetchPersonOrThrow(id);
		try {
			Person.ThesisWorkflowState oldState = person.getThesisWorkflowState();
			Person.ThesisWorkflowState state = Person.ThesisWorkflowState.valueOf(newState.toUpperCase());
			person.setThesisWorkflowState(state);
			personRepository.save(person);
			String logMessage = "Aluno <b>" + person.getName() + "</b> (" + person.getIstId() + "): Estado do <b>Workflow</b> de <b>Tese</b> alterado de <b>" + oldState + "</b> para <b>" + newState + "</b>";
			logService.log(LogType.UPDATE_THESIS_WORKFLOW, logMessage);
		} catch (IllegalArgumentException e) {
			throw new DEIException(ErrorMessage.INVALID_WORKFLOW_STATE, newState);
		}
	}

	
	@Transactional
	public void updateDefenseWorkflowState(long id, String newState) {
		Person person = fetchPersonOrThrow(id);
		try {
			Person.DefenseWorkflowState oldState = person.getDefenseWorkflowState();
			Person.DefenseWorkflowState state = Person.DefenseWorkflowState.valueOf(newState.toUpperCase());
			person.setDefenseWorkflowState(state);
			personRepository.save(person);
			String logMessage = "Aluno <b>" + person.getName() + "</b> (" + person.getIstId() + "): Estado do <b>Workflow</b> de <b>Defesa</b> alterado de <b>" + oldState + "</b> para <b>" + newState + "</b>";
			logService.log(LogType.UPDATE_DEFENSE_WORKFLOW, logMessage);
		} catch (IllegalArgumentException e) {
			throw new DEIException(ErrorMessage.INVALID_WORKFLOW_STATE, newState);
		}
	}

	public Map<String, Long> getStatistics() {
        Map<String, Long> stats = new HashMap<>();

        stats.put("numStudents", personRepository.countByType(Person.PersonType.STUDENT));
        stats.put("numTeachers", personRepository.countByType(Person.PersonType.TEACHER));
        
        stats.put("thesisNone", personRepository.countByThesisWorkflowState(Person.ThesisWorkflowState.NONE));
		stats.put("thesisProposalSubmitted", personRepository.countByThesisWorkflowState(Person.ThesisWorkflowState.PROPOSTA_JURI_SUBMETIDA));
        stats.put("thesisApproved", personRepository.countByThesisWorkflowState(Person.ThesisWorkflowState.APROVADO_PELO_SC));
        stats.put("thesisPresidentAssigned", personRepository.countByThesisWorkflowState(Person.ThesisWorkflowState.PRESIDENTE_JURI_ATRIBUIDO));
        stats.put("thesisSigned", personRepository.countByThesisWorkflowState(Person.ThesisWorkflowState.DOCUMENTO_ASSINADO));
        stats.put("thesisSubmittedFenix", personRepository.countByThesisWorkflowState(Person.ThesisWorkflowState.SUBMETIDO_AO_FENIX));
        
		stats.put("defenseNone", personRepository.countByDefenseWorkflowState(Person.DefenseWorkflowState.NONE));
        stats.put("defenseScheduled", personRepository.countByDefenseWorkflowState(Person.DefenseWorkflowState.DEFESA_AGENDADA));
        stats.put("defenseReview", personRepository.countByDefenseWorkflowState(Person.DefenseWorkflowState.EM_REVISAO));
        stats.put("defenseSubmittedFenix", personRepository.countByDefenseWorkflowState(Person.DefenseWorkflowState.SUBMETIDO_AO_FENIX));
        
        return stats;
	}

}
