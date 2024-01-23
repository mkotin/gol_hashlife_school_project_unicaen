package tests;
/**
 * La classe TesHashlife contient les tests unitaires pour la classe Hashlife.
 * @author sow224
 *
 */

import org.junit.Test;

import static org.junit.Assert.*;

import model.*;
import model.hashlife.*;
import app.Generator;

public class TestHashlife {
	
	@Test
	public void testCleanBorders() {
		// créer une grille
		 Generator generator = new Generator();
	    Hashlife hashlife = new Hashlife(generator);
        Grid grid = new Grid(3, 3);
        grid.getBoard()[0][0].setEtat(1);
        grid.getBoard()[0][1].setEtat(0);
        grid.getBoard()[1][0].setEtat(0);
        grid.getBoard()[1][1].setEtat(1);
        //appeler la fonction
       Grid  newGrid = hashlife.cleanBorders(grid,2,2);
       assertEquals("l'etat de la cellule de la nouvelle grille  doit etre le meme que l'ancienne",grid.getBoard()[0][0].getEtat(), newGrid.getBoard()[0][0].getEtat());
       assertEquals("l'etat de la cellule de la nouvelle grille  doit etre le meme que l'ancienne",grid.getBoard()[0][1].getEtat(), newGrid.getBoard()[0][1].getEtat());
       assertEquals("l'etat de la cellule de la nouvelle grille  doit etre le meme que l'ancienne", grid.getBoard()[1][0].getEtat(), newGrid.getBoard()[1][0].getEtat());
       assertEquals("l'etat de la cellule de la nouvelle grille  doit etre le meme que l'ancienne",grid.getBoard()[1][1].getEtat(), newGrid.getBoard()[1][1].getEtat());
       
	}
	@Test
	public void testConvertToGrid() {
		//creer un quadTree 
		  QuadNode rootNode = new QuadNode(new QuadNode(new QuadNode(1), new QuadNode(1), new QuadNode(0), new QuadNode(0)),
           new QuadNode(new QuadNode(0), new QuadNode(1), new QuadNode(0), new QuadNode(0)),
           new QuadNode(new QuadNode(0), new QuadNode(0), new QuadNode(1), new QuadNode(0)),
           new QuadNode(new QuadNode(0), new QuadNode(0), new QuadNode(0), new QuadNode(1)));
		  
		  // Convertir le QuadTree en une grille
		   Generator generator = new Generator();
		    Hashlife hashlife = new Hashlife(generator);
		    Grid grid = hashlife.convertToGrid(rootNode);
		    //recurer le tableau de cellules de le grille
		    Cellule[][] board = grid.getBoard();
		    //verifier que la taille de la grille correspond à la taille du quadTree
		    assertEquals("la taille de la grille resultante doit etre egale à la taille du quadTree",(Integer)4, grid.getNbLine());
		    assertEquals("la taille de la grille resultante doit etre egale à la taille du quadTree",(Integer)4, grid.getNbColum());
		    // Vérifier que chaque cellule de la grille a la bonne valeur
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",1, board[0][0].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",1, board[0][1].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[0][2].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[0][3].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[1][0].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",1, board[1][1].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[1][2].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[1][3].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[2][0].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[2][1].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",1, board[2][2].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[2][3].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[3][0].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[3][1].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",0, board[3][2].getEtat());
		    assertEquals("l'etat doit etre le même que celle du QaudNoeud à la meme position",1, board[3][3].getEtat());
		
		
	}
	
	@Test
	public void testBuildQuadtree() {
		 int[][] bg = {{0, 1},{1, 0}};
		  int size = bg.length;
		  Generator generator = new Generator();
		  Hashlife hashlife = new Hashlife(generator);
		  // Appel de la méthode à tester
		   QuadNode rootNode = hashlife.buildQuadtree(bg, size);
		   assertNotNull("l'arbre ne doit pas etre vide",rootNode);
		    QuadNode swNode = rootNode.getSW();
		    assertNotNull(swNode);
		    assertEquals("l'etat de SW doit etre 1",1, swNode.getState());

		    QuadNode seNode = rootNode.getSE();
		    assertNotNull("l'arbre ne doit pas etre vide",seNode);
		    assertEquals("l'etat de SE doit etre 0",0, seNode.getState());

		    QuadNode neNode = rootNode.getNE();
		    assertNotNull("l'arbre ne doit pas etre vide",neNode);
		    assertEquals("l'etat de NE doit etre 1",1, neNode.getState());

		    QuadNode nwNode = rootNode.getNW();
		    assertNotNull("l'arbre ne doit pas etre vide",nwNode);
		    assertEquals(0, nwNode.getState());
		   
		
		
		  
	}
	@Test
	public void testConvertToQuadtree() {
		
		// créer une grille
        Grid grid = new Grid(2,2);
        //definir les etats des cellules de la grille
        grid.getBoard()[0][0].setEtat(0);
        grid.getBoard()[0][1].setEtat(1);
        grid.getBoard()[1][0].setEtat(1);
        grid.getBoard()[1][1].setEtat(0);
        
     // créer un arbre quadtree à partir de la grille
        Generator generator = new Generator();
        Hashlife hashlife = new Hashlife(generator);
        QuadNode arbre = hashlife.convertToQuadtree(grid);
     // Vérifie que la racine a les bonnes valeurs
        assertNotNull("l'arbre ne doit pas etre null",arbre);
        assertEquals("l'etat de l'arbre doit etre initialisé à 0",0, arbre.getState());
        
        QuadNode swNode = arbre.getSW();
	    assertNotNull(swNode);
	    assertEquals("l'etat de SW doit etre 1",1, swNode.getState());

	    QuadNode seNode = arbre.getSE();
	    assertNotNull("l'arbre ne doit pas etre vide",seNode);
	    assertEquals("l'etat de SE doit etre 0",0, seNode.getState());

	    QuadNode neNode = arbre.getNE();
	    assertNotNull("l'arbre ne doit pas etre vide",neNode);
	    assertEquals("l'etat de NE doit etre 1",1, neNode.getState());

	    QuadNode nwNode = arbre.getNW();
	    assertNotNull("l'arbre ne doit pas etre vide",nwNode);
	    assertEquals(0, nwNode.getState());
              
		
	}

}
