package com.lakruwan.memento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * FileEditorUI: Interactive GUI demonstration for Memento Pattern
 */
public class FileEditorUI extends JFrame {
    private final FileDocument document;
    private final FileHistoryManager historyManager;

    private JTextField txtFileName;
    private JTextArea txtContent;
    private DefaultListModel<String> historyListModel;
    private JList<String> listHistory;
    private JLabel lblStatus;
    private JButton btnSave;
    private JButton btnUndo;
    private JButton btnRedo;

    public FileEditorUI() {
        document = new FileDocument("Untitled.txt", "Welcome to Memento File Editor!\nType here and save snapshots.");
        historyManager = new FileHistoryManager();

        initComponents();
        updateUIState("Ready. Created file: " + document.getFileName());
    }

    private void initComponents() {
        setTitle("Practical 09: Memento Pattern - File Restoring Application");
        setSize(850, 580);
        setMinimumSize(new Dimension(750, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout(10, 5));
        headerPanel.setBackground(new Color(245, 247, 250));

        JLabel lblTitle = new JLabel("Memento Design Pattern: File Restore Demo");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(33, 37, 41));

        JPanel fileInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        fileInfoPanel.setBackground(new Color(245, 247, 250));
        fileInfoPanel.add(new JLabel("File Name:"));
        txtFileName = new JTextField(document.getFileName(), 20);
        txtFileName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        fileInfoPanel.add(txtFileName);

        JButton btnNewFile = new JButton("New File");
        btnNewFile.setBackground(new Color(230, 235, 242));
        btnNewFile.setFocusPainted(false);
        btnNewFile.addActionListener(e -> createNewFile());
        fileInfoPanel.add(btnNewFile);

        headerPanel.add(lblTitle, BorderLayout.NORTH);
        headerPanel.add(fileInfoPanel, BorderLayout.SOUTH);

        // Center Split: Editor on Left, Memento Stack List on Right
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.68);
        splitPane.setDividerSize(6);

        // Editor Panel
        JPanel editorPanel = new JPanel(new BorderLayout(5, 5));
        editorPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 205, 215)),
                "Originator (Active File Content)",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(40, 60, 90)
        ));

        txtContent = new JTextArea(document.getContent());
        txtContent.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);
        txtContent.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane editorScrollPane = new JScrollPane(txtContent);
        editorPanel.add(editorScrollPane, BorderLayout.CENTER);

        // Toolbar for Editor
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        toolbarPanel.setBackground(Color.WHITE);

        btnSave = new JButton("💾 Save State (Memento)");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(e -> saveSnapshot());

        btnUndo = new JButton("↩️ Restore (Undo)");
        btnUndo.setBackground(new Color(70, 130, 180));
        btnUndo.setForeground(Color.WHITE);
        btnUndo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnUndo.setFocusPainted(false);
        btnUndo.addActionListener(e -> undoRestore());

        btnRedo = new JButton("↪️ Redo");
        btnRedo.setBackground(new Color(112, 128, 144));
        btnRedo.setForeground(Color.WHITE);
        btnRedo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRedo.setFocusPainted(false);
        btnRedo.addActionListener(e -> redoRestore());

        JButton btnCorrupt = new JButton("💥 Simulate Accidental Erase");
        btnCorrupt.setBackground(new Color(220, 53, 69));
        btnCorrupt.setForeground(Color.WHITE);
        btnCorrupt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCorrupt.setFocusPainted(false);
        btnCorrupt.addActionListener(e -> {
            txtContent.setText("");
            updateUIState("Accidental edit: Content cleared! Click 'Restore (Undo)' to recover.");
        });

        toolbarPanel.add(btnSave);
        toolbarPanel.add(btnUndo);
        toolbarPanel.add(btnRedo);
        toolbarPanel.add(btnCorrupt);

        editorPanel.add(toolbarPanel, BorderLayout.SOUTH);

        // History / Caretaker Panel
        JPanel caretakerPanel = new JPanel(new BorderLayout(5, 5));
        caretakerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 205, 215)),
                "Caretaker (Memento Stack)",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(40, 60, 90)
        ));

        historyListModel = new DefaultListModel<>();
        listHistory = new JList<>(historyListModel);
        listHistory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        listHistory.setBackground(new Color(250, 252, 255));
        JScrollPane historyScrollPane = new JScrollPane(listHistory);
        caretakerPanel.add(historyScrollPane, BorderLayout.CENTER);

        JLabel lblStackHint = new JLabel("<html><small>Top item is popped on Restore.<br>Encapsulation is strictly preserved.</small></html>");
        lblStackHint.setBorder(new EmptyBorder(5, 5, 5, 5));
        lblStackHint.setForeground(Color.GRAY);
        caretakerPanel.add(lblStackHint, BorderLayout.SOUTH);

        splitPane.setLeftComponent(editorPanel);
        splitPane.setRightComponent(caretakerPanel);

        // Bottom Status Bar
        lblStatus = new JLabel(" Ready");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStatus.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 215, 220)),
                new EmptyBorder(6, 6, 6, 6)
        ));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(lblStatus, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void createNewFile() {
        String name = JOptionPane.showInputDialog(this, "Enter file name:", "New File", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            document.setFileName(name.trim());
            document.setContent("");
            txtFileName.setText(document.getFileName());
            txtContent.setText("");
            updateUIState("Created new file: " + document.getFileName());
        }
    }

    private void saveSnapshot() {
        // Sync document from editor
        document.setFileName(txtFileName.getText().trim());
        document.setContent(txtContent.getText());

        String desc = JOptionPane.showInputDialog(this, "Enter save checkpoint label:", "Save State", JOptionPane.QUESTION_MESSAGE);
        if (desc == null || desc.trim().isEmpty()) {
            desc = "Saved snapshot #" + (historyManager.getUndoCount() + 1);
        }

        historyManager.saveState(document, desc);
        updateUIState("Saved state to Memento: \"" + desc + "\"");
    }

    private void undoRestore() {
        if (!historyManager.canUndo()) {
            JOptionPane.showMessageDialog(this, "No previous state to restore in Memento Stack!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        FileSnapshot restored = historyManager.undo(document);
        if (restored != null) {
            txtFileName.setText(document.getFileName());
            txtContent.setText(document.getContent());
            updateUIState("Restored to: \"" + restored.getDescription() + "\"");
        }
    }

    private void redoRestore() {
        if (!historyManager.canRedo()) {
            JOptionPane.showMessageDialog(this, "No redo states available!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        FileSnapshot redone = historyManager.redo(document);
        if (redone != null) {
            txtFileName.setText(document.getFileName());
            txtContent.setText(document.getContent());
            updateUIState("Redone to: \"" + redone.getDescription() + "\"");
        }
    }

    private void updateUIState(String message) {
        lblStatus.setText(" " + message);

        // Update button states
        btnUndo.setEnabled(historyManager.canUndo());
        btnRedo.setEnabled(historyManager.canRedo());

        // Refresh Memento Stack list
        historyListModel.clear();
        List<FileSnapshot> history = historyManager.getUndoHistory();
        if (history.isEmpty()) {
            historyListModel.addElement("(Empty - No snapshots)");
        } else {
            for (int i = 0; i < history.size(); i++) {
                FileSnapshot snap = history.get(i);
                String prefix = (i == 0) ? "► [TOP] " : "   ";
                historyListModel.addElement(prefix + snap.toString());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FileEditorUI().setVisible(true);
        });
    }
}

