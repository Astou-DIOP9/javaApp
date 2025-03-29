package view;

import models.Utilisateur;
import models.Seance;
import dao.SeanceDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResponsableView extends JFrame {
    private Utilisateur utilisateur;
    private SeanceDAO seanceDAO;
    
    public ResponsableView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        seanceDAO = new SeanceDAO();
        initUI();
    }
    
    private void initUI() {
        setTitle("Espace Responsable de Classe - " + utilisateur.getNom());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Liste des séances à valider
        List<Seance> seances = seanceDAO.getSeancesAValider();
        DefaultListModel<Seance> seancesModel = new DefaultListModel<>();
        for (Seance seance : seances) {
            seancesModel.addElement(seance);
        }
        JList<Seance> seancesList = new JList<>(seancesModel);
        
        // Bouton de validation
        JButton validerButton = new JButton("Valider la séance sélectionnée");
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Seance selectedSeance = seancesList.getSelectedValue();
                if (selectedSeance == null) {
                    JOptionPane.showMessageDialog(ResponsableView.this, 
                        "Veuillez sélectionner une séance à valider", 
                        "Aucune séance sélectionnée", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (seanceDAO.validerSeance(selectedSeance.getId(), utilisateur.getId())) {
                    JOptionPane.showMessageDialog(ResponsableView.this, 
                        "Séance validée avec succès", 
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                    // Mettre à jour la liste
                    seancesModel.removeElement(selectedSeance);
                } else {
                    JOptionPane.showMessageDialog(ResponsableView.this, 
                        "Erreur lors de la validation", 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(seancesList), BorderLayout.CENTER);
        mainPanel.add(validerButton, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
}