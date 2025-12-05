import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

// Main View - GUI Interface
public class HackathonMainView extends JFrame {
    private HackathonController controller;
    private JTabbedPane tabbedPane;
    
    // Components for View Teams Tab
    private JTable teamsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> categoryFilter;
    private JComboBox<String> sortBy;
    private JButton refreshButton;
    
    // Components for Search Team Tab
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea searchResultArea;
    
    // Components for Edit Team Tab
    private JTextField teamNumberField;
    private JTextField teamNameField;
    private JTextField universityField;
    private JComboBox<String> categoryCombo;
    private JTextField[] scoreFields;
    private JButton loadButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JLabel overallScoreLabel;
    
    // Components for Statistics Tab
    private JTextArea statsArea;
    private JButton generateReportButton;
    
    public HackathonMainView(HackathonController controller) {
        this.controller = controller;
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Hackathon Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Add window closing listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleClose();
            }
        });
        
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("View Teams", createViewTeamsPanel());
        tabbedPane.addTab("Search Team", createSearchPanel());
        tabbedPane.addTab("Edit Team", createEditPanel());
        tabbedPane.addTab("Statistics", createStatsPanel());
        
        add(tabbedPane);
    }
    
    // Create View Teams Panel
    private JPanel createViewTeamsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with filters
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Filter by Category:"));
        
        categoryFilter = new JComboBox<>(new String[]{"All", "Cybersecurity", "Artificial Intelligence", "Web Development", "Mobile Development"});
        topPanel.add(categoryFilter);
        
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(new JLabel("Sort by:"));
        
        sortBy = new JComboBox<>(new String[]{"Team Number", "Team Name", "Overall Score"});
        topPanel.add(sortBy);
        
        refreshButton = new JButton("Refresh");
        topPanel.add(refreshButton);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"Team #", "Team Name", "University", "Category", "Overall Score"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        teamsTable = new JTable(tableModel);
        teamsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(teamsTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add action listeners
        refreshButton.addActionListener(e -> refreshTeamsTable());
        categoryFilter.addActionListener(e -> refreshTeamsTable());
        sortBy.addActionListener(e -> refreshTeamsTable());
        
        return panel;
    }
    
    // Create Search Panel
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Enter Team Number:"));
        
        searchField = new JTextField(10);
        topPanel.add(searchField);
        
        searchButton = new JButton("Search");
        topPanel.add(searchButton);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Result area
        searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        searchResultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(searchResultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add action listener
        searchButton.addActionListener(e -> searchTeam());
        searchField.addActionListener(e -> searchTeam());
        
        return panel;
    }
    
    // Create Edit Panel
    private JPanel createEditPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        
        formPanel.add(new JLabel("Team Number:"));
        teamNumberField = new JTextField();
        formPanel.add(teamNumberField);
        
        formPanel.add(new JLabel("Team Name:"));
        teamNameField = new JTextField();
        formPanel.add(teamNameField);
        
        formPanel.add(new JLabel("University:"));
        universityField = new JTextField();
        formPanel.add(universityField);
        
        formPanel.add(new JLabel("Category:"));
        categoryCombo = new JComboBox<>(new String[]{"Cybersecurity", "Artificial Intelligence", "Web Development", "Mobile Development"});
        formPanel.add(categoryCombo);
        
        // Score fields
        scoreFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            formPanel.add(new JLabel("Score " + (i + 1) + ":"));
            scoreFields[i] = new JTextField();
            final int index = i;
            scoreFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    updateOverallScore();
                }
            });
            formPanel.add(scoreFields[i]);
        }
        
        formPanel.add(new JLabel("Overall Score:"));
        overallScoreLabel = new JLabel("0.00");
        overallScoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(overallScoreLabel);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        loadButton = new JButton("Load Team");
        saveButton = new JButton("Save Changes");
        deleteButton = new JButton("Delete Team");
        
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        loadButton.addActionListener(e -> loadTeamForEdit());
        saveButton.addActionListener(e -> saveTeamChanges());
        deleteButton.addActionListener(e -> deleteTeam());
        
        return panel;
    }
    
    // Create Statistics Panel
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(statsArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshStatsButton = new JButton("Refresh Statistics");
        generateReportButton = new JButton("Generate Full Report");
        
        buttonPanel.add(refreshStatsButton);
        buttonPanel.add(generateReportButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        refreshStatsButton.addActionListener(e -> refreshStatistics());
        generateReportButton.addActionListener(e -> generateReport());
        
        return panel;
    }
    
    // Refresh teams table
    public void refreshTeamsTable() {
        controller.refreshTeamsTable();
    }
    
    // Update table with data
    public void updateTeamsTable(Object[][] data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    // Search team
    private void searchTeam() {
        String input = searchField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a team number", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int teamNumber = Integer.parseInt(input);
            controller.searchTeam(teamNumber);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid team number", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Display search result
    public void displaySearchResult(String result) {
        searchResultArea.setText(result);
    }
    
    // Load team for editing
    private void loadTeamForEdit() {
        String input = teamNumberField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a team number", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int teamNumber = Integer.parseInt(input);
            controller.loadTeamForEdit(teamNumber);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid team number", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Populate edit form
    public void populateEditForm(Team team) {
        if (team == null) {
            JOptionPane.showMessageDialog(this, "Team not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        teamNumberField.setText(String.valueOf(team.getTeamNumber()));
        teamNumberField.setEditable(false);
        teamNameField.setText(team.getTeamName());
        universityField.setText(team.getUniversityName());
        categoryCombo.setSelectedItem(team.getCategory());
        
        int[] scores = team.getScoreArray();
        for (int i = 0; i < Math.min(scores.length, scoreFields.length); i++) {
            scoreFields[i].setText(String.valueOf(scores[i]));
        }
        
        updateOverallScore();
    }
    
    // Update overall score display
    private void updateOverallScore() {
        try {
            int[] scores = new int[5];
            boolean valid = true;
            
            for (int i = 0; i < 5; i++) {
                String text = scoreFields[i].getText().trim();
                if (!text.isEmpty()) {
                    scores[i] = Integer.parseInt(text);
                    if (scores[i] < 0 || scores[i] > 5) {
                        valid = false;
                        break;
                    }
                }
            }
            
            if (valid) {
                String category = (String) categoryCombo.getSelectedItem();
                Team tempTeam = createTempTeam(category, scores);
                overallScoreLabel.setText(String.format("%.2f", tempTeam.getOverallScore()));
            }
        } catch (NumberFormatException e) {
            overallScoreLabel.setText("Invalid");
        }
    }
    
    // Create temporary team for score calculation
    private Team createTempTeam(String category, int[] scores) {
        switch (category.toLowerCase()) {
            case "cybersecurity":
                return new CybersecurityTeam(0, "", "", scores);
            case "artificial intelligence":
                return new AITeam(0, "", "", scores);
            case "web development":
                return new WebDevTeam(0, "", "", scores);
            default:
                return new MobileAppTeam(0, "", "", scores);
        }
    }
    
    // Save team changes
    private void saveTeamChanges() {
        try {
            int teamNumber = Integer.parseInt(teamNumberField.getText().trim());
            String teamName = teamNameField.getText().trim();
            String university = universityField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            
            int[] scores = new int[5];
            for (int i = 0; i < 5; i++) {
                scores[i] = Integer.parseInt(scoreFields[i].getText().trim());
                if (scores[i] < 0 || scores[i] > 5) {
                    JOptionPane.showMessageDialog(this, "Scores must be between 0 and 5", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if (teamName.isEmpty() || university.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            controller.saveTeamChanges(teamNumber, teamName, university, category, scores);
            JOptionPane.showMessageDialog(this, "Team updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTeamsTable();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Delete team
    private void deleteTeam() {
        String input = teamNumberField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please load a team first", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this team?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int teamNumber = Integer.parseInt(input);
                if (controller.deleteTeam(teamNumber)) {
                    JOptionPane.showMessageDialog(this, "Team deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearEditForm();
                    refreshTeamsTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete team", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid team number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Clear edit form
    private void clearEditForm() {
        teamNumberField.setText("");
        teamNumberField.setEditable(true);
        teamNameField.setText("");
        universityField.setText("");
        for (JTextField field : scoreFields) {
            field.setText("");
        }
        overallScoreLabel.setText("0.00");
    }
    
    // Refresh statistics
    public void refreshStatistics() {
        controller.refreshStatistics();
    }
    
    // Update statistics display
    public void updateStatistics(String stats) {
        statsArea.setText(stats);
    }
    
    // Generate report
    private void generateReport() {
        controller.generateReport();
    }
    
    // Handle close
    private void handleClose() {
        controller.handleClose();
    }
    
    // Get selected category filter
    public String getSelectedCategory() {
        return (String) categoryFilter.getSelectedItem();
    }
    
    // Get selected sort option
    public String getSelectedSortOption() {
        return (String) sortBy.getSelectedItem();
    }
}
