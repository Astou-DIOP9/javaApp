import javax.swing.SwingUtilities;
import javax.swing.JFrame;

import view.AuthView;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  // Utiliser SwingUtilities.invokeLater pour assurer que l'interface graphique
        // est créée et mise à jour sur le thread de dispatch d'événements (EDT)
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JFrame frame = new JFrame("Gestion de cahier de texte"); 
            	frame.setVisible(true);
                new AuthView().setVisible(true);
            }
        });
}
}