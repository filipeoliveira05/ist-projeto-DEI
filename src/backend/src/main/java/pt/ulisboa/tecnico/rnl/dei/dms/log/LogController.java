package pt.ulisboa.tecnico.rnl.dei.dms.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.dms.log.LogEntry;
import pt.ulisboa.tecnico.rnl.dei.dms.log.LogService;
import pt.ulisboa.tecnico.rnl.dei.dms.log.LogEntry.LogType;

@RestController
public class LogController {
    
    @Autowired
    private LogService logService;

    @GetMapping("/logs")
    public List<LogEntry> getLogs() {
        return logService.getLogs();
    }

    @GetMapping("/logs/type/{type}")
    public List<LogEntry> getLogsByType(@PathVariable LogType type) {
        return logService.getLogsByType(type);
    }
}
