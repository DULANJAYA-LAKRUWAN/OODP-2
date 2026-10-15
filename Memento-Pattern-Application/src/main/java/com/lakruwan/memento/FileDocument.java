package com.lakruwan.memento;

/**
 * Originator: FileDocument
 * 
 * Represents an active text file whose content can be modified,
 * captured into a FileSnapshot, and restored from a FileSnapshot.
 */
public class FileDocument {
    private String fileName;
    private StringBuilder content;

    public FileDocument(String fileName) {
        this.fileName = fileName;
        this.content = new StringBuilder();
    }

    public FileDocument(String fileName, String initialContent) {
        this.fileName = fileName;
        this.content = new StringBuilder(initialContent);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content.toString();
    }

    public void setContent(String newContent) {
        this.content = new StringBuilder(newContent);
    }

    public void append(String text) {
        this.content.append(text);
    }

    /**
     * Creates a Memento (FileSnapshot) capturing the current document state.
     */
    public FileSnapshot createSnapshot(String description) {
        return new FileSnapshot(this.fileName, this.content.toString(), description);
    }

    /**
     * Restores state from a Memento (FileSnapshot).
     */
    public void restoreFromSnapshot(FileSnapshot snapshot) {
        if (snapshot != null) {
            this.fileName = snapshot.getFileName();
            this.content = new StringBuilder(snapshot.getContent());
        }
    }
}

