package pt.ulisboa.tecnico.rnl.dei.dms.log;

import java.util.List;
import pt.ulisboa.tecnico.rnl.dei.dms.log.LogEntry.LogType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogService {
    
    @Autowired
    private LogRepository logRepository;

    public void log(LogType type, String details) {
        LogEntry log = new LogEntry(type, details);
        logRepository.save(log);
    }

    public List<LogEntry> getLogs() {
        return logRepository.findAll();
    }

    public List<LogEntry> getLogsByType(LogType type) {
        return logRepository.findByType(type);
    }
}
