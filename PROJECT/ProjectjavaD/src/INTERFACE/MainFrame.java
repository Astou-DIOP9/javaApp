package INTERFACE;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	 public MainFrame() {
	        // Configuration de la fenêtre principale
	        setTitle("Gestion du Cahier de Texte");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(330, 150);
	        setBackground(Color.pink);
	        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran

	        // Ajouter LoginPanel à la fenêtre
	        LoginPanel loginPanel = new LoginPanel();
	        add(loginPanel);

	        // Rendre la fenêtre visible
	        setVisible(true);
	    }

	 public static void main(String[] args) {
	        // Exécuter l'application
	        SwingUtilities.invokeLater(() -> {
	            new MainFrame();});
	 }
}
