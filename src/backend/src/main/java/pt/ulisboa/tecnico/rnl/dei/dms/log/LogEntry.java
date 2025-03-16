package pt.ulisboa.tecnico.rnl.dei.dms.log;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.extern.java.Log;


@Entity
@Table(name = "logs")
public class LogEntry {
    
    public enum LogType {
        CREATE_PERSON, EDIT_PERSON, DELETE_PERSON, UPDATE_THESIS_WORKFLOW, UPDATE_DEFENSE_WORKFLOW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LogType type;

    @Column(columnDefinition = "TEXT")
    private String details;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public LogEntry() {
    }

    public LogEntry(LogType type, String details) {
        this.type = type;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public LogType getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
