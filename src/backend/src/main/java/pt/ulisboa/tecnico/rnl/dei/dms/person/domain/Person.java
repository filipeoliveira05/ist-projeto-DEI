package pt.ulisboa.tecnico.rnl.dei.dms.person.domain;

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

    public enum ThesisWorkflowState {
        NONE, PROPOSTA_JURI_SUBMETIDA, APROVADO_PELO_SC, PRESIDENTE_JURI_ATRIBUIDO, DOCUMENTO_ASSINADO, SUBMETIDO_AO_FENIX
    }

    public enum DefenseWorkflowState {
        NONE, DEFESA_AGENDADA, EM_REVISAO, SUBMETIDO_AO_FENIX
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

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "thesis_workflow_state", nullable = false)
    private ThesisWorkflowState thesisWorkflowState = ThesisWorkflowState.NONE;

    @Enumerated(EnumType.STRING)
    @Column(name = "defense_workflow_state", nullable = false)
    private DefenseWorkflowState defenseWorkflowState = DefenseWorkflowState.NONE;

    protected Person() {
    }

    public Person(String name, String istId, PersonType type, String email, String phoneNumber) {
        this.name = name;
        this.istId = istId;
        this.type = type;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.thesisWorkflowState = ThesisWorkflowState.NONE;
        this.defenseWorkflowState = DefenseWorkflowState.NONE;
    }

    public Person(PersonDto personDto) {
        this(personDto.name(), personDto.istId(),
		PersonType.valueOf(personDto.type().toUpperCase()),
		personDto.email(), personDto.phoneNumber());
    }

    public void updateFromDto(PersonDto personDto) {
        this.name = personDto.name();
        this.istId = personDto.istId();
        this.type = PersonType.valueOf(personDto.type().toUpperCase());
        this.email = personDto.email();
        this.phoneNumber = personDto.phoneNumber();
    }

    public String getDetails() {
        return String.format(
            "<b>ID:</b> %d, <b>Nome:</b> %s, <b>IST ID:</b> %s, <b>Tipo:</b> %s, <b>Email:</b> %s, <b>Telefone:</b> %s",
            this.id,
            this.name,
            this.istId,
            this.type,
            this.email,
            this.phoneNumber
        );
    }    

    public void setThesisWorkflowState(ThesisWorkflowState newState) {
        this.thesisWorkflowState = newState;
    }

    public void setDefenseWorkflowState(DefenseWorkflowState newState) {
        this.defenseWorkflowState = newState;
    }
}
