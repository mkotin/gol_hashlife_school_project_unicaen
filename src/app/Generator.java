package app;

import model.Cellule;
import model.Grid;
import model.rule.Rule;

/*
 * A class to generate the next generation of the grid
 */

public class Generator {
    private Rule rule;
    private int[][] neighbors;

    public Generator(Rule rule) {
        this.rule = rule;
        this.neighbors = constants.NeighborsType.GAMEOFLIFE; // game of life default
    }

    public Generator() {
        this(new Rule(constants.Rules.GAMEOFLIFE)); // game of life default
    }

    public int[][] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(int[][] neighbors) {
		this.neighbors = neighbors;
	}


    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    /**
     * cette fonction calcule le nombre de voisins d'une cellule
     * @param i entier : ligne de la cellule dont on veut compter les voisins vivants. 
     * @param j entier : colonne de la cellule dont on veut compter les voisins
     * @param grid objet Grid : une instance de la classe Grid représentant la grille de cellules
     * @requires les entées i et j doivent être des entiers compris entre 0 et les dimensions de la grille Grid
     * @requires L'objet grid doit être une instance valide de la classe Grid et doit contenir des cellules accessibles par grid.getBoard()
     * @ensures La méthode retourne un entier représentant le nombre de voisins vivants de la cellule donnée
     * @return Un entier représentant le nombre de voisins vivants de la cellule située à la ligne i et la colonne j dans la grille grid
     */
    public int countAliveNeighbors(int row, int col, Grid grid) {
        int count = 0;
        for (int[] n : neighbors) {
          int r = row + n[0];
          int c = col + n[1];
          if (r >= 0 && r < grid.getRows() && c >= 0 && c < grid.getCols() && grid.getBoard()[r][c].getEtat() == 1) {
            count++;
          }
        }
        return count;
    }


    /**
     * Cette fonction prend en entrée une grille actuelle et renvoie la
     * grille de la generation suivante en utilisant les règles spécifié par l'utilisateur
     * @param grid une instance de la classe Grid
     * @requires la fonction countLiveNeighbors doit être créé avant son utilisation dans la fonction
     * @return un objet de type Grid representant la nouvelle generation 
     */

    public Grid nextGeneration(Grid grid){
    	int row = grid.getBoard().length;
        int col = grid.getBoard()[0].length;
    	Grid nextGrid = new Grid(grid.getBoard().length, grid.getBoard()[0].length);
    	Cellule [][] nextBoard = nextGrid.getBoard();
    	Cellule [][]board = grid.getBoard();
            	
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int liveNeighbors = countAliveNeighbors(i, j, grid);
                if (board[i][j].getEtat() == -1){
                    nextBoard[i][j].setEtat(-1);
                }else if (board[i][j].getEtat() == 1) {
                    if (rule.checkSurvive(liveNeighbors)) {
                    	nextBoard[i][j].setEtat(1);
                    } else {
                    	nextBoard[i][j].setEtat(0);
                    }
                } else {
                    if (rule.checkBorn(liveNeighbors)) {
                    	nextBoard[i][j].setEtat(1);
                    } else {
                    	nextBoard[i][j].setEtat(0);
                    }
                }
            }
        }
        
        return nextGrid;
        
    }

   }


    

