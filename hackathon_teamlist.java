import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamList {
    private ArrayList<Team> teams;
    
    public TeamList() {
        teams = new ArrayList<>();
    }
    
    // Add a team
    public void addTeam(Team team) {
        teams.add(team);
    }
    
    // Get all teams
    public ArrayList<Team> getAllTeams() {
        return teams;
    }
    
    // Find team by number
    public Team findTeamByNumber(int teamNumber) {
        for (Team team : teams) {
            if (team.getTeamNumber() == teamNumber) {
                return team;
            }
        }
        return null;
    }
    
    // Remove team
    public boolean removeTeam(int teamNumber) {
        Team team = findTeamByNumber(teamNumber);
        if (team != null) {
            teams.remove(team);
            return true;
        }
        return false;
    }
    
    // Get team with highest score
    public Team getHighestScoringTeam() {
        if (teams.isEmpty()) return null;
        
        Team highest = teams.get(0);
        for (Team team : teams) {
            if (team.getOverallScore() > highest.getOverallScore()) {
                highest = team;
            }
        }
        return highest;
    }
    
    // Get average overall score
    public double getAverageOverallScore() {
        if (teams.isEmpty()) return 0;
        
        double sum = 0;
        for (Team team : teams) {
            sum += team.getOverallScore();
        }
        return sum / teams.size();
    }
    
    // Get minimum overall score
    public double getMinimumOverallScore() {
        if (teams.isEmpty()) return 0;
        
        double min = teams.get(0).getOverallScore();
        for (Team team : teams) {
            if (team.getOverallScore() < min) {
                min = team.getOverallScore();
            }
        }
        return min;
    }
    
    // Get maximum overall score
    public double getMaximumOverallScore() {
        if (teams.isEmpty()) return 0;
        
        double max = teams.get(0).getOverallScore();
        for (Team team : teams) {
            if (team.getOverallScore() > max) {
                max = team.getOverallScore();
            }
        }
        return max;
    }
    
    // Get frequency report of individual scores
    public Map<Integer, Integer> getScoreFrequency() {
        Map<Integer, Integer> frequency = new HashMap<>();
        
        for (Team team : teams) {
            int[] scores = team.getScoreArray();
            for (int score : scores) {
                frequency.put(score, frequency.getOrDefault(score, 0) + 1);
            }
        }
        
        return frequency;
    }
    
    // Get teams by category
    public ArrayList<Team> getTeamsByCategory(String category) {
        ArrayList<Team> filtered = new ArrayList<>();
        for (Team team : teams) {
            if (team.getCategory().equalsIgnoreCase(category)) {
                filtered.add(team);
            }
        }
        return filtered;
    }
    
    // Get count by category
    public Map<String, Integer> getCategoryCount() {
        Map<String, Integer> count = new HashMap<>();
        for (Team team : teams) {
            String category = team.getCategory();
            count.put(category, count.getOrDefault(category, 0) + 1);
        }
        return count;
    }
    
    // Get full details table
    public String getFullDetailsTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(80)).append("\n");
        sb.append("HACKATHON TEAMS - FULL DETAILS\n");
        sb.append("=".repeat(80)).append("\n\n");
        
        for (Team team : teams) {
            sb.append(team.getFullDetails()).append("\n");
            sb.append("-".repeat(80)).append("\n");
        }
        
        return sb.toString();
    }
    
    // Generate complete report
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=".repeat(80)).append("\n");
        report.append("HACKATHON MANAGEMENT SYSTEM - FINAL REPORT\n");
        report.append("=".repeat(80)).append("\n\n");
        
        // Full details table
        report.append(getFullDetailsTable()).append("\n");
        
        // Highest scoring team
        Team highest = getHighestScoringTeam();
        if (highest != null) {
            report.append("HIGHEST SCORING TEAM:\n");
            report.append(highest.getFullDetails()).append("\n\n");
        }
        
        // Summary statistics
        report.append("SUMMARY STATISTICS:\n");
        report.append("-".repeat(80)).append("\n");
        report.append(String.format("Total Teams: %d\n", teams.size()));
        report.append(String.format("Average Overall Score: %.2f\n", getAverageOverallScore()));
        report.append(String.format("Minimum Overall Score: %.2f\n", getMinimumOverallScore()));
        report.append(String.format("Maximum Overall Score: %.2f\n\n", getMaximumOverallScore()));
        
        // Category breakdown
        report.append("TEAMS BY CATEGORY:\n");
        report.append("-".repeat(80)).append("\n");
        Map<String, Integer> categoryCount = getCategoryCount();
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            report.append(String.format("%s: %d teams\n", entry.getKey(), entry.getValue()));
        }
        report.append("\n");
        
        // Frequency report
        report.append("INDIVIDUAL SCORE FREQUENCY:\n");
        report.append("-".repeat(80)).append("\n");
        Map<Integer, Integer> frequency = getScoreFrequency();
        for (int score = 0; score <= 5; score++) {
            int count = frequency.getOrDefault(score, 0);
            report.append(String.format("Score %d: %d times\n", score, count));
        }
        
        report.append("\n").append("=".repeat(80)).append("\n");
        report.append("END OF REPORT\n");
        report.append("=".repeat(80)).append("\n");
        
        return report.toString();
    }
    
    // Get size
    public int size() {
        return teams.size();
    }
}
