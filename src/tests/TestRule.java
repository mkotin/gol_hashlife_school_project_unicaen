package tests;
/**
 * La classe TestRule contient les tests unitaires pour la classe Rule .
 */

import org.junit.Test;
import static org.junit.Assert.*;

import model.*;
import model.rule.*;

public class TestRule {
	
	@Test
	public void testConstructeur() {
		String chaine1 = "B4/S2-3";
		Rule rule = new Rule(chaine1);
		RuleMulttF ruleMult = new RuleMulttF("4");
		RuleRangeF ruleRange = new RuleRangeF("2-3");
		assertEquals("la regle de naissance doit etre la meme que ruleMult",ruleMult,rule.getBornRule());
		assertEquals("la regle de survie doit etre la meme que ruleRange",ruleRange ,rule.getSurviveRule());
		
	}
	
	@Test
	public void testRead() {
		//une chaine au format x-y
		String chaine1 = "B2-3/S45";
		Rule rule = new Rule(chaine1);	
		rule.read(chaine1);
		RuleMulttF ruleMult = new RuleMulttF("45");
		RuleRangeF ruleRange = new RuleRangeF("2-3");
		assertEquals("la regle de naissance doit etre la meme que ruleMult",ruleRange,rule.getBornRule());
		assertEquals("la regle de survie doit etre la meme que ruleRange",ruleMult ,rule.getSurviveRule());
		
	}

}
