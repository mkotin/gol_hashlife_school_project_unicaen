package tests;
/**
 * /**
 * La classe TestNeighborsType contient les tests unitaires pour la classe NeighborsType.
 * @author sow224
 *
 */
import org.junit.Test;

import constants.NeighborsType;

import static org.junit.Assert.*;

import model.*;

public class TestNeighborsType {
	@Test
	public void testgetType() {
		//creer une instance de NeighborsType
		//appelle de la fonction getType
		//int[][] tableauTest = neighbors.getType("type2");
		int[][] type2 = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
		int[][] type3 = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}, {0, 0}};
		int[][] game = NeighborsType.GAMEOFLIFE;
		assertArrayEquals("le tableau doit etre le meme que le type 2",type2, NeighborsType.getType("type2"));
		assertArrayEquals("le tableau doit etre le meme que le type 3",type3, NeighborsType.getType("type3"));
		assertArrayEquals("le tableau doit etre le meme que Game of life",game, NeighborsType.getType("type"));
		
	}
	@Test
	public void testcoordRepresentation() {
		String chaine = "(-1, 0), (0, -1), (0, 1), (1, 0)";
		assertEquals("le type 2 doit etre egale à la variable chaine",chaine,NeighborsType.coordRepresentation("type2"));
	}
	
	@Test
	public void testStringToCoord() {
		String chaine = "(-1,0);(0,-1);(0,1);(1,0)";
		int[][] coordToTab = NeighborsType.stringToCoord(chaine);
		int[][] expectedOutput1 = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
		String chaineVide = "";
		int[][] expectedOutput2 = NeighborsType.GAMEOFLIFE;
		assertArrayEquals("",expectedOutput1,coordToTab);
		assertArrayEquals(expectedOutput2, NeighborsType.stringToCoord(chaineVide));
		
	}
	
	

}
