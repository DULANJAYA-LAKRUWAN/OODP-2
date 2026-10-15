package com.lakruwan.memento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Memento: FileSnapshot
 * 
 * Stores the immutable snapshot of a FileDocument.
 */
public class FileSnapshot {
    private final String fileName;
    private final String content;
    private final LocalDateTime timestamp;
    private final String description;

    public FileSnapshot(String fileName, String content, String description) {
        this.fileName = fileName;
        this.content = content;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "[" + getFormattedTimestamp() + "] " + description + " (" + content.length() + " chars)";
    }
}

