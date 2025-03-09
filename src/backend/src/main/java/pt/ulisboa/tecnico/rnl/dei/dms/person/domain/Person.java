package pt.ulisboa.tecnico.rnl.dei.dms.person.domain;


import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.*;

import lombok.Data;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;

@Data
@Entity
@Table(name = "people")
public class Person {

	public enum PersonType {
		COORDINATOR, STAFF, STUDENT, TEACHER, SC
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "ist_id", nullable = false, unique = true)
	private String istId;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
    private PersonType type;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "status", nullable = false)
	private boolean status = true; // true = active, false = inactive

	@Column(name = "phone_number", nullable = false, unique = true) 
	private String phoneNumber;
	

	protected Person() {
	}

	public Person(String name, String istId, PersonType type, String email, boolean status, String phoneNumber) {
		this.name = name;
		this.istId = istId;
		this.type = type;
		this.email = email;
		this.status = status;
		this.phoneNumber = phoneNumber;
	}

	public Person(PersonDto personDto) {
		this(personDto.name(), personDto.istId(),
				PersonType.valueOf(personDto.type().toUpperCase()), personDto.email(), personDto.status(), personDto.phoneNumber());
		System.out.println("PersonDto: " + personDto);
		System.out.println("PersonType: " + personDto.type());

	}

	public void updateFromDto(PersonDto personDto) {
		this.name = personDto.name();
		this.istId = personDto.istId();
		this.type = PersonType.valueOf(personDto.type().toUpperCase());
		this.email = personDto.email();
		this.status = personDto.status();
		this.phoneNumber = personDto.phoneNumber();
	}
}
