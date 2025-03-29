package view;

import models.Utilisateur;
import dao.UtilisateurDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private UtilisateurDAO utilisateurDAO;
    
    public AuthView() {
        utilisateurDAO = new UtilisateurDAO();
        initUI();
    }
    
    private void initUI() {
        setTitle("Authentification - Cahier de texte");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Titre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Connexion au Cahier de Texte", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, gbc);
        
        // Email
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        // Mot de passe
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mot de passe:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);
        
        // Bouton de connexion
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Se connecter");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                
                Utilisateur utilisateur = utilisateurDAO.authentifier(email, password);
                
                if (utilisateur != null) {
                    JOptionPane.showMessageDialog(AuthView.this, 
                        "Bienvenue " + utilisateur.getPrenom() + " " + utilisateur.getNom(), 
                        "Connexion réussie", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Rediriger vers la vue appropriée selon le rôle
                    switch (utilisateur.getRole()) {
                        case "chef":
                            new ChefView(utilisateur).setVisible(true);
                            break;
                        case "enseignant":
                            new EnseignantView(utilisateur).setVisible(true);
                            break;
                        case "responsable":
                            new ResponsableView(utilisateur).setVisible(true);
                            break;
                    }
                    
                    dispose(); // Fermer la fenêtre d'authentification
                } else {
                    JOptionPane.showMessageDialog(AuthView.this, 
                        "Email ou mot de passe incorrect", 
                        "Erreur d'authentification", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(loginButton, gbc);
        
        add(panel);
    }
}