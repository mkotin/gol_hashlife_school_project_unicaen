package tests;


/**
 * 
 * @author sow224
 * La classe TestPosition contient les tests unitaires pour la classe Cellule.
 *
 */

import org.junit.Test;


import static org.junit.Assert.*;

import model.*;

public class TestPosition {
	
	@Test
	public void testConstructeur() {
		Position position = new Position(5,7);
		assertEquals("le nombre de lignes doit etre le meme que position.getRow",5, position.getRow());
		assertEquals("le nombre de colones doit etre le meme que position.getCol",7, position.getCol());
		
	}

}
