package model.rule;

import java.util.Arrays;

public class RuleRangeF  implements RuleFormat{
    
    protected int[] range;
    

    /**
     * constructeur de la classe qui initialise un tableau d'entiers de taille deux
     */
    public RuleRangeF() {
        this.range = new int[2];
    }
    /**
     * constructeur de la classe qui prend en entrée une chaine et appelle la méthode read dessus
     * @param rulestr
     */

    public RuleRangeF(String rulestr) {
        this.range = new int[2];
        this.read(rulestr);
    }

    /**
     * Cette méthode a pour but de lire la règle entrée par l'utilisateur
     * sous forme de chaîne de caractères  la diviser en deux parties et la stocker dans l'attribut range.
     * la méthode s'assure que la chaine contient que des entiers et que le premier est inférieur au deuxieme
     * @param userRule chaine de caractere qui represente la regle saisi par l'utilisateur
     */
    @Override
    public void read(String userRule){
         String[] parts = userRule.split("-");
         boolean validRule = true;
        try{
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[1]);

            if (num1 > num2) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
            range[0] = num1;
            range[1] = num2;
        }catch (NumberFormatException e) {
            validRule = false;
        }
        if (!validRule) {
            System.out.println("Entrée non valide : " + userRule);
            range = new int[0];
        }
       

    }


     
    /**
     *@param neighbors :le nombre de voisin d'une cellule
     *@return  La méthode renvoie true si le nombre de voision d'une cellule est compris entre la premiere et la deuxieme valeur de l'attribut de la classe range.false sinon
     */
    @Override
    public boolean check(int neighbors) {
        return (neighbors >= range[0] && neighbors <= range[1]);
    }
    
    //getter and setter
    
	public int[] getRange() {
		return range;
	}
	public void setRange(int[] range) {
		this.range = range;
	}
	
	//redefinition de toString
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(range);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleRangeF other = (RuleRangeF) obj;
		if (!Arrays.equals(range, other.range))
			return false;
		return true;
	}

       
}

        
    
