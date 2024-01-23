package tests;

/**
 * /**
 * La classe TestCellule contient les tests unitaires pour la classe Cellule.
 * @author sow224
 *
 */
import org.junit.Test;
import static org.junit.Assert.*;

import model.*;


public class TestCellule {
	
	@Test
	public void testConstructeur() {
		Cellule cellule = new Cellule(new Position(0,1),0);
		assertEquals("la coordonnee x de la cellule doit correspondre à getRow de cellule",0, cellule.getPosition().getRow());
		assertEquals("la coordonnee y de la cellule doit correspondre à getCol de cellule",1, cellule.getPosition().getCol());
		assertEquals("l'etat de la cellule doit etre initialisé à 0",0, cellule.getEtat());
	}
}

