import java.io.*;
import java.util.ArrayList;

public class FileManager {
    
    // Read teams from CSV file
    public static TeamList readTeamsFromCSV(String filename) {
        TeamList teamList = new TeamList();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                // Skip header line
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                try {
                    Team team = parseTeamLine(line);
                    if (team != null) {
                        teamList.addTeam(team);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    System.err.println("Error: " + e.getMessage());
                }
            }
            
            System.out.println("Successfully loaded " + teamList.size() + " teams from " + filename);
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        return teamList;
    }
    
    // Parse a single line from CSV
    private static Team parseTeamLine(String line) {
        String[] parts = line.split(",");
        
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid line format: needs at least 4 fields");
        }
        
        int teamNumber = Integer.parseInt(parts[0].trim());
        String teamName = parts[1].trim();
        String universityName = parts[2].trim();
        String category = parts[3].trim();
        
        // Parse scores (remaining fields)
        int[] scores = new int[parts.length - 4];
        for (int i = 4; i < parts.length; i++) {
            scores[i - 4] = Integer.parseInt(parts[i].trim());
        }
        
        // Create appropriate team type based on category
        switch (category.toLowerCase()) {
            case "cybersecurity":
                return new CybersecurityTeam(teamNumber, teamName, universityName, scores);
            case "artificial intelligence":
            case "ai":
                return new AITeam(teamNumber, teamName, universityName, scores);
            case "web development":
            case "web":
                return new WebDevTeam(teamNumber, teamName, universityName, scores);
            case "mobile development":
            case "mobile":
                return new MobileAppTeam(teamNumber, teamName, universityName, scores);
            default:
                // Default to Mobile team for unknown categories
                return new MobileAppTeam(teamNumber, teamName, universityName, scores);
        }
    }
    
    // Write report to text file
    public static boolean writeReportToFile(String filename, String report) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(report);
            System.out.println("Report successfully written to " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing report to file: " + e.getMessage());
            return false;
        }
    }
    
    // Write teams to CSV file
    public static boolean writeTeamsToCSV(String filename, TeamList teamList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("TeamNumber,TeamName,University,Category,Score1,Score2,Score3,Score4,Score5");
            
            // Write team data
            for (Team team : teamList.getAllTeams()) {
                writer.print(team.getTeamNumber() + ",");
                writer.print(team.getTeamName() + ",");
                writer.print(team.getUniversityName() + ",");
                writer.print(team.getCategory());
                
                int[] scores = team.getScoreArray();
                for (int score : scores) {
                    writer.print("," + score);
                }
                writer.println();
            }
            
            System.out.println("Teams successfully written to " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing teams to file: " + e.getMessage());
            return false;
        }
    }
    
    // Create sample CSV file if it doesn't exist
    public static void createSampleCSVFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            return; // Don't overwrite existing file
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("TeamNumber,TeamName,University,Category,Score1,Score2,Score3,Score4,Score5");
            writer.println("101,Cyber Titans,MIT,Cybersecurity,3,4,3,4,4");
            writer.println("102,AI Innovators,Stanford,Artificial Intelligence,5,4,5,4,3");
            writer.println("103,Web Warriors,Harvard,Web Development,4,3,4,5,4");
            writer.println("104,Code Breakers,Berkeley,Cybersecurity,4,4,3,5,4");
            writer.println("105,Neural Nets,CMU,Artificial Intelligence,5,5,4,4,5");
            writer.println("106,Design Masters,Yale,Web Development,3,4,4,3,5");
            writer.println("107,Security Squad,Princeton,Cybersecurity,4,5,4,4,3");
            writer.println("108,Data Wizards,Columbia,Artificial Intelligence,4,4,5,5,4");
            writer.println("109,Frontend Force,Cornell,Web Development,5,4,4,3,4");
            writer.println("110,Tech Titans,Duke,Mobile Development,4,5,5,4,4");
            writer.println("111,Hack Heroes,Northwestern,Cybersecurity,3,3,4,4,5");
            writer.println("112,Innovation Hub,Brown,Artificial Intelligence,4,5,4,5,5");
            writer.println("113,Pixel Perfect,Dartmouth,Web Development,5,5,3,4,4");
            writer.println("114,App Architects,Rice,Mobile Development,4,4,4,5,4");
            writer.println("115,Binary Blazers,Vanderbilt,Cybersecurity,5,4,4,4,5");
            
            System.out.println("Sample CSV file created: " + filename);
        } catch (IOException e) {
            System.err.println("Error creating sample file: " + e.getMessage());
        }
    }
}
