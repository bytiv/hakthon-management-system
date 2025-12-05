// Cybersecurity Team - calculates score by averaging top 4 scores
class CybersecurityTeam extends Team {
    
    public CybersecurityTeam(int teamNumber, String teamName, String universityName, int[] scores) {
        super(teamNumber, teamName, universityName, "Cybersecurity", scores);
    }
    
    @Override
    public double getOverallScore() {
        // Sort scores in descending order and average top 4
        int[] sortedScores = scores.clone();
        java.util.Arrays.sort(sortedScores);
        
        int n = Math.min(4, sortedScores.length);
        double sum = 0;
        for (int i = sortedScores.length - 1; i >= sortedScores.length - n; i--) {
            sum += sortedScores[i];
        }
        return sum / n;
    }
}

// AI Team - uses weighted average (weights: 1, 1.5, 2, 1.5, 1)
class AITeam extends Team {
    
    public AITeam(int teamNumber, String teamName, String universityName, int[] scores) {
        super(teamNumber, teamName, universityName, "Artificial Intelligence", scores);
    }
    
    @Override
    public double getOverallScore() {
        double[] weights = {1.0, 1.5, 2.0, 1.5, 1.0};
        double weightedSum = 0;
        double totalWeight = 0;
        
        for (int i = 0; i < scores.length && i < weights.length; i++) {
            weightedSum += scores[i] * weights[i];
            totalWeight += weights[i];
        }
        
        return totalWeight > 0 ? weightedSum / totalWeight : 0;
    }
}

// Web Development Team - average excluding highest and lowest scores
class WebDevTeam extends Team {
    
    public WebDevTeam(int teamNumber, String teamName, String universityName, int[] scores) {
        super(teamNumber, teamName, universityName, "Web Development", scores);
    }
    
    @Override
    public double getOverallScore() {
        if (scores.length <= 2) {
            // If only 2 or fewer scores, just average them
            double sum = 0;
            for (int score : scores) {
                sum += score;
            }
            return scores.length > 0 ? sum / scores.length : 0;
        }
        
        // Find min and max
        int min = scores[0];
        int max = scores[0];
        double sum = 0;
        
        for (int score : scores) {
            sum += score;
            if (score < min) min = score;
            if (score > max) max = score;
        }
        
        // Exclude min and max
        sum = sum - min - max;
        return sum / (scores.length - 2);
    }
}

// Mobile App Team - simple average
class MobileAppTeam extends Team {
    
    public MobileAppTeam(int teamNumber, String teamName, String universityName, int[] scores) {
        super(teamNumber, teamName, universityName, "Mobile Development", scores);
    }
    
    @Override
    public double getOverallScore() {
        if (scores.length == 0) return 0;
        
        double sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return sum / scores.length;
    }
}
