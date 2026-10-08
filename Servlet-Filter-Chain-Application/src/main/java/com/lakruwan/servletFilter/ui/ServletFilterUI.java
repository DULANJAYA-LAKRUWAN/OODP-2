package com.lakruwan.servletFilter.ui;

import com.lakruwan.servletFilter.chain.FilterCallback;
import com.lakruwan.servletFilter.chain.JspValidationFilter;
import com.lakruwan.servletFilter.chain.ParameterNameFilter;
import com.lakruwan.servletFilter.chain.ParameterValueFilter;
import com.lakruwan.servletFilter.model.Request;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Interactive Swing GUI for Practical 05: Chain of Responsibility Pattern.
 * Visualizes the Servlet Filter pipeline with live status indicators and logging.
 */
public class ServletFilterUI extends JFrame implements FilterCallback {

    private final JTextField urlField;
    private final JTextField paramField;
    private final JTextArea logArea;

    // Filter Status Badges
    private final JLabel filter1StatusLabel;
    private final JLabel filter2StatusLabel;
    private final JLabel filter3StatusLabel;

    private final JPanel filter1Card;
    private final JPanel filter2Card;
    private final JPanel filter3Card;

    public ServletFilterUI() {
        super("Servlet Filter Chain of Responsibility — Practical 05");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 720);
        setLocationRelativeTo(null);

        // Main Layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Practical 05: Servlet Filter Simulation (Chain of Responsibility)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 41, 59));

