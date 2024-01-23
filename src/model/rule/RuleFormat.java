package model.rule;

/**
 * Interface définissant les méthodes pour un format de règle
 */

public interface RuleFormat {

   /**
    * cette méthode permet de lire une chaine de caractère entrée par l'utilisateur 
    * @param userRule la règle entrée par l'utilisateur
    */
   void read(String userRule );

    /**
    * 
    * @param neighbors le nombre de voisin d'une cellule 
    * @return un boolean qui dit si le nombre de voisin d'une cellule se trouve dans le tableau de valeur entrées par l'utilisateur
    */

   boolean check(int neighbors);

    
}
