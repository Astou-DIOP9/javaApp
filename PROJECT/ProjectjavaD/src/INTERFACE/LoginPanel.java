package INTERFACE;
import javax.swing.*;
import java.awt.*;
public class LoginPanel extends JPanel {
	public LoginPanel() {
		//initialiser des composant
		setLayout(new GridLayout(3,2));
		JLabel userLabel = new JLabel("Nom d'utilisateur :");
        JTextField userField = new JTextField();
        JLabel passwordLabel = new JLabel("Mot de passe :");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Connexion");
        loginButton.setPreferredSize(new Dimension(80, 30));

        // Ajout des composants au panneau
        add(userLabel);
        add(userField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Espace vide
        add(loginButton);
    }
	}


