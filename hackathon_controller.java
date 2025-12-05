import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

// Controller - handles interaction between Model and View
public class HackathonController {
    private HackathonModel model;
    private HackathonMainView view;
    private static final String DEFAULT_CSV_FILE = "HackathonTeams.csv";
    private static final String REPORT_FILE = "HackathonReport.txt";
    
    public HackathonController(HackathonModel model) {
        this.model = model;
    }
    
    public void setView(HackathonMainView view) {
        this.view = view;
    }
    
    // Initialize application
    public void initialize() {
        // Create sample CSV file if it doesn't exist
        FileManager.createSampleCSVFile(DEFAULT_CSV_FILE);
        
        // Load teams from CSV
        model.loadTeamsFromFile(DEFAULT_CSV_FILE);
        
        // Refresh view
        if (view != null) {
            view.refreshTeamsTable();
            view.refreshStatistics();
        }
    }
    
    // Refresh teams table
    public void refreshTeamsTable() {
        String category = view.getSelectedCategory();
        String sortOption = view.getSelectedSortOption();
        
        ArrayList<Team> teams;
        
        // Filter by category
        if ("All".equals(category)) {
            teams = model.getAllTeams();
        } else {
            teams = model.getTeamsByCategory(category);
        }
        
        // Sort teams
        sortTeams(teams, sortOption);
        
        // Prepare data for table
        Object[][] data = new Object[teams.size()][5];
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            data[i][0] = team.getTeamNumber();
            data[i][1] = team.getTeamName();
            data[i][2] = team.getUniversityName();
            data[i][3] = team.getCategory();
            data[i][4] = String.format("%.2f", team.getOverallScore());
        }
        
        view.updateTeamsTable(data);
    }
    
    // Sort teams based on selected option
    private void sortTeams(ArrayList<Team> teams, String sortOption) {
        switch (sortOption) {
            case "Team Number":
                teams.sort(Comparator.comparingInt(Team::getTeamNumber));
                break;
            case "Team Name":
                teams.sort(Comparator.comparing(Team::getTeamName));
                break;
            case "Overall Score":
                teams.sort((t1, t2) -> Double.compare(t2.getOverallScore(), t1.getOverallScore()));
                break;
        }
    }
    
    // Search team by number
    public void searchTeam(int teamNumber) {
        Team team = model.findTeam(teamNumber);
        
        if (team != null) {
            String result = "TEAM FOUND!\n\n" + team.getFullDetails();
            view.displaySearchResult(result);
        } else {
            view.displaySearchResult("Team with number " + teamNumber + " not found.");
        }
    }
    
    // Load team for editing
    public void loadTeamForEdit(int teamNumber) {
        Team team = model.findTeam(teamNumber);
        view.populateEditForm(team);
    }
    
    // Save team changes
    public void saveTeamChanges(int teamNumber, String teamName, String university, String category, int[] scores) {
        // Remove old team
        model.removeTeam(teamNumber);
        
        // Create new team with updated data
        Team newTeam;
        switch (category.toLowerCase()) {
            case "cybersecurity":
                newTeam = new CybersecurityTeam(teamNumber, teamName, university, scores);
                break;
            case "artificial intelligence":
                newTeam = new AITeam(teamNumber, teamName, university, scores);
                break;
            case "web development":
                newTeam = new WebDevTeam(teamNumber, teamName, university, scores);
                break;
            case "mobile development":
                newTeam = new MobileAppTeam(teamNumber, teamName, university, scores);
                break;
            default:
                newTeam = new MobileAppTeam(teamNumber, teamName, university, scores);
        }
        
        model.addTeam(newTeam);
    }
    
    // Delete team
    public boolean deleteTeam(int teamNumber) {
        return model.removeTeam(teamNumber);
    }
    
    // Refresh statistics
    public void refreshStatistics() {
        String stats = generateStatisticsText();
        view.updateStatistics(stats);
    }
    
    // Generate statistics text
    private String generateStatisticsText() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=".repeat(60)).append("\n");
        sb.append("HACKATHON STATISTICS SUMMARY\n");
        sb.append("=".repeat(60)).append("\n\n");
        
        sb.append(model.getStatisticsSummary()).append("\n\n");
        
        // Category breakdown
        sb.append("TEAMS BY CATEGORY:\n");
        sb.append("-".repeat(60)).append("\n");
        java.util.Map<String, Integer> categoryCount = model.getTeamList().getCategoryCount();
        for (java.util.Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            sb.append(String.format("%-30s: %d teams\n", entry.getKey(), entry.getValue()));
        }
        
        sb.append("\n");
        
        // Score frequency
        sb.append("INDIVIDUAL SCORE FREQUENCY:\n");
        sb.append("-".repeat(60)).append("\n");
        java.util.Map<Integer, Integer> frequency = model.getTeamList().getScoreFrequency();
        for (int score = 0; score <= 5; score++) {
            int count = frequency.getOrDefault(score, 0);
            sb.append(String.format("Score %d: %d times\n", score, count));
        }
        
        return sb.toString();
    }
    
    // Generate full report
    public void generateReport() {
        boolean success = model.saveReportToFile(REPORT_FILE);
        
        if (success) {
            JOptionPane.showMessageDialog(view, 
                "Report successfully generated!\nSaved to: " + REPORT_FILE, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, 
                "Failed to generate report!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Handle application close
    public void handleClose() {
        int option = JOptionPane.showConfirmDialog(view, 
            "Do you want to save teams to CSV before closing?", 
            "Save Changes", 
            JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            model.saveTeamsToFile(DEFAULT_CSV_FILE);
            model.saveReportToFile(REPORT_FILE);
            JOptionPane.showMessageDialog(view, 
                "Teams and report saved successfully!", 
                "Saved", 
                JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (option == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        // If CANCEL, do nothing (window stays open)
    }
}
