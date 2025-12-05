// Abstract Team Base Class
public abstract class Team {
    protected int teamNumber;
    protected String teamName;
    protected String universityName;
    protected String category;
    protected int[] scores;
    
    // Constructor
    public Team(int teamNumber, String teamName, String universityName, String category, int[] scores) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
        this.universityName = universityName;
        this.category = category;
        this.scores = scores;
    }
    
    // Getters and Setters
    public int getTeamNumber() {
        return teamNumber;
    }
    
    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public String getUniversityName() {
        return universityName;
    }
    
    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int[] getScoreArray() {
        return scores;
    }
    
    public void setScores(int[] scores) {
        this.scores = scores;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract double getOverallScore();
    
    // Get full details
    public String getFullDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team ID ").append(teamNumber).append(", name ").append(teamName).append("\n");
        sb.append(teamName).append(" from ").append(universityName);
        sb.append(" is competing in the ").append(category).append(" category\n");
        sb.append("Scores: [");
        for (int i = 0; i < scores.length; i++) {
            sb.append(scores[i]);
            if (i < scores.length - 1) sb.append(", ");
        }
        sb.append("], Overall Score: ").append(String.format("%.2f", getOverallScore()));
        return sb.toString();
    }
    
    // Get short details
    public String getShortDetails() {
        String initials = getInitials(teamName);
        return String.format("TID %d (%s) has an overall score of %.2f", 
                           teamNumber, initials, getOverallScore());
    }
    
    // Helper method to get initials
    private String getInitials(String name) {
        String[] words = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                initials.append(word.charAt(0));
            }
        }
        return initials.toString().toUpperCase();
    }
    
    @Override
    public String toString() {
        return getShortDetails();
    }
}
