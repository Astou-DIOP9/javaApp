package view;

import models.Utilisateur;
import models.Cours;
import models.FichePedagogique;
import dao.UtilisateurDAO;
import dao.CoursDAO;
import dao.FichePedagogiqueDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ChefView extends JFrame {
    private Utilisateur utilisateur;
    private UtilisateurDAO utilisateurDAO;
    private CoursDAO coursDAO;
    private FichePedagogiqueDAO fichePedagogiqueDAO;
    
    public ChefView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.utilisateurDAO = new UtilisateurDAO();
        this.coursDAO = new CoursDAO();
        this.fichePedagogiqueDAO = new FichePedagogiqueDAO();
        initUI();
    }
    
    private void initUI() {
        setTitle("Espace Chef de Département - " + utilisateur.getNom());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Onglet 1: Gestion des enseignants
        tabbedPane.addTab("Enseignants", createEnseignantsPanel());
        
        // Onglet 2: Gestion des responsables
        tabbedPane.addTab("Responsables", createResponsablesPanel());
        
        // Onglet 3: Assignation des cours
        tabbedPane.addTab("Assignation Cours", createAssignationPanel());
        
        // Onglet 4: Fiches pédagogiques
        tabbedPane.addTab("Fiches Pédagogiques", createFichesPedagogiquesPanel());
        
        add(tabbedPane);
    }
    
    private JPanel createEnseignantsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Liste des enseignants
        List<Utilisateur> enseignants = utilisateurDAO.getByRole("enseignant");
        DefaultListModel<Utilisateur> model = new DefaultListModel<>();
        enseignants.forEach(model::addElement);
        
        JList<Utilisateur> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        
        // Boutons
        JPanel buttonPanel = new JPanel();
        
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> showAddUserDialog(model, "enseignant", "Nouvel enseignant"));
        
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> {
            Utilisateur selected = list.getSelectedValue();
            if (selected != null) {
                deleteUser(selected, model);
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createResponsablesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Liste des responsables
        List<Utilisateur> responsables = utilisateurDAO.getByRole("responsable");
        DefaultListModel<Utilisateur> model = new DefaultListModel<>();
        responsables.forEach(model::addElement);
        
        JList<Utilisateur> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        
        // Boutons
        JPanel buttonPanel = new JPanel();
        
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> showAddUserDialog(model, "responsable", "Nouveau responsable"));
        
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createAssignationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Liste des enseignants
        List<Utilisateur> enseignants = utilisateurDAO.getByRole("enseignant");
        JComboBox<Utilisateur> enseignantCombo = new JComboBox<>(enseignants.toArray(new Utilisateur[0]));
        
        // Liste des cours
        List<Cours> cours = coursDAO.getAll();
        JComboBox<Cours> coursCombo = new JComboBox<>(cours.toArray(new Cours[0]));
        
        // Panel de formulaire
        JPanel formPanel = new JPanel(new GridLayout(4, 1));
        formPanel.add(new JLabel("Enseignant:"));
        formPanel.add(enseignantCombo);
        formPanel.add(new JLabel("Cours à assigner:"));
        formPanel.add(coursCombo);
        
        // Bouton d'assignation
        JButton assignButton = new JButton("Assigner");
        assignButton.addActionListener(e -> {
            Utilisateur enseignant = (Utilisateur) enseignantCombo.getSelectedItem();
            Cours coursSelectionne = (Cours) coursCombo.getSelectedItem();
            
            if (enseignant == null || coursSelectionne == null) {
                JOptionPane.showMessageDialog(this, 
                    "Veuillez sélectionner un enseignant et un cours", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (coursDAO.assigner(enseignant.getId(), coursSelectionne.getId())) {
                JOptionPane.showMessageDialog(this, 
                    "Cours assigné avec succès à " + enseignant.getNom());
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de l'assignation", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(assignButton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createFichesPedagogiquesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Liste des cours pour génération de fiches
        List<Cours> cours = coursDAO.getAll();
        DefaultListModel<Cours> model = new DefaultListModel<>();
        cours.forEach(model::addElement);
        
        JList<Cours> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        
        JButton generateButton = new JButton("Générer fiche");
        generateButton.addActionListener(e -> {
            Cours selected = list.getSelectedValue();
            if (selected != null) {
                generateFichePedagogique(selected);
            }
        });
        
        JButton viewButton = new JButton("Voir fiches");
        viewButton.addActionListener(e -> {
            Cours selected = list.getSelectedValue();
            if (selected != null) {
                viewFichesPedagogiques(selected);
            }
        });
        
        buttonPanel.add(generateButton);
        buttonPanel.add(viewButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Méthodes utilitaires
    private void showAddUserDialog(DefaultListModel<Utilisateur> model, String role, String title) {
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(passwordField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, title, 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || 
                emailField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Tous les champs doivent être remplis", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Utilisateur nouvelUtilisateur = new Utilisateur();
            nouvelUtilisateur.setNom(nomField.getText());
            nouvelUtilisateur.setPrenom(prenomField.getText());
            nouvelUtilisateur.setEmail(emailField.getText());
            nouvelUtilisateur.setPassword(new String(passwordField.getPassword()));
            nouvelUtilisateur.setRole(role);
            
            if (utilisateurDAO.create(nouvelUtilisateur)) {
                model.addElement(nouvelUtilisateur);
                JOptionPane.showMessageDialog(this, 
                    role.equals("enseignant") ? "Enseignant ajouté" : "Responsable ajouté");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de l'ajout", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteUser(Utilisateur user, DefaultListModel<Utilisateur> model) {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer " + user.getNom() + "?", 
            "Confirmation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (utilisateurDAO.delete(user.getId())) {
                model.removeElement(user);
                JOptionPane.showMessageDialog(this, "Utilisateur supprimé");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la suppression", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void generateFichePedagogique(Cours cours) {
        FichePedagogique fiche = new FichePedagogique();
        fiche.setCoursId(cours.getId());
        fiche.setDateGeneration(new java.sql.Date(System.currentTimeMillis()));
        
        if (fichePedagogiqueDAO.create(fiche)) {
            JOptionPane.showMessageDialog(this, 
                "Fiche pédagogique générée pour: " + cours.getNom(),
                "Succès", JOptionPane.INFORMATION_MESSAGE);
            
            // Ici vous pourriez ajouter l'export PDF/Excel
            // exportFicheToPDF(fiche);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la génération", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewFichesPedagogiques(Cours cours) {
        List<FichePedagogique> fiches = fichePedagogiqueDAO.getByCoursId(cours.getId());
        
        if (fiches.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Aucune fiche pédagogique pour ce cours", 
                "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        DefaultListModel<FichePedagogique> model = new DefaultListModel<>();
        fiches.forEach(model::addElement);
        
        JList<FichePedagogique> list = new JList<>(model);
        JOptionPane.showMessageDialog(this, 
            new JScrollPane(list), 
            "Fiches pédagogiques pour " + cours.getNom(), 
            JOptionPane.PLAIN_MESSAGE);
    }
}