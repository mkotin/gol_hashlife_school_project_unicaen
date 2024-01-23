package views;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.JLabel;

/**
cette classe permet de gerer le Menu
@author Mamadou Alpha Diallo
@version 1.0
*/
public class Menu extends JToolBar {
	private static final long serialVersionUID = 1L;
	public JButton help;
	protected JComboBox<String> patterns;
	public Menu() {
		//partie pattern
		JLabel menu=new JLabel("Patterns:");
		String[] choix={"Burst.txt","Coeur.txt","Delta.txt","Lettre_L.txt","Sawfish.txt"};
		this.patterns=new JComboBox<>(choix);
		//create button aide
		this.help=new JButton("Help");
		this.help.setPreferredSize(new Dimension(110,30));
		//add in JToolBar
		this.add(menu);
		this.add(this.patterns);
		this.add(help);
	}
}
