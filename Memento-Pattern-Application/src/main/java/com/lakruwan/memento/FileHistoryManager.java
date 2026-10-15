package com.lakruwan.memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Caretaker: FileHistoryManager
 * 
 * Manages the history of FileSnapshots using undo and redo stacks.
 * It never reads or modifies the snapshot data itself.
 */
public class FileHistoryManager {
    private final Stack<FileSnapshot> undoStack = new Stack<>();
    private final Stack<FileSnapshot> redoStack = new Stack<>();

    public void saveState(FileDocument document, String description) {
        if (document != null) {
            undoStack.push(document.createSnapshot(description));
            redoStack.clear(); // Clear redo stack on new operation
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public FileSnapshot undo(FileDocument document) {
        if (!canUndo()) {
            return null;
        }

        // Push current state to redo stack before restoring
        redoStack.push(document.createSnapshot("Redo state before undoing"));

        FileSnapshot snapshot = undoStack.pop();
        document.restoreFromSnapshot(snapshot);
        return snapshot;
    }

    public FileSnapshot redo(FileDocument document) {
        if (!canRedo()) {
            return null;
        }

        // Push current state to undo stack before redoing
        undoStack.push(document.createSnapshot("State before redoing"));

        FileSnapshot snapshot = redoStack.pop();
        document.restoreFromSnapshot(snapshot);
        return snapshot;
    }

    public List<FileSnapshot> getUndoHistory() {
        List<FileSnapshot> list = new ArrayList<>(undoStack);
        Collections.reverse(list);
        return list;
    }

    public int getUndoCount() {
        return undoStack.size();
    }

    public int getRedoCount() {
        return redoStack.size();
    }
}

