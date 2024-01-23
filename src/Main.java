
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import views.MainWindow;
import model.Grid;
import app.Generator;
import app.Game;


public class Main {

	public static void main(String[] args) {
		// Creating the game object
		Grid grid = new Grid(24, 24);
		Generator generator = new Generator();
		Game game = new Game(grid, generator);

		
		// Launching GUI
		try {
			new MainWindow(game);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
