import java.util.ArrayList;
import java.util.List;

// Category Class
class Category {
    private int categoryId;
    private String categoryName;
    private String description;
    
    public Category(int categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void updateDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return categoryName + ": " + description;
    }
}

// TeamMember Class
class TeamMember {
    private String memberName;
    private String email;
    private String studentId;
    
    public TeamMember(String memberName, String email, String studentId) {
        this.memberName = memberName;
        this.email = email;
        this.studentId = studentId;
    }
    
    public String getMemberName() {
        return memberName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void updateContactDetails(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return memberName + " (" + studentId + ") - " + email;
    }
}

// Judge Class
class Judge {
    private int judgeId;
    private String firstName;
    private String lastName;
    private String email;
    private List<Category> assignedCategories;
    
    public Judge(int judgeId, String firstName, String lastName, String email) {
        this.judgeId = judgeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.assignedCategories = new ArrayList<>();
    }
    
    public int getJudgeId() {
        return judgeId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public List<Category> getAssignedCategories() {
        return assignedCategories;
    }
    
    public void assignCategory(Category category) {
        if (!assignedCategories.contains(category)) {
            assignedCategories.add(category);
        }
    }
    
    public boolean canJudgeTeam(Team team) {
        for (Category category : assignedCategories) {
            if (category.getCategoryName().equalsIgnoreCase(team.getCategory())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Judge " + judgeId + ": " + getFullName() + " (" + email + ")";
    }
}

// Score Class
class Score {
    private int scoreId;
    private int creativity;
    private int technicalImplementation;
    private int teamwork;
    private int presentation;
    private double overallScore;
    private Judge judge;
    private Team team;
    
    public Score(int scoreId, Judge judge, Team team) {
        this.scoreId = scoreId;
        this.judge = judge;
        this.team = team;
        this.creativity = 0;
        this.technicalImplementation = 0;
        this.teamwork = 0;
        this.presentation = 0;
        this.overallScore = 0;
    }
    
    public void setScores(int creativity, int technical, int teamwork, int presentation) {
        this.creativity = creativity;
        this.technicalImplementation = technical;
        this.teamwork = teamwork;
        this.presentation = presentation;
        calculateOverall();
    }
    
    public double calculateOverall() {
        // Calculate overall as average of all components
        overallScore = (creativity + technicalImplementation + teamwork + presentation) / 4.0;
        return overallScore;
    }
    
    public int getScoreId() {
        return scoreId;
    }
    
    public int getCreativity() {
        return creativity;
    }
    
    public int getTechnicalImplementation() {
        return technicalImplementation;
    }
    
    public int getTeamwork() {
        return teamwork;
    }
    
    public int getPresentation() {
        return presentation;
    }
    
    public double getOverallScore() {
        return overallScore;
    }
    
    public Judge getJudge() {
        return judge;
    }
    
    public Team getTeam() {
        return team;
    }
    
    public boolean validateScores() {
        return creativity >= 0 && creativity <= 5 &&
               technicalImplementation >= 0 && technicalImplementation <= 5 &&
               teamwork >= 0 && teamwork <= 5 &&
               presentation >= 0 && presentation <= 5;
    }
    
    @Override
    public String toString() {
        return String.format("Score ID %d - Team: %s, Judge: %s, Overall: %.2f", 
                           scoreId, team.getTeamName(), judge.getFullName(), overallScore);
    }
}

// User Authentication Class
class User {
    private String username;
    private String password;
    private String role; // "ADMIN", "JUDGE", "PARTICIPANT", "PUBLIC"
    
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getRole() {
        return role;
    }
    
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    
    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}
