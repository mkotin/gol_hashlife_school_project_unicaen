package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;

import app.Game;
import constants.NeighborsType;

/**
 * cette classe permet de contenir les configuration du jeu
 * @author Mamadou Alpha Diallo
 * @version 1.0
 */

public class Config extends JPanel{
    private static final long serialVersionUID = 1L;
    protected JPanel config,panelRule,panelVoisins;
    protected JComboBox<String> listRules,listVoisins;
    protected JLabel iterationZone,generationZone,rulesZone,voisinsZone,speedZone;
    protected JTextField txt,txt2;
    protected JButton initRandom;

    public Config() {
    //init attributs
        this.config=new JPanel();
        this.rulesZone=new JLabel();
        this.rulesZone.setPreferredSize(new Dimension(169,30));
        this.rulesZone.setBorder(BorderFactory.createLoweredBevelBorder());
        this.iterationZone=new JLabel();
        this.iterationZone.setPreferredSize(new Dimension(100,30));
        this.iterationZone.setBorder(BorderFactory.createLoweredBevelBorder());
        this.generationZone=new JLabel();
        this.generationZone.setPreferredSize(new Dimension(100,30));
        this.generationZone.setBorder(BorderFactory.createLoweredBevelBorder());
        this.voisinsZone=new JLabel();
        this.voisinsZone.setPreferredSize(new Dimension(169,30));
        this.voisinsZone.setBorder(BorderFactory.createLoweredBevelBorder());
        this.speedZone=new JLabel();
        this.speedZone.setPreferredSize(new Dimension(100,30));
        this.speedZone.setBorder(BorderFactory.createLoweredBevelBorder());
        this.txt=new JTextField("B3/S23");
        this.txt2=new JTextField("(1,1);(0,0);(1,0);(0,1)");
        this.initRandom=new JButton("Init random");

        //zone des labels de configuration
        JLabel vitesse=new JLabel("Speed:");
        vitesse.setPreferredSize(new Dimension(130,20));
        JLabel iterationlabel=new JLabel("Iteration:");
        iterationlabel.setPreferredSize(new Dimension(90,20));
        JLabel populationLabel=new JLabel("Population:");
        populationLabel.setPreferredSize(new Dimension(90,20));

        //creation des regles
        JLabel rulLabel=new JLabel("Rules:");
        rulLabel.setPreferredSize(new Dimension(110,20));
        JToolBar rules=new JToolBar();
        String[] rulesExisted= {"game of life","autre"};
        this.listRules=new JComboBox<>(rulesExisted);
        this.listRules.setPreferredSize(new Dimension(100,30));
        rules.add(rulLabel);
        rules.add(this.listRules);

        //creation de type de voisinage
        JLabel voisinsLabel=new JLabel("Neighbors type:");
        JToolBar voisins=new JToolBar();
        voisinsLabel.setPreferredSize(new Dimension(110,20));
        String[] voisinsType= {"GAMEOFLIFE","TYPE2","TYPE3","AUTRE"};
        this.listVoisins=new JComboBox<>(voisinsType);
        this.listVoisins.setPreferredSize(new Dimension(100,30));
        voisins.add(voisinsLabel);
        voisins.add(this.listVoisins);

        //vitesse panel
        JPanel panelVitesse=new JPanel();
        panelVitesse.add(vitesse);
        panelVitesse.add(speedZone);

        //iteration panel
        JPanel panelIteration=new JPanel();
        panelIteration.add(iterationlabel);
        panelIteration.add(iterationZone);

        //population panel
        JPanel panelPopulation =new JPanel();
        panelPopulation.add(populationLabel);
        panelPopulation.add(generationZone);

        //rule panel
        panelRule= new JPanel();
        panelRule.setLayout(new FlowLayout());
        panelRule.setPreferredSize(new Dimension(400,70));
        panelRule.add(rules);
        panelRule.add(this.rulesZone);

        //voisins panel
        panelVoisins=new JPanel();
        panelVoisins.setLayout(new FlowLayout());
        panelVoisins.setPreferredSize(new Dimension(400,70));
        panelVoisins.add(voisins);
        panelVoisins.add(this.voisinsZone);

        //button init random
        JPanel randomPanel=new JPanel();
        randomPanel.setPreferredSize(new Dimension(400,50));
        randomPanel.add(initRandom);

        //panel principal
        this.config.setPreferredSize(new Dimension(490,300));
        this.config.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        this.config.add(panelIteration);
        this.config.add(panelPopulation);
        this.config.add(panelRule);
        this.config.add(panelVoisins);
        this.config.add(panelVitesse);
        this.config.add(randomPanel);

        JScrollPane jscp=new JScrollPane(this.config);
        this.add(jscp);
    }

    
}