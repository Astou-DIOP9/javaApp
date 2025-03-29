
package dao;

import models.FichePedagogique;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FichePedagogiqueDAO {
    private Connection connexion;

    public FichePedagogiqueDAO() {
        this.connexion = ConnexionBD.getConnexion();
    }

    public boolean create(FichePedagogique fiche) {
        if (fiche == null || fiche.getCoursId() <= 0) {
            throw new IllegalArgumentException("Paramètres de la fiche invalides");
        }

        String sql = "INSERT INTO fiche_pedagogique (cours_id, date_generation) VALUES (?, ?)";
        
        try (PreparedStatement statement = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, fiche.getCoursId());
            statement.setDate(2, fiche.getDateGeneration());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de la fiche, aucune ligne affectée");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fiche.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Échec de la création de la fiche, aucun ID obtenu");
                }
            }
            
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la création de la fiche pédagogique: " + e.getMessage());
            return false;
        }
    }

    public List<FichePedagogique> getByCoursId(int coursId) {
        if (coursId <= 0) {
            throw new IllegalArgumentException("L'ID du cours doit être positif");
        }

        List<FichePedagogique> fiches = new ArrayList<>();
        String sql = "SELECT * FROM fiche_pedagogique WHERE cours_id = ?";
        
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, coursId);
            
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    FichePedagogique fiche = new FichePedagogique();
                    fiche.setId(result.getInt("id"));
                    fiche.setCoursId(result.getInt("cours_id"));
                    fiche.setDateGeneration(result.getDate("date_generation"));
                    fiche.setCheminFichier(result.getString("chemin_fichier"));
                    fiche.setFormat(result.getString("format"));
                    
                    fiches.add(fiche);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la récupération des fiches: " + e.getMessage());
        }
        
        return fiches;
    }

    // ... autres méthodes avec vérifications ...
}