package BaseDeDonne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import databaseConnector.DatabaseConnection;

public class ajouterENSEIGNANT {
	

	    private Connection conn = DatabaseConnection.getConnection();

	    public void ajouterUtilisateur(String nom, String email, String motDePasse, String role) {
	        String sql = "INSERT INTO Utilisateur (nom, email, motDePasse, role) VALUES (?, ?, ?, ?)";
	        try {
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, nom);
	            stmt.setString(2, email);
	            stmt.setString(3, motDePasse);
	            stmt.setString(4, role);

	            int rowsInserted = stmt.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("Utilisateur ajouté avec succès !");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	} 



