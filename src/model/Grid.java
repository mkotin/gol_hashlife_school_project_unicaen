package model;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * la classe correspondant à la grille de jeu.
 * @param board est un tableau de cellule de deux dimensions
 * @param nbLine correspond au nombre de lignes du tableau board.
 * @param nbColum correspond au nombre de colonnes du tableau board.
 * @author Mamadou Alpha Diallo
 * @version 1.0
 */
public class Grid{
    private Cellule [][] board;
    private Integer nbLine,nbColum;
    
    public Grid(Integer nbLine , Integer nbColum){
        this.board=new Cellule[nbLine][nbColum];
        this.nbLine=nbLine;
        this.nbColum=nbColum;
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                this.board[i][j]=new Cellule(new Position(i,j), 0);
            }
        }
    }
    public Integer getNbLine(){
        return this.nbLine;
    }
    public Integer getNbColum(){
        return this.nbColum;
    }

    public int getRows() {
    	return board.length;
    }
    
    public int getCols() {
    	return board[0].length;
    }

    

    public int getPopulation() {
        int population = 0;
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                if(getCellule(i, j).getEtat() == 1)
                    population++;
            }
        }

        return population;
    }
   
    public Cellule getCellule(int i, int j) {
        return board[i][j];
    }

    public boolean[][] getBooleanBoard() {
        boolean[][] boolBoard = new boolean[getRows()][getCols()];

        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                boolBoard[i][j] = this.board[i][j].getEtat() == 1 ? true : false;
            }
        }

        return boolBoard;
    }

    public boolean getCellState(int i, int j) {
        return this.board[i][j].getEtat() == 1 ? true : false;
    }

    /**

     Initialise les cellules de la grille pour avoir un état initial de 1 pour chaque position donnée dans le tableau de Positions.
     @param tabPosition un tableau de Positions à définir comme cellules vivantes sur la grille
     @throws NullPointerException si le tableau de Positions donné est nul
     */
    public void initGridUser(Position[] tabPosition){
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                Cellule cell = this.board[i][j];
                for(int z = 0; z < tabPosition.length;z++){
                    if(cell.getPosition().equals(tabPosition[z])){
                        cell.setEtat(1);
                    }
                }

            }
        }
    }
    /**

     Initialise une configuration de cellules sur la grille à partir d'un fichier texte contenant une matrice de 0 et de 1.
     Les cellules sont initialisées selon les valeurs du fichier texte en utilisant la position centrale de la grille comme point de départ.
     @param fileName le nom du fichier texte contenant la matrice de configuration de cellules
     @throws Exception si le fichier texte ne peut pas être ouvert, si le nombre de lignes ou de colonnes de la matrice de configuration est trop grand pour la grille, ou si un caractère autre que '0' ou '1' est trouvé dans le fichier.
     */
    public  void initPattern(String fileName) {
        reset();
        int[][] pattern = new int[this.nbLine][this.nbColum];
        int patternRow = 0;
        int patternCol = 0;

        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            while(fileScanner.hasNextLine()) {
                patternCol = 0;
                if (patternRow >= this.nbLine) {
                    throw new Exception("Too many lines");
                }
                String line = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter("");
                while(lineScanner.hasNext()) {
                    if (patternCol >= this.nbColum) {
                        throw new Exception("Too many columns");
                    }
                    char value = lineScanner.next().charAt(0);
                    if (value == '1') {
                        pattern[patternRow][patternCol] = 1;
                    } else if (value == '0') {
                        pattern[patternRow][patternCol] = 0;
                    } else {
                        String message = String.format("Invalid character at (%d, %d)", patternRow+1, patternCol+1);
                        throw new Exception(message);
                    }
                    patternCol++;
                }
                patternRow++;
        
            }

            int startRow = (this.nbLine - patternRow) / 2;
            int startCol = (this.nbColum - patternCol) / 2;
            for (int i = 0; i < patternRow; i++) {
                for (int j = 0; j < patternCol; j++) {
                    this.getBoard()[startRow + i][startCol + j].setEtat(pattern[i][j]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    /**
     * Il s'agit de la fonction permettant d'initialiser aléatoirement la grille.
     */
    public void initRandomGrid(){
        Random rd=new Random();
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                this.board[i][j]=new Cellule(new Position(i,j), rd.nextInt(2));
            }
        }
    }
    
    public Cellule[][] getBoard(){
        return this.board;
    }
    public void initOneCellGrid(Position pos,int etat){
        this.board[pos.getRow()][pos.getCol()]=new Cellule(pos,etat);
    }
    /**
     * C'est la fonction qui compte le nombre de cellules vivante.
     * @return 0 si aucune cellule vivante, le nombre de cellules vivantes sinon.
     */
    public int getAliveCell() {
    	int count = 0;
    	for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                	Cellule cell = this.board[i][j];
                	if(cell.getEtat() == 1) {
                		count++;
                	}
            }
        }
    	
    	return count;
    }


    public void reset() {
    	for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                	Cellule cell = this.board[i][j];
                	cell.setEtat(0);
            }
        }
    	
    }
    
    /**
     * cette fonction vérifie que toutes les cellules sont mortes.
     * @return Retour true si elles sont toutes mortes false sinon.
     */
    public boolean isAllDead() {
    	return this.getAliveCell() == 0;
    }
    
    
    @Override
    public String toString(){
        String chaine=" -----"+System.lineSeparator();
        for(int i=0;i<this.board.length;i++){
            for(int j=0;j<this.board[i].length;j++){
                chaine+="|"+this.board[i][j].getEtat();
            }
            chaine+="|"+System.lineSeparator();
            chaine+=" -----"+System.lineSeparator();
        }
        chaine+="\n";
        return chaine;
    }
    /**

     Compare si deux grilles sont égales.
     Deux grilles sont considérées comme égales si elles ont les mêmes dimensions et les mêmes états de cellules.
     @param obj l'objet à comparer avec cette grille.
     @return true si les deux grilles sont égales, false sinon.
     */
    public boolean equals(Object obj) {
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j].getEtat() != ((Grid) obj).getBoard()[i][j].getEtat()) {
                    return false;
                }
            }
        }
        return true;
    }
}