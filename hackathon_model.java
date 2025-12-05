import java.util.ArrayList;
import java.util.List;

// Model class - manages data and business logic
public class HackathonModel {
    private TeamList teamList;
    private List<Judge> judges;
    private List<Category> categories;
    private List<User> users;
    private User currentUser;
    
    public HackathonModel() {
        this.teamList = new TeamList();
        this.judges = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.users = new ArrayList<>();
        this.currentUser = null;
        
        // Initialize default categories
        initializeCategories();
        
        // Initialize default users
        initializeUsers();
        
        // Initialize sample judges
        initializeJudges();
    }
    
    private void initializeCategories() {
        categories.add(new Category(1, "Cybersecurity", "Security-focused applications and tools"));
        categories.add(new Category(2, "Artificial Intelligence", "AI and Machine Learning projects"));
        categories.add(new Category(3, "Web Development", "Web applications and services"));
        categories.add(new Category(4, "Mobile Development", "Mobile apps for Android/iOS"));
    }
    
    private void initializeUsers() {
        users.add(new User("admin", "admin123", "ADMIN"));
        users.add(new User("judge1", "judge123", "JUDGE"));
        users.add(new User("judge2", "judge123", "JUDGE"));
        users.add(new User("participant", "part123", "PARTICIPANT"));
        users.add(new User("public", "public123", "PUBLIC"));
    }
    
    private void initializeJudges() {
        Judge judge1 = new Judge(1, "John", "Smith", "john.smith@hackathon.com");
        judge1.assignCategory(categories.get(0)); // Cybersecurity
        judge1.assignCategory(categories.get(1)); // AI
        judges.add(judge1);
        
        Judge judge2 = new Judge(2, "Sarah", "Johnson", "sarah.j@hackathon.com");
        judge2.assignCategory(categories.get(2)); // Web Dev
        judge2.assignCategory(categories.get(3)); // Mobile
        judges.add(judge2);
    }
    
    // User authentication
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    // Team management
    public TeamList getTeamList() {
        return teamList;
    }
    
    public void setTeamList(TeamList teamList) {
        this.teamList = teamList;
    }
    
    public void addTeam(Team team) {
        teamList.addTeam(team);
    }
    
    public boolean removeTeam(int teamNumber) {
        return teamList.removeTeam(teamNumber);
    }
    
    public Team findTeam(int teamNumber) {
        return teamList.findTeamByNumber(teamNumber);
    }
    
    public ArrayList<Team> getAllTeams() {
        return teamList.getAllTeams();
    }
    
    public ArrayList<Team> getTeamsByCategory(String category) {
        return teamList.getTeamsByCategory(category);
    }
    
    // Judge management
    public List<Judge> getJudges() {
        return judges;
    }
    
    public void addJudge(Judge judge) {
        judges.add(judge);
    }
    
    public Judge findJudge(int judgeId) {
        for (Judge judge : judges) {
            if (judge.getJudgeId() == judgeId) {
                return judge;
            }
        }
        return null;
    }
    
    // Category management
    public List<Category> getCategories() {
        return categories;
    }
    
    public void addCategory(Category category) {
        categories.add(category);
    }
    
    public Category findCategory(String categoryName) {
        for (Category category : categories) {
            if (category.getCategoryName().equalsIgnoreCase(categoryName)) {
                return category;
            }
        }
        return null;
    }
    
    // File operations
    public void loadTeamsFromFile(String filename) {
        this.teamList = FileManager.readTeamsFromCSV(filename);
    }
    
    public boolean saveTeamsToFile(String filename) {
        return FileManager.writeTeamsToCSV(filename, teamList);
    }
    
    public boolean saveReportToFile(String filename) {
        String report = teamList.generateReport();
        return FileManager.writeReportToFile(filename, report);
    }
    
    // Statistics
    public String getStatisticsSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Teams: ").append(teamList.size()).append("\n");
        sb.append(String.format("Average Score: %.2f\n", teamList.getAverageOverallScore()));
        sb.append(String.format("Highest Score: %.2f\n", teamList.getMaximumOverallScore()));
        sb.append(String.format("Lowest Score: %.2f\n", teamList.getMinimumOverallScore()));
        
        Team highest = teamList.getHighestScoringTeam();
        if (highest != null) {
            sb.append("\nTop Team: ").append(highest.getTeamName());
            sb.append(String.format(" (%.2f)", highest.getOverallScore()));
        }
        
        return sb.toString();
    }
}
