package BaseDeDonne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Assignement {
	
	    private Connection connection;

	    // Constructeur pour initialiser la connexion à la base de données
	    public Assignement(Connection connection) {
			super();
			this.connection = connection;
		}
         
	    // Méthode pour assigner un cours à un enseignant
	    public boolean assignCourseToTeacher(int teacherId, int courseId) {
	        String query = "INSERT INTO assignations (teacher_id, course_id) VALUES (?, ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, teacherId);
	            preparedStatement.setInt(2, courseId);
	            int rowsInserted = preparedStatement.executeUpdate();

	            return rowsInserted > 0; // Retourne vrai si l'insertion a réussi
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de l'assignation : " + e.getMessage());
	            return false; // En cas d'échec
	        }
	    }

		

	
	}
