package model.hashlife;

import app.Generator;
import model.Cellule;
import model.Grid;
import model.Position;

import java.util.HashMap;

public class Hashlife {

    Generator generator;

    public Hashlife(Generator generator) {
        this.generator = generator;
    }

    /**
     * Convertit une grille en un arbre quadtree.
     * @param g la grille à convertir en arbre quadtree.
     * @return l'arbre quadtree correspondant à la grille.
     * @pre la grille doit être valide et non vide.
     * @post retourne un arbre quadtree construit à partir des données de la grille.
     */
    public QuadNode convertToQuadtree(Grid g) {
        int size = Math.max(g.getNbColum(), g.getNbLine());

        int power = 1;
        while (power < size) {
            power *= 2;
        }
        size = power;

        int[][] bg = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i > g.getNbLine() -1 || j > g.getNbColum() -1) {
                    bg[i][j] = -1;
                } else {
                    bg[i][j] = g.getBoard()[i][j].getEtat();
                }
            }
        }
        return this.buildQuadtree(bg, size);
    }
    /**

     Nettoie les bords d'une grille donnée et crée une nouvelle grille avec les dimensions spécifiées.
     Les cellules en dehors de la nouvelle grille seront ignorées.
     @param old la grille d'origine à nettoyer
     @param rows le nombre de lignes de la nouvelle grille
     @param columns le nombre de colonnes de la nouvelle grille
     @return la nouvelle grille avec les cellules de la grille d'origine dans les limites de la nouvelle grille
     */
    public Grid cleanBorders(Grid old, int rows, int columns) {
        Grid grid = new Grid(rows, columns);
        for (int i=0;i<rows; i++) {
            for (int j=0;j<columns; j++) {
                grid.getBoard()[i][j].setEtat(old.getBoard()[i][j].getEtat());
            }
        }
        return grid;
    }
    /**
     * Construit un arbre QuadTree à partir d'une matrice d'entiers et d'une taille donnée.
     *
     * @param bg   la matrice d'entiers à partir de laquelle construire l'arbre QuadTree
     * @param size la taille de la matrice d'entiers
     * @return le nœud racine de l'arbre QuadTree construit
     *
     * @require bg != null
     * @require bg.length == size
     * @require bg[i].length == size, pour tout i allant de 0 à size-1
     * @require size > 0
     *
     * @ensure le nœud racine de l'arbre QuadTree construit est retourné
     */
    public QuadNode buildQuadtree(int[][] bg, int size) {
        QuadNode node;
        if (size == 1) {
            node = QuadNode.create(bg[0][0]);
        } else {
            int halfSize = size/2;
            int[][] nw = new int[halfSize][halfSize];
            int[][] ne = new int[halfSize][halfSize];
            int[][] sw = new int[halfSize][halfSize];
            int[][] se = new int[halfSize][halfSize];
            for (int i = 0; i < halfSize; i++) {
                for (int j = 0; j < halfSize; j++) {
                    nw[i][j] = bg[i][j];
                    ne[i][j] = bg[i][j + halfSize];
                    sw[i][j] = bg[i + halfSize][j];
                    se[i][j] = bg[i + halfSize][j + halfSize];
                }
            }
            QuadNode swNode = buildQuadtree(sw, halfSize);
            QuadNode seNode = buildQuadtree(se, halfSize);
            QuadNode neNode = buildQuadtree(ne, halfSize);
            QuadNode nwNode = buildQuadtree(nw, halfSize);
            node = QuadNode.create(swNode, seNode, neNode, nwNode);
        }
        return node;
    }
    /**
     * Sauter le nombre de générations spécifié dans le jeu de la vie.
     *
     * @param grid Une grille carrée représentant l'état initial du jeu.
     * @param generations Le nombre de générations à sauter.
     * @return Une grille représentant l'état du jeu après avoir sauté le nombre de générations spécifié.
     *
     * @pre La grille d'entrée doit être carrée et avoir une taille supérieure ou égale à 2.
     * @pre La valeur de generations doit être un entier positif ou nul.
     * @post La grille renvoyée est de la même taille que la grille d'entrée.
     * @post La grille renvoyée représente l'état du jeu après avoir sauté le nombre de générations spécifié.
     * @post Les cellules de la grille renvoyée sont mises à jour selon les règles du jeu de la vie.
     * @post Les cellules de la grille renvoyée sont soit vivantes, soit mortes.
     */

    public Grid jumpGenerations(Grid grid, int generations) {
        if (generations == 0) {
            return grid;
        }

        QuadNode node = this.convertToQuadtree(grid);

        // Keep adding borders to the tree to skip more generations at once
        int jump = (int) Math.pow(2, node.getDepth()-2);
        int addedBorders = 0;
        while (generations >= jump*2) {
            node = node.addBorder();
            jump = (int) Math.pow(2, node.getDepth()-2);
            addedBorders++;
        }

        // Compute many generations, trim added borders and reset results
        if (addedBorders > 0) {
            node = this.computeNextGeneration(node, true);
            for (int i=0; i<addedBorders-1;i++) {
                node = node.getCenter();
            }
            // Clear results
            QuadNode.results = new HashMap<>();

            generations = generations - jump;
        }

        // Compute a single generation at a time
        for (int i=0; i < generations; i++) {
            node = node.addBorder();
            node = this.computeNextGeneration(node, false);
        }

        return this.cleanBorders(this.convertToGrid(node), grid.getNbLine(), grid.getNbColum());
    }
    /**

     Calcule et renvoie le prochain niveau de la hiérarchie du quadtree à partir du nœud donné.
     @param node le nœud à partir duquel le prochain niveau doit être calculé
     @param fast un indicateur de performance, si "true" alors le calcul est effectué plus rapidement mais avec moins de précision
     @pre le nœud "node" est un nœud valide dans la hiérarchie du quadtree
     @pre l'indicateur "fast" est soit "true" soit "false"
     @post le résultat est un nœud valide dans la hiérarchie du quadtree, représentant le prochain niveau de la hiérarchie à partir du nœud donné
     @return le prochain niveau de la hiérarchie du quadtree à partir du nœud donné
     */
    private QuadNode computeNextGeneration(QuadNode node, boolean fast) {
        QuadNode resultNode;

        if (node.getPopulation() == 0) {
            return node.getCenter();
        }

        resultNode = node.next();
        if (resultNode != null) {
            return resultNode;
        }

        if (node.getDepth() == 2) {
            Grid grid = this.convertToGrid(node);
            Grid nextGrid = this.generator.nextGeneration(grid);
            QuadNode nextNode = this.convertToQuadtree(nextGrid);
            resultNode = nextNode.getCenter();
        } else {
            /*
             * n1 | n2 | n3
             * ---|----|---
             * n4 | n5 | n6
             * ---|----|---
             * n7 | n8 | n9
             */
            QuadNode n7 = QuadNode.create(node.getSW().getSW(), node.getSW().getSE(), node.getSW().getNE(), node.getSW().getNW());
            QuadNode n4 = QuadNode.create(node.getSW().getNW(), node.getSW().getNE(), node.getNW().getSE(), node.getNW().getSW());
            QuadNode n1 = QuadNode.create(node.getNW().getSW(), node.getNW().getSE(), node.getNW().getNE(), node.getNW().getNW());

            QuadNode n8 = QuadNode.create(node.getSW().getSE(), node.getSE().getSW(), node.getSE().getNW(), node.getSW().getNE());
            QuadNode n5 = QuadNode.create(node.getSW().getNE(), node.getSE().getNW(), node.getNE().getSW(), node.getNW().getSE());
            QuadNode n2 = QuadNode.create(node.getNW().getSE(), node.getNE().getSW(), node.getNE().getNW(), node.getNW().getNE());

            QuadNode n9 = QuadNode.create(node.getSE().getSW(), node.getSE().getSE(), node.getSE().getNE(), node.getSE().getNW());
            QuadNode n6 = QuadNode.create(node.getSE().getNW(), node.getSE().getNE(), node.getNE().getSE(), node.getNE().getSW());
            QuadNode n3 = QuadNode.create(node.getNE().getSW(), node.getNE().getSE(), node.getNE().getNE(), node.getNE().getNW());

            QuadNode r7 = this.computeNextGeneration(n7, fast);
            QuadNode r8 = this.computeNextGeneration(n8, fast);
            QuadNode r9 = this.computeNextGeneration(n9, fast);
            QuadNode r4 = this.computeNextGeneration(n4, fast);
            QuadNode r5 = this.computeNextGeneration(n5, fast);
            QuadNode r6 = this.computeNextGeneration(n6, fast);
            QuadNode r1 = this.computeNextGeneration(n1, fast);
            QuadNode r2 = this.computeNextGeneration(n2, fast);
            QuadNode r3 = this.computeNextGeneration(n3, fast);

            QuadNode sw, se, nw, ne;
            if (!fast) {
                sw = QuadNode.create(r7, r8, r5, r4).getCenter();
                se = QuadNode.create(r8, r9, r6, r5).getCenter();
                nw = QuadNode.create(r4, r5, r2, r1).getCenter();
                ne = QuadNode.create(r5, r6, r3, r2).getCenter();
            } else {
                sw = this.computeNextGeneration(QuadNode.create(r7, r8, r5, r4), true);
                se = this.computeNextGeneration(QuadNode.create(r8, r9, r6, r5), true);
                nw = this.computeNextGeneration(QuadNode.create(r4, r5, r2, r1), true);
                ne = this.computeNextGeneration(QuadNode.create(r5, r6, r3, r2), true);
            }
            resultNode = QuadNode.create(sw, se, ne, nw);
        }

        return node.addNext(resultNode);
    }

    /**
     * Convertit un arbre QuadTree en une grille (Grid) de cellules.
     * @param root Le noeud racine de l'arbre QuadTree.
     * @return Une grille (Grid) de cellules représentant l'arbre QuadTree.
     * @pre root != null
     * @post La grille (Grid) retournée a les mêmes dimensions que l'arbre QuadTree représenté par le noeud racine root.
     */
    public Grid convertToGrid(QuadNode root) {
        int size = root.getSize();
        Grid grid = new Grid(size, size);
        Cellule[][] board = grid.getBoard();
        this.convertToGridRecursive(root, board, 0, 0, size);
        return grid;
    }
    /**

     Convertit un QuadTree en une grille de cellules.
     @param node le QuadNode à convertir.
     @param board la grille de cellules à remplir.
     @param x la position en x où commencer à remplir la grille.
     @param y la position en y où commencer à remplir la grille.
     @param size la taille de la grille à remplir.
     @throws IllegalArgumentException si la grille ou le QuadNode est null, ou si la taille de la grille est négative ou supérieure à la taille de la grille.
     @ensures La grille de cellules board est remplie avec les états du QuadTree node, en commençant à la position (x,y) et avec une taille de size.
     */
    private void convertToGridRecursive(QuadNode node, Cellule[][] board, int x, int y, int size) {
        if (node.getSW() == null ){
            int state = node.getState();
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    board[i][j] = new Cellule(new Position(i, j), state);
                }
            }
        } else {
            int halfSize = size/2;
            convertToGridRecursive(node.getNW(), board, x, y, halfSize);
            convertToGridRecursive(node.getNE(), board, x, y + halfSize, halfSize);
            convertToGridRecursive(node.getSW(), board, x + halfSize, y, halfSize);
            convertToGridRecursive(node.getSE(), board, x + halfSize, y + halfSize, halfSize);
        }
    }
}