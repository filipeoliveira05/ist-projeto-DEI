package pt.ulisboa.tecnico.rnl.dei.dms.person.repository;

import java.util.Optional;
import java.util.List;
//import java.lang.foreign.Linker.Option;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);
    Optional<Person> findByIstId(String istId);
    List<Person> findByType(Person.PersonType type);
    Optional<Person> findByEmail(String email);
    List<Person> findByStatus(boolean status);
    Optional<Person> findByPhoneNumber(String phoneNumber);
    List<Person> findByThesisWorkflowState(Person.ThesisWorkflowState thesisWorkflowState);
    List<Person> findByDefenseWorkflowState(Person.DefenseWorkflowState defenseWorkflowState);
}
