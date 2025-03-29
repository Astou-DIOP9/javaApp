import java.sql.Connection;

import BaseDeDonne.ajouterENSEIGNANT;
import databaseConnector.DatabaseConnection;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		        Connection conn = DatabaseConnection.getConnection();
		        if (conn != null) {
		            System.out.println("Connexion à la base de données MySQL réussie !");
		        }
		        ajouterENSEIGNANT astou = new ajouterENSEIGNANT();
		        astou.ajouterUtilisateur("Alice", "alice@example.com", "password1234", "enseignant");

	}

}
