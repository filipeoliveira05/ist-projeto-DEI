package pt.ulisboa.tecnico.rnl.dei.dms.person.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

// Data Transfer Object, to communicate with frontend
public record PersonDto(long id, String name, String istId, String type, String email, boolean status, String phoneNumber) {
	public PersonDto(Person person) {
		this(person.getId(), person.getName(), person.getIstId(),
				person.getType().toString(), person.getEmail(), person.isStatus(), person.getPhoneNumber());
	}
}