        JLabel subtitleLabel = new JLabel("Visualizes Request dispatching through Filter 1 (.jsp check) → Filter 2 (param names) → Filter 3 (param values)");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center Panel: Controls + Visual Pipeline + Console
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // 1. Request Inputs Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                new EmptyBorder(12, 16, 12, 16)
        ));
        inputPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // URL Row
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        JLabel urlLabel = new JLabel("Request URL:");
        urlLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        inputPanel.add(urlLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.9;
        urlField = new JTextField("www.abc.com/a.jsp");
        urlField.setFont(new Font("Consolas", Font.PLAIN, 13));
        inputPanel.add(urlField, gbc);

        // Parameters Row
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        JLabel paramLabel = new JLabel("Query Parameters:");
        paramLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        inputPanel.add(paramLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.9;
        paramField = new JTextField("username=abc&password=123");
        paramField.setFont(new Font("Consolas", Font.PLAIN, 13));
        inputPanel.add(paramField, gbc);

        // Quick Preset Buttons
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel presetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        presetPanel.setOpaque(false);
        JLabel presetLabel = new JLabel("Quick Presets:");
        presetLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        presetPanel.add(presetLabel);

        JButton p1Btn = new JButton("1. Valid (abc / 123)");
        p1Btn.addActionListener(e -> setPreset("www.abc.com/a.jsp", "username=abc&password=123"));
        presetPanel.add(p1Btn);

        JButton p2Btn = new JButton("2. Invalid URL (.html)");
        p2Btn.addActionListener(e -> setPreset("www.abc.com/index.html", "username=abc&password=123"));
        presetPanel.add(p2Btn);

        JButton p3Btn = new JButton("3. Missing Password Param");
        p3Btn.addActionListener(e -> setPreset("www.abc.com/a.jsp", "username=abc"));
        presetPanel.add(p3Btn);

        JButton p4Btn = new JButton("4. Wrong Password Value");
        p4Btn.addActionListener(e -> setPreset("www.abc.com/a.jsp", "username=abc&password=999"));
        presetPanel.add(p4Btn);

        inputPanel.add(presetPanel, gbc);

        centerPanel.add(inputPanel);
        centerPanel.add(Box.createVerticalStrut(15));

        // 2. Visual Filter Pipeline Cards
        JPanel pipelinePanel = new JPanel(new GridLayout(1, 3, 15, 0));
        pipelinePanel.setOpaque(false);

        filter1Card = createFilterCard("Filter 1", ".jsp Validation", "Checks if requested URL ends with .jsp");
        filter1StatusLabel = (JLabel) filter1Card.getClientProperty("statusLabel");

        filter2Card = createFilterCard("Filter 2", "Parameter Names", "Verifies presence of 'username' & 'password'");
        filter2StatusLabel = (JLabel) filter2Card.getClientProperty("statusLabel");

        filter3Card = createFilterCard("Filter 3", "Parameter Values", "Authenticates username='abc' & password='123'");
        filter3StatusLabel = (JLabel) filter3Card.getClientProperty("statusLabel");

        pipelinePanel.add(filter1Card);
        pipelinePanel.add(filter2Card);
        pipelinePanel.add(filter3Card);

        centerPanel.add(pipelinePanel);
        centerPanel.add(Box.createVerticalStrut(15));

        // 3. Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        actionPanel.setOpaque(false);

        JButton sendButton = new JButton("▶ Send Request Through Filter Chain");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBackground(new Color(37, 99, 235));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.addActionListener(e -> executeChain());

        JButton resetButton = new JButton("↺ Reset Pipeline");
        resetButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resetButton.addActionListener(e -> resetPipeline());

        actionPanel.add(sendButton);
        actionPanel.add(resetButton);
        centerPanel.add(actionPanel);
        centerPanel.add(Box.createVerticalStrut(15));

        // 4. Live Log Console
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setOpaque(false);
        JLabel logTitle = new JLabel("Filter Execution Log:");
        logTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logPanel.add(logTitle, BorderLayout.NORTH);

        logArea = new JTextArea(10, 70);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setBackground(new Color(15, 23, 42));
        logArea.setForeground(new Color(241, 245, 249));
        logArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane logScroll = new JScrollPane(logArea);
        logPanel.add(logScroll, BorderLayout.CENTER);

        centerPanel.add(logPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("JIAT OODP 2 | Practical 05: Chain of Responsibility Pattern");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(148, 163, 184));
        footerPanel.add(footerLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        resetPipeline();
    }

    private JPanel createFilterCard(String filterNumber, String filterTitle, String description) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel numLabel = new JLabel(filterNumber);
        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        numLabel.setForeground(new Color(30, 41, 59));

        JLabel statusLabel = new JLabel("IDLE");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(241, 245, 249));
        statusLabel.setForeground(new Color(100, 116, 139));
        statusLabel.setBorder(new EmptyBorder(3, 8, 3, 8));

        top.add(numLabel, BorderLayout.WEST);
        top.add(statusLabel, BorderLayout.EAST);

        JLabel titleLbl = new JLabel(filterTitle);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLbl.setForeground(new Color(15, 23, 42));

        JLabel descLbl = new JLabel("<html><body style='width: 170px; color: #64748B; font-size: 10px;'>" 
                                  + description + "</body></html>");

        JPanel body = new JPanel(new BorderLayout(4, 4));
        body.setOpaque(false);
        body.add(titleLbl, BorderLayout.NORTH);
        body.add(descLbl, BorderLayout.CENTER);

        card.add(top, BorderLayout.NORTH);
        card.add(body, BorderLayout.CENTER);

        card.putClientProperty("statusLabel", statusLabel);
        return card;
    }

    private void setPreset(String url, String params) {
        urlField.setText(url);
        paramField.setText(params);
        resetPipeline();
    }

    private void resetPipeline() {
        setCardStatus(filter1Card, filter1StatusLabel, "IDLE", new Color(241, 245, 249), new Color(100, 116, 139));
        setCardStatus(filter2Card, filter2StatusLabel, "IDLE", new Color(241, 245, 249), new Color(100, 116, 139));
        setCardStatus(filter3Card, filter3StatusLabel, "IDLE", new Color(241, 245, 249), new Color(100, 116, 139));
        logArea.setText("Ready. Enter Request URL and Parameters and click 'Send Request'.\n");
    }

    private void setCardStatus(JPanel card, JLabel label, String text, Color bg, Color fg) {
        label.setText(text);
        label.setBackground(bg);
        label.setForeground(fg);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(fg, 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
    }

    private void executeChain() {
        resetPipeline();
        String url = urlField.getText().trim();
        String params = paramField.getText().trim();

        logArea.append("\n=======================================================\n");
        logArea.append(">> INCOMING HTTP REQUEST DISPATCHED\n");
        logArea.append("   Target URL: " + url + "\n");
        logArea.append("   Parameters: " + params + "\n");
        logArea.append("=======================================================\n");

        Request request = new Request(url, params);

        // Assemble Chain
        JspValidationFilter f1 = new JspValidationFilter(this);
        ParameterNameFilter f2 = new ParameterNameFilter(this);
        ParameterValueFilter f3 = new ParameterValueFilter("abc", "123", this);

        f1.setHandler(f2);
        f2.setHandler(f3);

        // Trigger chain execution
        f1.handle(request);
    }

    @Override
    public void onFilterSuccess(String filterName, String message) {
        logArea.append("  [SUCCESS] " + filterName + ": " + message + "\n");
        if (filterName.contains("Filter 1")) {
            setCardStatus(filter1Card, filter1StatusLabel, "PASSED", new Color(220, 252, 231), new Color(22, 101, 52));
        } else if (filterName.contains("Filter 2")) {
            setCardStatus(filter2Card, filter2StatusLabel, "PASSED", new Color(220, 252, 231), new Color(22, 101, 52));
        } else if (filterName.contains("Filter 3")) {
            setCardStatus(filter3Card, filter3StatusLabel, "PASSED", new Color(220, 252, 231), new Color(22, 101, 52));
        }
    }

    @Override
    public void onFilterFailure(String filterName, String errorMessage) {
        logArea.append("  [BLOCKED] " + filterName + ": " + errorMessage + "\n");
        logArea.append("  >>> Pipeline terminated. Request was NOT dispatched to target.\n");
        if (filterName.contains("Filter 1")) {
            setCardStatus(filter1Card, filter1StatusLabel, "BLOCKED", new Color(254, 226, 226), new Color(153, 27, 27));
        } else if (filterName.contains("Filter 2")) {
            setCardStatus(filter2Card, filter2StatusLabel, "BLOCKED", new Color(254, 226, 226), new Color(153, 27, 27));
        } else if (filterName.contains("Filter 3")) {
            setCardStatus(filter3Card, filter3StatusLabel, "BLOCKED", new Color(254, 226, 226), new Color(153, 27, 27));
        }
    }

    @Override
    public void onRequestComplete(String targetUrl, String message) {
        logArea.append("\n  *** SUCCESS: " + message + " ***\n");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            ServletFilterUI ui = new ServletFilterUI();
            ui.setVisible(true);
        });
    }
}
