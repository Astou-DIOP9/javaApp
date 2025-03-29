package BaseDeDonne;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import databaseConnector.DatabaseConnection;

public class updateUtlilsateur {
	public void updateUtilisateur(int id, String nom, String email) {
	    String sql = "UPDATE Utilisateur SET nom = ?, email = ? WHERE id = ?";
	    try {
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, nom);
	        stmt.setString(2, email);
	        stmt.setInt(3, id);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Utilisateur mis à jour !");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
