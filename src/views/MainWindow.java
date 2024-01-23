package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import app.Game;
import model.rule.Rule;
import util.ListeningModel;
import util.Aide;
import constants.NeighborsType;
import constants.Rules;

/**
 * C'est la classe qui represente la fenetre principale du jeu
 * elle prent en parametre le model
 * @author Mamadou Alpha Diallo
 * @version 1.0
 */

public class MainWindow extends JFrame implements ListeningModel {
	private static final long serialVersionUID = 7376825297884956163L;
    Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWith = (tailleEcran.width*2/3)+150;
    int screenheight = (tailleEcran.height*2/3)+100;

	private final Menu menu;
    private final Config zoneConfiguration;
    private final Rendu zoneRendu;
	private  GridGraphique grid;

	private Game game;

	public  MainWindow(Game game)  throws UnsupportedLookAndFeelException{
		super("Game of Life");
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		JPanel contentPane= (JPanel)this.getContentPane();

		this.menu = new Menu();
		this.zoneRendu = new Rendu();
		this.zoneConfiguration = new Config();
		this.game = game;
		this.grid = new GridGraphique(this.game);
		contentPane.add(menu, BorderLayout.NORTH);
		contentPane.add(createPage(),BorderLayout.CENTER);
		//events

		this.eventNavigation();
		this.zoneConfiguration.listRules.addActionListener(this::choiceRule);
		this.zoneConfiguration.listVoisins.addActionListener(this::choiceNeighborsType);
		this.menu.patterns.addActionListener(this::choicePattern);

		this.setSize(screenWith,screenheight);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.game.addListening(this);
	}
	private JPanel createPage(){
		JPanel page = new JPanel(new BorderLayout(50, 30));
		page.setPreferredSize(new Dimension(300,200));
		this.zoneRendu.rendu.add(this.grid,BorderLayout.CENTER);
		page.add(this.zoneConfiguration,BorderLayout.WEST);
		page.add(this.zoneRendu.getRenduPanel(),BorderLayout.CENTER);
		//creation de panel vide pour rejoudre le probleme des margin
		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1,0));
		page.add(south,BorderLayout.SOUTH);
		return page;
	}

	/**
	 * cette fonction permet de choisir la régle de jeu
	 */
	public void choiceRule(ActionEvent e) {
		if(this.zoneConfiguration.listRules.getSelectedIndex()==1){
			this.zoneConfiguration.txt.setPreferredSize(new Dimension(100,30));
			this.zoneConfiguration.panelRule.setVisible(false);
			this.zoneConfiguration.rulesZone.setText("");
			this.zoneConfiguration.txt.addActionListener( event -> {
				this.zoneConfiguration.rulesZone.setText(this.zoneConfiguration.txt.getText());
				this.zoneConfiguration.panelRule.remove(this.zoneConfiguration.txt);
				this.zoneConfiguration.panelRule.setVisible(false);
				this.zoneConfiguration.panelRule.setVisible(true);

				if(!this.game.useCustomRule(this.zoneConfiguration.txt.getText())) {
					this.zoneConfiguration.listRules.setSelectedItem("game of life");
				}
			});
			
			this.zoneConfiguration.panelRule.add(this.zoneConfiguration.txt);
		}
		else{
			this.zoneConfiguration.panelRule.remove(this.zoneConfiguration.txt);
			this.zoneConfiguration.panelRule.setVisible(false);
			this.zoneConfiguration.rulesZone.setText(Rules.GAMEOFLIFE);
			this.game.useGolRule();
		}
		this.zoneConfiguration.panelRule.revalidate();
		this.zoneConfiguration.panelRule.repaint();
		this.zoneConfiguration.panelRule.setVisible(false);
		this.zoneConfiguration.panelRule.setVisible(true);
	}

	/**
	 * cette fonction permet de choisir le type de voisin
	 */
	public void choiceNeighborsType(ActionEvent e) {
		String selectedType = (String) this.zoneConfiguration.listVoisins.getSelectedItem();
		if(selectedType.toUpperCase() != "AUTRE") {
			this.zoneConfiguration.panelVoisins.remove(this.zoneConfiguration.txt2);
			this.zoneConfiguration.panelVoisins.setVisible(false);
			this.zoneConfiguration.panelVoisins.setVisible(true);

			// show type to user
			this.zoneConfiguration.voisinsZone.setText(NeighborsType.coordRepresentation(selectedType));

			// use type in model
			game.changeNeighborsType(selectedType);

		} else {
			this.zoneConfiguration.txt2.setPreferredSize(new Dimension(200,30));
			this.zoneConfiguration.panelVoisins.setVisible(false);
			this.zoneConfiguration.voisinsZone.setText("");
			this.zoneConfiguration.txt2.addActionListener( event -> {
				this.zoneConfiguration.voisinsZone.setText(this.zoneConfiguration.txt2.getText());
				this.zoneConfiguration.panelVoisins.remove(this.zoneConfiguration.txt2);
				this.zoneConfiguration.panelVoisins.setVisible(false);
				this.zoneConfiguration.panelVoisins.setVisible(true);

				String ctype = this.zoneConfiguration.txt2.getText();

				// if not valid, gol is used by default
				if(!game.changeNeighborsTypeCustom(ctype)) {
					this.zoneConfiguration.listVoisins.setSelectedItem("GAMEOFLIFE");
					this.zoneConfiguration.voisinsZone.setText(NeighborsType.coordRepresentation("GAMEOFLIFE"));
				}
			});

			this.zoneConfiguration.panelVoisins.add(this.zoneConfiguration.txt2);

			
		}
		
		this.zoneConfiguration.panelVoisins.revalidate();
		this.zoneConfiguration.panelVoisins.repaint();
		this.zoneConfiguration.panelVoisins.setVisible(false);
		this.zoneConfiguration.panelVoisins.setVisible(true);
		
	}

	/**
	 * cette fonction permet gerer les evenements sur les boutons
	 */
	public void eventNavigation() {

		//events pour le bouton play
				this.zoneRendu.play.addActionListener(event -> {
					if(this.zoneRendu.play.isSelected()){
						this.zoneRendu.play.setText("stop");
						if(this.zoneRendu.classic.isSelected()){
							this.game.useClassicAlgo();
						}else{
							this.game.useHashlifeAlgo();
						}
							this.game.runGenThread();
					}else{
						this.game.stopGenThread();
						this.zoneRendu.play.setText("play");
					}
				});
		//events pour le bouton next
				this.zoneRendu.next.addActionListener(event -> {
					if(this.zoneRendu.classic.isSelected()){
						this.game.nextGenerationClassic();
					}else {
						this.game.nextGenerationHashlife();
					}
				});
		//events pour le bouton back
				this.zoneRendu.back.addActionListener(event -> {

					if(this.game.previousGeneration()){
						this.game.previousGeneration();
					}
				});
		//events pour le bouton avance rapide
				this.zoneRendu.rapide.addActionListener(event -> {
					this.game.increaseSpeed();
				});
		//events pour le bouton recule rapide
				this.zoneRendu.recule.addActionListener(event -> {
					this.game.decreaseSpeed();
				});
		//events pour le bouton debuter
				this.zoneRendu.start.addActionListener(event -> {
					this.game.resetGrid();
					this.zoneRendu.play.setSelected(false);
				});
		//events pour le bouton random
		this.zoneConfiguration.initRandom.addActionListener(event -> {
			this.game.useRandom();
		});
		//events pour le bouton aide
		this.menu.help.addActionListener((event) -> new Aide());
	}

	/**
	 * cette fonction permet de choisir le pattern
	 * @param e
	 */
	public void choicePattern(ActionEvent e) {
		String pattern ="patterns/"+ (String)this.menu.patterns.getSelectedItem();
			this.game.usePattern(pattern);
	}

	/**
	 * cette fonction permet de mettre a jour les etats des labels
	 */
	public void updateStats() {
		// udpate speed
		this.zoneConfiguration.speedZone.setText(game.getLastGenComputeTimeInNnos() + " ns");

		// update iteration
		this.zoneConfiguration.iterationZone.setText(game.getIteration() + "");

		// update population
		this.zoneConfiguration.generationZone.setText(game.getGrid().getPopulation() + "");
	}

	@Override
	public void modeleMIsAJour(Object source, Object notification) {
		this.grid.repaint();
		this.updateStats();
	}

}
