package tests;
/**
 *  La classe TestRuleMult contient les tests unitaires pour la classe RuleMult.
 */



import org.junit.Test;
import static org.junit.Assert.*;

import model.*;
import model.rule.*;
import java.util.ArrayList;

public class TestRuleMullt {
	
	
	@Test
	public void testConstructeur() {
		RuleMulttF ruleMult = new RuleMulttF();
		assertEquals("le constructeur sans argument n'est pas bien défini",0, ruleMult.getValues().size());
		
	}
	
	@Test
	public void testConstructeurArgument() {
		//initialistation de l'attribue values avec une chaine de caractère
		RuleMulttF ruleMult = new RuleMulttF("123");
		assertEquals("la taille de l'attribut values doit etre égle à 3",3, ruleMult.getValues().size());
		assertTrue("values doit contenir 1",ruleMult.getValues().contains(1));
		assertTrue("values doit contenir 2",ruleMult.getValues().contains(2));
		assertTrue("values doit contenir 3",ruleMult.getValues().contains(3));
		
		
	}
	
	@Test
	public void testRead() {
		//initialisation de RuleFormat
		RuleMulttF ruleMult = new RuleMulttF();
		//une chaine permettant de tester la methode read
		String chaine1 = "123";
		ruleMult.read(chaine1);
		//verification
		assertNotNull(ruleMult.getValues());
		assertEquals("la taille de la liste Values doit être 3",3, ruleMult.getValues().size());
		assertTrue(ruleMult.getValues().contains(1));
		assertTrue(ruleMult.getValues().contains(2));
		assertTrue(ruleMult.getValues().contains(3));
		 String input = "1a2b3c";
		 RuleMulttF ruleMult2 = new RuleMulttF();
		 // Rediriger la sortie standard vers un ByteArrayOutputStream pour pouvoir recuperer la sortie de la fonction affichage    
	        ruleMult2.read(input);
	        assertEquals("la taille de values doit être 0",0,ruleMult2.getValues().size());


	}
	
	@Test
	public void testCheck() {
		//initialisation de RuleFormat	
		RuleMulttF ruleMult = new RuleMulttF();
		//recuperation de l'attribue values de la classe RuleFormat
		ArrayList<Integer> valeurs = ruleMult.getValues();
		//teste si le nombre de voision entrée se trouve dans la liste values
		valeurs.add(5);
		valeurs.add(3);
		assertTrue("la méthode doit renvoyer true car 5 se trouve dans le tableau values",ruleMult.check(5));
		assertFalse("la méthode doit renvoyer false car 2 ne se trouve pas dans le tableau values",ruleMult.check(2));
		
		
	}
}
