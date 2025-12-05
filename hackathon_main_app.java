import javax.swing.*;

// Main Application Class
public class HackathonManagementSystem {
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create MVC components
        SwingUtilities.invokeLater(() -> {
            // Create model
            HackathonModel model = new HackathonModel();
            
            // Create controller
            HackathonController controller = new HackathonController(model);
            
            // Create view
            HackathonMainView view = new HackathonMainView(controller);
            
            // Set view in controller
            controller.setView(view);
            
            // Initialize application
            controller.initialize();
            
            // Show GUI
            view.setVisible(true);
        });
    }
}
