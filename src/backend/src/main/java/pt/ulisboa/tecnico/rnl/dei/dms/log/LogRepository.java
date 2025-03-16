package pt.ulisboa.tecnico.rnl.dei.dms.log;

import pt.ulisboa.tecnico.rnl.dei.dms.log.LogEntry.LogType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LogRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByType(LogType type);
}
