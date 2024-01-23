package model.hashlife;

import java.util.HashMap;
import java.util.Objects;

public class QuadNode {
    private int size;

    private int depth;

    private int population;
    protected int state;
    protected QuadNode southWest;
    protected QuadNode southEast;
    protected QuadNode northEast;
    protected QuadNode northWest;

    public QuadNode(int state) {
        this.southWest = null;
        this.southEast = null;
        this.northEast = null;
        this.northWest = null;
        this.state = state;
        this.depth = 0;
        this.size = 1;
        if (this.state == -1) {
            this.population = 0;
        } else {
            this.population = this.state;
        }
    }

    public QuadNode(QuadNode southWest, QuadNode southEast, QuadNode northEast, QuadNode northWest) {
        this.southWest = southWest;
        this.southEast = southEast;
        this.northEast = northEast;
        this.northWest = northWest;

        this.depth = this.southWest.depth + 1;
        this.size = this.southWest.size * 2;
        this.population = this.southWest.population + this.southEast.population + this.northEast.population + this.northWest.population;
    }

    public QuadNode getSW() {return southWest;}
    public QuadNode getSE() {return southEast;}
    public QuadNode getNE() {return northEast;}
    public QuadNode getNW() {return northWest;}

    static HashMap<QuadNode, QuadNode> nodes = new HashMap<QuadNode, QuadNode>();
    static HashMap<QuadNode, QuadNode> results = new HashMap<QuadNode, QuadNode>();

    public int getSize() {
        return this.size;
    }

    public int getDepth() {
        return this.depth;
    }
    public int getState() {
        return this.state;
    }

    public int getPopulation() {
        return this.population;
    }

    public static QuadNode create(int state) {
        return new QuadNode(state).get() ;
    }
    public static QuadNode create(QuadNode southWest, QuadNode southEast, QuadNode northEast, QuadNode northWest) {
        return new QuadNode(southWest, southEast, northEast, northWest).get() ;
    }
    public QuadNode next() {
        return results.get(this);
    }
    public QuadNode addNext(QuadNode next) {
        results.put(this, next);
        return next;
    }
    public QuadNode get() {
        QuadNode q = nodes.get(this);
        if (q != null) {
            return q;
        }
        nodes.put(this, this);
        return this;
    }

    public QuadNode getCenter() {
        return create(
                this.getSW().getNE(),
                this.getSE().getNW(),
                this.getNE().getSW(),
                this.getNW().getSE()
        ).get();
    }
    /**

     Retourne le code de hachage pour cet objet QuadNode.
     Si le noeud est une feuille, le code de hachage est simplement l'état du noeud.
     Sinon, le code de hachage est calculé à partir des sous-noeuds nord-ouest, nord-est,
     sud-ouest et sud-est, en utilisant une combinaison de la méthode de hachage de chaque
     sous-noeud.
     @return le code de hachage pour cet objet QuadNode
     */

    @Override
    // TODO: Improve hashing algorithm
    public int hashCode() {
        if (this.depth == 0) {
            return this.state;
        }
        int prime = 31;
        int result = 1;
        result = prime * result + this.northWest.hashCode();
        result = prime * result + this.northEast.hashCode();
        result = prime * result + this.southWest.hashCode();
        result = prime * result + this.southEast.hashCode();
        return result;
    }
    /**
     * Vérifie si l'objet spécifié est égal à ce QuadNode.
     * @param o L'objet à comparer avec ce QuadNode.
     * @return true si l'objet spécifié est égal à ce QuadNode, false sinon.
     * @throws ClassCastException si l'objet spécifié n'est pas une instance de QuadNode.
     */
    @Override
    public boolean equals(Object o) {
        QuadNode node = (QuadNode) o;
        if (this.depth != node.depth) {
            return false;
        }
        if (this.depth == 0) {
            return this.state == node.state;
        }
        return this.northWest.equals(node.northWest)  &&
                this.northEast.equals(node.northEast)  &&
                this.southWest.equals(node.southWest)  &&
                this.southEast.equals(node.southEast);
    }
    /**
     * Ajoute une bordure de nœuds vides autour du nœud actuel et renvoie le nouveau nœud racine
     *
     * @return le nouveau nœud racine avec une bordure de nœuds vides ajoutée autour du nœud actuel
     */
    public QuadNode addBorder() {
        QuadNode nodeBorder = create(-1);
        for (int i=0; i < this.depth-1; i++) {
            nodeBorder = create(nodeBorder, nodeBorder, nodeBorder, nodeBorder);
        }
        QuadNode sw = create(nodeBorder, nodeBorder, this.southWest, nodeBorder);
        QuadNode se = create(nodeBorder, nodeBorder, nodeBorder, this.southEast);
        QuadNode ne = create(this.northEast, nodeBorder, nodeBorder, nodeBorder);
        QuadNode nw = create(nodeBorder, this.northWest, nodeBorder, nodeBorder);
        return create(sw, se, ne, nw);
    }
}
