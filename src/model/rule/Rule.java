package model.rule;

public class Rule {
    private RuleFormat bornRule;
    private RuleFormat surviveRule;

    
    /**
     * constructeur de la classe qui appelle la méthode read qui permet de créer un objet ruleFormat en fonction de rulestr
     * @param rulestr la chaine de caractère entrée par l'utilisateur
     */
    public Rule(String rulestr) {
        this.read(rulestr);
    }

    

    //getters and stters
    public void setRule(String rule){
        this.read(rule);
    }
    public RuleFormat getBornRule() {
        return bornRule;
    }


    public void setBornRule(RuleFormat bornRule) {
        this.bornRule = bornRule;
    }


    public RuleFormat getSurviveRule() {
        return surviveRule;
    }


    public void setSurviveRule(RuleFormat surviveRule) {
        this.surviveRule = surviveRule;
    }


   


     /**
    *Cette méthode prend une chaîne de caractères rulestr en entrée et détermine quel type de règle doit être utilisé.
    *Si la chaîne rulestr est au format "x-y" (avec x et y deux nombres), la variable rule est initialisée avec une nouvelle instance de la classe RuleRangeF
    *Si la chaîne rulestr est un nombre, la variable rule est initialisée avec une nouvelle instance de la classe RuleMulttF
    *Si la chaîne rulestr n'est pas dans un format valide, une exception de type IllegalArgumentException est levée avec le message "La règle est erronée
    *@param rulestr :une chaîne de caractères entrée par l'utilisateur
    *@return rule une instance de RuleFormat
    
     */

    public RuleFormat readAux(String rulestr){
        RuleFormat rule = null;
        rulestr = rulestr.substring(1);
        if(rulestr.matches("^[0-9]-[0-9]$")){
            rule = new RuleRangeF(rulestr);
        }else if(rulestr.matches("^[0-9]+$")){
            rule = new RuleMulttF(rulestr);
        }else{
            throw new IllegalArgumentException("La règle est erronée");
        }

        return rule;
    }

   

    /**
    *La méthode "read" prend une chaine entrée par l'utilisateur la divise en deux parties en fonction du slash et appelle la fonction readAux sur chaque partie
    *Elle ne renvoie aucune valeur mais permet de stocker les règles dans les attributs de l'objet
    *@param userRule est une chaine valide entrée par l'utilisateur
    */

    public void read(String userRule){
        String[] parties = userRule.split("/");
        if(parties.length != 2) {
            throw new IllegalArgumentException("La règle est erronée");
        }
        String born = parties[0];
        String survive = parties[1];
        this.bornRule = readAux(born);
        this.surviveRule = readAux(survive);
    }


    /**
    *Vérifie si la cellule spécifiée doit naître selon le nombre de voisin qu'elle a.
    *fait appel à la méthode check de l'objet bornRule pour vérifier si la cellule doit naitre.
    * @param neighbors le nombre de voisin d'une cellule 
    * @return true si la cellule doit naître selon les règles de naissance, false sinon
    */

    public boolean checkBorn(int neighbors){
        return bornRule.check(neighbors);
    }


    /**
    * Vérifie si la cellule spécifiée survit à la prochaine génération en se basant sur la règle de survie spécifiée par l'utilisateur.
    * fait appel à la méthode check de l'objet surviveRule pour vérifier si la cellule survit.
    * @param neighbors le nombre de voisins d'une cellule
    * @return true si la cellule survit, false sinon
    */

    public boolean checkSurvive(int neighbors){
        //read(userRule);
        return surviveRule.check(neighbors);
    }


    

}
