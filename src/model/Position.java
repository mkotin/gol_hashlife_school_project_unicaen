package model;

/*
 * A class representing the position of a cell in the grid
 */

public class Position {
    
    // the row
    
    private int row;

    // the column
    private int col;

    
    /**
     * Buids a new instance
     * 
     * @param row The row
     * @param col The col
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Return a row
     * @return row
     */
    public int getRow() {
        return row;
    }

     /**
     * Set the value of  row
     * @param row The row
     */
    public void setRow(int row) {
        this.row = row;
    }

     /**
     * Return a column
     * @return col
     */
    public int getCol() {
        return col;
    }


     /**
     * Set the value of column
     * @param col The column
     */
    public void setCol(int col) {
        this.col = col;
    }
    
    
    

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
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
		Position other = (Position) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}



    /* @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true 
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Position or not
          "null instanceof [type]" also returns false */
      /*  if (!(o instanceof Position)) {
            return false;
        }

        // typecast o to Position so that we can compare data members
        Position otherPosition = (Position) o;
        return (this.row == otherPosition.getRow() && this.col == otherPosition.getCol());
    }*/
    
    
    
}
