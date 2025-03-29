package BaseDeDonne;
import databaseConnector.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;


public class supprimerUtilisateur {
	
	public supprimerUtilisateur(int id) {
	    String sql = "DELETE FROM Utilisateur WHERE id = ?";
	    try {
	      
			PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, id);
	        
	        int rowsDeleted = stmt.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("Utilisateur supprimé !");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
