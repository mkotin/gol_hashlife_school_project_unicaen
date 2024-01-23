package tests;
/**
 * La classe TestGenerator contient les tests unitaires pour la classe Generator
 */

import org.junit.Test;

import app.Generator;

import static org.junit.Assert.*;

import model.*;
import model.rule.*;

public class TestGenerator {
	@Test
	public void testConstructeurArgument() {
		//creer une instance de Generator
		Rule rule = new Rule("B4/S2-3");
		Generator generator = new Generator(rule);
		//verifier que l'attribut neighbors de Generator est bien initialisé
		assertNotNull("lattribut neighbors ne doit pas être vide",generator.getNeighbors());
		assertEquals("le nbre de neighbors doit être égale à 8",8, generator.getNeighbors().length);
		
		
	}
	
	@Test
	public void testConstructeurSansArgument() {
		//creer une instance de Generator
		Generator generator = new Generator();
		Rule rule = generator.getRule();
		//verifier que generateur est initialisé avec les règles de game of life
		RuleMulttF ruleMult1 = new RuleMulttF("3");
		RuleMulttF ruleMult2 =new RuleMulttF("23");
		assertEquals("la règle de naissance doit être celle de game of life",ruleMult1 ,rule.getBornRule());
		assertEquals("la règle de survie doit être celle de game of life",ruleMult2  ,rule.getSurviveRule());
	}
	
	@Test
	public void testcountAliveNeighbors() {
		Grid grid = new Grid(3,3);
		Generator generator = new Generator();
		//definir  l'etat des cellules de la grille
		  	grid.getBoard()[0][0].setEtat(1);
	        grid.getBoard()[0][1].setEtat(1);
	        grid.getBoard()[0][2].setEtat(0);
	        grid.getBoard()[1][0].setEtat(1);
	        grid.getBoard()[1][1].setEtat(0);
	        grid.getBoard()[1][2].setEtat(1);
	        grid.getBoard()[2][0].setEtat(0);
	        grid.getBoard()[2][1].setEtat(1);
	        grid.getBoard()[2][2].setEtat(0);
	        //verifier le nombre de voisin selon les règles du jeu de la vie [[-1, -1], [-1, 0], [-1, 1], [0, -1], [0, 1], [1, -1], [1, 0], [1, 1]]
	        
	        assertEquals("le nombre de voisin pour la cellule à la position 0 1 doit etre 3",3, generator.countAliveNeighbors(0, 1, grid));
	        assertEquals("le nombre de voisin pour la cellule à la position 1 0 doit etre 3",3, generator.countAliveNeighbors(1, 0, grid));
	        assertEquals("le nombre de voisin pour la cellule à la position 1 2 doit etre 2",2, generator.countAliveNeighbors(1, 2, grid));
	        assertEquals("le nombre de voisin pour la cellule à la position 2 1 doit etre 2",2, generator.countAliveNeighbors(2, 1, grid));
	        assertEquals("le nombre de voisin pour la cellule à la position 0 0 doit etre 2",2, generator.countAliveNeighbors(0, 0, grid));
	        assertEquals("le nombre de voisin pour la cellule à la position 2 2 doit etre 2",2, generator.countAliveNeighbors(2, 2, grid));
		
	}
	
	

}
