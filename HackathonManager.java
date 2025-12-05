import java.util.Scanner;

// Console-based Manager class for testing without GUI
public class HackathonManager {
    private TeamList teamList;
    private Scanner scanner;
    private static final String CSV_FILE = "HackathonTeams.csv";
    private static final String REPORT_FILE = "HackathonReport.txt";
    
    public HackathonManager() {
        this.teamList = new TeamList();
        this.scanner = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        HackathonManager manager = new HackathonManager();
        manager.run();
    }
    
    public void run() {
        System.out.println("=".repeat(60));
        System.out.println("HACKATHON MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        
        // Create sample file if it doesn't exist
        FileManager.createSampleCSVFile(CSV_FILE);
        
        // Load teams from file
        loadTeamsFromFile();
        
        // Main menu loop
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    displayAllTeams();
                    break;
                case 2:
                    searchTeam();
                    break;
                case 3:
                    displayHighestScoringTeam();
                    break;
                case 4:
                    displayStatistics();
                    break;
                case 5:
                    generateReport();
                    break;
                case 6:
                    addNewTeam();
                    break;
                case 7:
                    editTeam();
                    break;
                case 8:
                    deleteTeam();
                    break;
                case 9:
                    saveTeamsToFile();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        System.out.println("\nThank you for using Hackathon Management System!");
        scanner.close();
    }
    
    private void displayMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(60));
        System.out.println("1. Display All Teams");
        System.out.println("2. Search Team by Number");
        System.out.println("3. Display Highest Scoring Team");
        System.out.println("4. Display Statistics");
        System.out.println("5. Generate Full Report");
        System.out.println("6. Add New Team");
        System.out.println("7. Edit Team");
        System.out.println("8. Delete Team");
        System.out.println("9. Save Teams to File");
        System.out.println("0. Exit");
        System.out.println("=".repeat(60));
    }
    
    private void loadTeamsFromFile() {
        System.out.println("\nLoading teams from " + CSV_FILE + "...");
        teamList = FileManager.readTeamsFromCSV(CSV_FILE);
        System.out.println("Loaded " + teamList.size() + " teams successfully!");
    }
    
    private void displayAllTeams() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALL TEAMS");
        System.out.println("=".repeat(60));
        
        if (teamList.size() == 0) {
            System.out.println("No teams found.");
            return;
        }
        
        System.out.println(teamList.getFullDetailsTable());
    }
    
    private void searchTeam() {
        int teamNumber = getIntInput("\nEnter team number to search: ");
        Team team = teamList.findTeamByNumber(teamNumber);
        
        if (team != null) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("TEAM FOUND");
            System.out.println("=".repeat(60));
            System.out.println(team.getFullDetails());
        } else {
            System.out.println("Team with number " + teamNumber + " not found.");
        }
    }
    
    private void displayHighestScoringTeam() {
        Team highest = teamList.getHighestScoringTeam();
        
        if (highest != null) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("HIGHEST SCORING TEAM");
            System.out.println("=".repeat(60));
            System.out.println(highest.getFullDetails());
        } else {
            System.out.println("No teams found.");
        }
    }
    
    private void displayStatistics() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("STATISTICS SUMMARY");
        System.out.println("=".repeat(60));
        
        System.out.println("Total Teams: " + teamList.size());
        System.out.printf("Average Overall Score: %.2f\n", teamList.getAverageOverallScore());
        System.out.printf("Minimum Overall Score: %.2f\n", teamList.getMinimumOverallScore());
        System.out.printf("Maximum Overall Score: %.2f\n", teamList.getMaximumOverallScore());
        
        System.out.println("\nTeams by Category:");
        java.util.Map<String, Integer> categoryCount = teamList.getCategoryCount();
        for (java.util.Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " teams");
        }
        
        System.out.println("\nIndividual Score Frequency:");
        java.util.Map<Integer, Integer> frequency = teamList.getScoreFrequency();
        for (int score = 0; score <= 5; score++) {
            int count = frequency.getOrDefault(score, 0);
            System.out.println("Score " + score + ": " + count + " times");
        }
    }
    
    private void generateReport() {
        System.out.println("\nGenerating full report...");
        boolean success = FileManager.writeReportToFile(REPORT_FILE, teamList.generateReport());
        
        if (success) {
            System.out.println("Report successfully generated and saved to " + REPORT_FILE);
        } else {
            System.out.println("Failed to generate report.");
        }
    }
    
    private void addNewTeam() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ADD NEW TEAM");
        System.out.println("=".repeat(60));
        
        int teamNumber = getIntInput("Enter team number: ");
        
        // Check if team already exists
        if (teamList.findTeamByNumber(teamNumber) != null) {
            System.out.println("Team with this number already exists!");
            return;
        }
        
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        
        System.out.print("Enter university name: ");
        String university = scanner.nextLine();
        
        System.out.println("\nSelect category:");
        System.out.println("1. Cybersecurity");
        System.out.println("2. Artificial Intelligence");
        System.out.println("3. Web Development");
        System.out.println("4. Mobile Development");
        int categoryChoice = getIntInput("Enter choice: ");
        
        String category;
        switch (categoryChoice) {
            case 1: category = "Cybersecurity"; break;
            case 2: category = "Artificial Intelligence"; break;
            case 3: category = "Web Development"; break;
            case 4: category = "Mobile Development"; break;
            default:
                System.out.println("Invalid category choice. Using Mobile Development.");
                category = "Mobile Development";
        }
        
        int[] scores = new int[5];
        for (int i = 0; i < 5; i++) {
            scores[i] = getScoreInput("Enter score " + (i + 1) + " (0-5): ");
        }
        
        Team newTeam = createTeam(teamNumber, teamName, university, category, scores);
        teamList.addTeam(newTeam);
        
        System.out.println("\nTeam added successfully!");
        System.out.println(newTeam.getShortDetails());
    }
    
    private void editTeam() {
        int teamNumber = getIntInput("\nEnter team number to edit: ");
        Team team = teamList.findTeamByNumber(teamNumber);
        
        if (team == null) {
            System.out.println("Team not found!");
            return;
        }
        
        System.out.println("\nCurrent team details:");
        System.out.println(team.getFullDetails());
        
        System.out.print("\nEnter new team name (or press Enter to keep current): ");
        String teamName = scanner.nextLine();
        if (teamName.isEmpty()) {
            teamName = team.getTeamName();
        }
        
        System.out.print("Enter new university (or press Enter to keep current): ");
        String university = scanner.nextLine();
        if (university.isEmpty()) {
            university = team.getUniversityName();
        }
        
        System.out.println("\nUpdate scores? (y/n): ");
        String updateScores = scanner.nextLine();
        
        int[] scores = team.getScoreArray().clone();
        if (updateScores.equalsIgnoreCase("y")) {
            for (int i = 0; i < 5; i++) {
                scores[i] = getScoreInput("Enter score " + (i + 1) + " (0-5): ");
            }
        }
        
        // Remove old team and add updated team
        teamList.removeTeam(teamNumber);
        Team updatedTeam = createTeam(teamNumber, teamName, university, team.getCategory(), scores);
        teamList.addTeam(updatedTeam);
        
        System.out.println("\nTeam updated successfully!");
        System.out.println(updatedTeam.getShortDetails());
    }
    
    private void deleteTeam() {
        int teamNumber = getIntInput("\nEnter team number to delete: ");
        Team team = teamList.findTeamByNumber(teamNumber);
        
        if (team == null) {
            System.out.println("Team not found!");
            return;
        }
        
        System.out.println("\nTeam to delete:");
        System.out.println(team.getShortDetails());
        
        System.out.print("Are you sure? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            if (teamList.removeTeam(teamNumber)) {
                System.out.println("Team deleted successfully!");
            } else {
                System.out.println("Failed to delete team.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private void saveTeamsToFile() {
        System.out.println("\nSaving teams to " + CSV_FILE + "...");
        boolean success = FileManager.writeTeamsToCSV(CSV_FILE, teamList);
        
        if (success) {
            System.out.println("Teams saved successfully!");
        } else {
            System.out.println("Failed to save teams.");
        }
    }
    
    // Helper method to create team based on category
    private Team createTeam(int teamNumber, String teamName, String university, String category, int[] scores) {
        switch (category.toLowerCase()) {
            case "cybersecurity":
                return new CybersecurityTeam(teamNumber, teamName, university, scores);
            case "artificial intelligence":
                return new AITeam(teamNumber, teamName, university, scores);
            case "web development":
                return new WebDevTeam(teamNumber, teamName, university, scores);
            case "mobile development":
                return new MobileAppTeam(teamNumber, teamName, university, scores);
            default:
                return new MobileAppTeam(teamNumber, teamName, university, scores);
        }
    }
    
    // Helper method to get integer input
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    // Helper method to get score input (0-5)
    private int getScoreInput(String prompt) {
        while (true) {
            int score = getIntInput(prompt);
            if (score >= 0 && score <= 5) {
                return score;
            }
            System.out.println("Score must be between 0 and 5.");
        }
    }
}
