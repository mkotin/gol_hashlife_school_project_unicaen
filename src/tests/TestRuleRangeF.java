package tests;
/**
 * La classe TestRuleRangeF contient les tests unitaires pour la classe RuleRangeF.
 */


import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import model.*;
import model.rule.*;

public class TestRuleRangeF {
	@Test
	public void testConstructeur() {
		RuleRangeF ruleRangef = new RuleRangeF();
		assertEquals(2, ruleRangef.getRange().length);
		
	}
	
	@Test
	public void testConstructeurArgument() {
		//initialistation de l'attribue values avec une chaine de caractère
		RuleRangeF ruleRangef = new RuleRangeF("2-3");
		assertEquals("le tablau range doit etre de taille 2",2, ruleRangef.getRange().length);
		assertEquals("la premiere colone de range doit avoir la valeur 2",2,ruleRangef.getRange()[0]);
		assertEquals("la deuxieme colone de range doit avoir la valeur 3",3,ruleRangef.getRange()[1]);
		
	}
	
	@Test
	public void testRead() {
		//initialisation de RuleRangeF
		RuleRangeF ruleRangef = new RuleRangeF();
		//creer une chaine et appeler la methode read
		String ranges = "1-3";
		ruleRangef.read(ranges);
		//verifier que la chaine est bien affecté à l'attribut range de RuleRangeF
		assertEquals("le tablau range doit etre de taille 2",2, ruleRangef.getRange().length);
		assertEquals("la premiere colone de range doit avoir la valeur 1",1,ruleRangef.getRange()[0]);
		assertEquals("la deuxieme colone de range doit avoir la valeur 3",3,ruleRangef.getRange()[1]);
		String ranges2 = "a1";
		ruleRangef.read(ranges2);
		assertEquals("la taille de range doit etre vide car la règle est erronée",0,ruleRangef.getRange().length);
		
	}
	
	@Test
	public void testCheck() {
		//initialisation de RuleFormat	
		RuleRangeF ruleRangef = new RuleRangeF();
		//recuperation de l'attribue range de la classe RuleFormat
		int[] valeurs = ruleRangef.getRange();
		//vérification du retour de la méthode
		valeurs[0] =2;
		valeurs[1] = 3;
		assertTrue("la mééthode doit renvoyer true car 2 est inférieur à 3 et égale à 2",ruleRangef.check(2));
				
	}
	
	

}
