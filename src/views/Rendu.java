package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/**
 * cette classe permet de contenir la grille graphique et les boutons de navigation
 * @author Mamadou Alpha Diallo
 * @version 1.0
 */
public class Rendu extends JPanel {

	private static final long serialVersionUID = 1L;

	public JPanel rendu = new JPanel(new BorderLayout());
	public JPanel nav=new JPanel(new FlowLayout());
	public JButton next,back,rapide,recule,start;
	public JToggleButton play;
	public JRadioButton classic,hashlif;


	public Rendu(){
		this.setLayout(new BorderLayout());
		//boutton de navigation
		this.play=new JToggleButton("play");
		this.next= new JButton("next");
		this.back= new JButton("back");
		this.rapide= new JButton("fast");
		this.recule= new JButton("slow");
		this.start= new JButton("reset");
		//button radio
		this.classic=new JRadioButton("Classic",true);
		this.hashlif=new JRadioButton("Hashlife");
		//create button group
		ButtonGroup bGroup=new ButtonGroup();
		bGroup.add(this.classic);
		bGroup.add(this.hashlif);
		//panel choix de l'algo
		JPanel algoPanel=new JPanel();
		algoPanel.add(this.classic);
		algoPanel.add(this.hashlif);
		//creation de panel nav
		this.nav.setPreferredSize(new Dimension(100,40));
		nav.add(this.play);nav.add(this.next);nav.add(this.back);nav.add(this.rapide);nav.add(this.recule);nav.add(this.start);
		this.nav.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		//creation de panel west pour le marging
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(20,0));

		this.rendu.setPreferredSize(new Dimension(600,600));
		this.rendu.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.rendu.add(algoPanel,BorderLayout.NORTH);
		this.rendu.add(west,BorderLayout.WEST);

		JScrollPane jsc1=new JScrollPane(this.rendu);
		JScrollPane jsc2=new JScrollPane(this.nav);
		
		this.add(jsc2,BorderLayout.NORTH);
		this.add(jsc1,BorderLayout.CENTER);
	}
	JScrollPane getRenduPanel(){
		return new JScrollPane(this);
	}

}
