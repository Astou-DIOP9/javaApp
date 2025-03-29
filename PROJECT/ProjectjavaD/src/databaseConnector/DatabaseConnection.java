package databaseConnector;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class DatabaseConnection {
	    private static final String URL = "jdbc:mysql://localhost:3306/gestion_cahier"; 
	    private static final String USER = "root";  
	    private static final String PASSWORD = "";  // Mot de passe XAMPP (par défaut vide)
	    private static Connection connection;

	    public static Connection getConnection() {
	        if (connection == null) {
	            try {
	                // Charger le driver JDBC
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                // Établir la connexion
	                
	                connection = DriverManager.getConnection(URL, USER, PASSWORD);
	                System.out.println("Connexion réussie à MySQL avec XAMPP !");
	            } catch (ClassNotFoundException e) {
	                System.out.println("Driver JDBC non trouvé !");
	                e.printStackTrace();
	            } catch (SQLException e) {
	                System.out.println("Erreur de connexion !");
	                e.printStackTrace();
	            }
	        }
	        return connection;
	    }
	}

