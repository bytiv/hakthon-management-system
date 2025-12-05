# Hackathon Management System

**Student:** Nikita Devi Gurumayum  
**Student ID:** SCSJ2400109

## Quick Start Guide

### STEP 1: Extract All Files
Ensure all Java files are in the same directory:

1. Team.java (abstract base class)
2. CybersecurityTeam.java
3. AITeam.java  
4. WebDevTeam.java
5. MobileAppTeam.java
6. TeamList.java
7. FileManager.java
8. HackathonModel.java
9. HackathonMainView.java
10. HackathonController.java
11. HackathonManagementSystem.java (GUI main)
12. HackathonManager.java (Console main)
13. Category.java
14. Judge.java
15. Score.java
16. TeamMember.java
17. User.java

### STEP 2: Compile All Files

**Windows:**
Open Command Prompt in the project directory and run:
```bash
javac *.java
```

**Linux/Mac:**
Open Terminal in the project directory and run:
```bash
javac *.java
```

This will compile all Java files and create .class files.

### STEP 3: Run The Application

**Option A: GUI Application (Recommended)**

Windows:
```bash
java HackathonManagementSystem
```

Linux/Mac:
```bash
java HackathonManagementSystem
```

This will launch the graphical user interface with tabs for:
- Viewing teams
- Searching teams
- Editing teams
- Viewing statistics

**Option B: Console Application (For Testing)**

Windows:
```bash
java HackathonManager
```

Linux/Mac:
```bash
java HackathonManager
```

This will launch a text-based menu interface.

### STEP 4: Using The GUI Application

**1. VIEW TEAMS TAB**
   - See all teams in a table format
   - Filter by category using dropdown
   - Sort by team number, name, or score
   - Click "Refresh" to update the display

**2. SEARCH TEAM TAB**
   - Enter a team number (e.g., 101)
   - Click "Search" or press Enter
   - View full team details

**3. EDIT TEAM TAB**
   - Enter team number and click "Load Team"
   - Modify any field (name, university, scores)
   - Scores auto-calculate overall score
   - Click "Save Changes" to update
   - Click "Delete Team" to remove

**4. STATISTICS TAB**
   - View summary statistics
   - See category breakdown
   - Check score frequency
   - Click "Generate Full Report" to save to file

**5. CLOSING THE APPLICATION**
   - Click the X button or use File menu
   - You'll be prompted to save changes
   - Report is automatically generated to HackathonReport.txt

### STEP 5: Working With CSV Files

**SAMPLE FILE CREATED AUTOMATICALLY:**
A file named "HackathonTeams.csv" will be created automatically on first run with 15 sample teams.

**CSV FORMAT:**
```
TeamNumber,TeamName,University,Category,Score1,Score2,Score3,Score4,Score5
101,Cyber Titans,MIT,Cybersecurity,3,4,3,4,4
```

**CATEGORIES SUPPORTED:**
- Cybersecurity
- Artificial Intelligence (or AI)
- Web Development (or Web)
- Mobile Development (or Mobile)

### STEP 6: Understanding Scoring Algorithms

Each team category uses a different scoring method:

**1. CYBERSECURITY TEAMS**
   - Takes the average of the TOP 4 scores
   - Example: Scores [3,4,3,4,4] → top 4 are [4,4,4,4] → average = 4.0

**2. AI TEAMS**
   - Uses weighted average with weights [1.0, 1.5, 2.0, 1.5, 1.0]
   - Middle score has highest weight
   - Example: [5,4,5,4,3] with weights → weighted average = 4.29

**3. WEB DEVELOPMENT TEAMS**
   - Average excluding highest and lowest scores
   - Example: [4,3,4,5,4] → exclude 3 and 5 → average of [4,4,4] = 4.0

**4. MOBILE DEVELOPMENT TEAMS**
   - Simple average of all scores
   - Example: [4,5,5,4,4] → average = 4.4

### STEP 7: Testing The System

**TEST SEQUENCE FOR GUI:**

1. Launch the GUI application
2. Go to "View Teams" tab - verify 15 teams are displayed
3. Try filtering by "Cybersecurity" - should show only those teams
4. Go to "Search Team" tab
5. Enter team number "101" and search
6. Go to "Edit Team" tab
7. Enter "102" and click "Load Team"
8. Change a score value and watch overall score update
9. Click "Save Changes"
10. Go back to "View Teams" and verify the change
11. Go to "Statistics" tab
12. Click "Generate Full Report"
13. Check that HackathonReport.txt was created

**TEST SEQUENCE FOR CONSOLE:**

1. Launch the console application
2. Choose option 1 to display all teams
3. Choose option 2 to search for team 101
4. Choose option 3 to see highest scoring team
5. Choose option 4 to view statistics
6. Choose option 5 to generate report
7. Choose option 6 to add a new team
8. Enter all required information
9. Choose option 1 again to verify the new team
10. Choose option 9 to save changes
11. Choose option 0 to exit

### STEP 8: Generated Files

The system creates these files:

**1. HackathonTeams.csv**
   - Contains all team data
   - Can be edited manually or through the system

**2. HackathonReport.txt**
   - Full report with all team details
   - Statistics and summaries
   - Frequency reports
   - Generated when closing GUI or using console option 5

### STEP 9: Troubleshooting

**PROBLEM:** "javac is not recognized"
**SOLUTION:** Install JDK and add it to your PATH

**PROBLEM:** Files won't compile
**SOLUTION:** Make sure all 17 Java files are in the same directory

**PROBLEM:** CSV file not found
**SOLUTION:** The system creates it automatically - just run the program

**PROBLEM:** GUI doesn't appear
**SOLUTION:** Make sure you have Java 8+ with GUI support

**PROBLEM:** Scores show as "Invalid"
**SOLUTION:** Scores must be integers between 0 and 5

**PROBLEM:** Can't save changes
**SOLUTION:** Check file permissions in the directory

### STEP 10: Advanced Usage

**ADDING NEW CATEGORIES:**
1. Create a new class extending Team
2. Implement getOverallScore() method
3. Update FileManager parseTeamLine() method
4. Add to category lists in GUI

**CUSTOMIZING SCORING:**
1. Edit the getOverallScore() method in respective team class
2. Implement your custom algorithm
3. Recompile and test

**MODIFYING GUI:**
1. Edit HackathonMainView.java
2. Add new panels or modify existing ones
3. Update controller to handle new actions

**BATCH IMPORTING:**
1. Create a CSV file following the format
2. Name it HackathonTeams.csv
3. Place in the same directory
4. Run the application - it will load automatically

---

## Assignment Compliance Checklist

- ✓ Stage 4: Team class with all required methods
- ✓ Stage 5: ArrayList, file I/O, reports, statistics
- ✓ Stage 6: Inheritance, GUI with MVC pattern
- ✓ Three-tier architecture implemented
- ✓ Multiple team categories with different scoring
- ✓ Search and filter functionality
- ✓ Edit and delete capabilities
- ✓ Report generation
- ✓ CSV import/export
- ✓ Input validation
- ✓ No drag-and-drop IDE used for GUI

## Additional Features

- ✓ User authentication framework (Admin, Judge, Participant, Public)
- ✓ Judge management system
- ✓ Score tracking with multiple criteria
- ✓ Category management
- ✓ Team member information
- ✓ Console and GUI interfaces
- ✓ Comprehensive error handling
- ✓ Auto-save on exit
- ✓ Sample data generation

## Contact & Support

For more information:
- Read README.md for detailed documentation
- Check inline comments in source code
- Review class diagrams and UML documentation

**System Version:** 1.0  
**Last Updated:** December 2024

---

*End of Quick Start Guide*
