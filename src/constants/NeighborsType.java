package constants;

import java.util.Arrays;

public final class NeighborsType {
	private NeighborsType() {
		
	}
	
	public static final int[][] GAMEOFLIFE = 
		{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
	
	public static final int[][] TYPE2 = 
			{{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
	
	public static final int[][] TYPE3 = 
			{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}, {0, 0}};
	
	/**
	 * Return the correct type according to the string param and gol type by default
	 * @param type the type
	 * @return  the correct type according to the string param and gol type by default
	 */
	public static int[][] getType(String type) {
		if(type.toUpperCase().equals("TYPE2")) {
			return TYPE2;
		} else if(type.toUpperCase().equals("TYPE3")) {
			return TYPE3;
		} else {
			return GAMEOFLIFE;
		}
	}

	/**
	 * Return a string representation of a neighbors type
	 * @param type the type 
	 * @return a string representation of a neighbors type
	 */
	public static String coordRepresentation(String type) {
		int[][] nType = getType(type);
		
		StringBuilder sb = new StringBuilder();
        String delimiteur = ", ";

        for (int i = 0; i < nType.length; i++) {
            sb.append("(");
            sb.append(nType[i][0]);
            sb.append(delimiteur);
            sb.append(nType[i][1]);
            sb.append(")");

            if (i < nType.length - 1) {
                sb.append(delimiteur);
            }
        }

        return sb.toString();

	}

	/**
	 * Convert a string of coord (reperesenting neighbors) to a 2D table of coord
	 * @param type
	 * @return a 2D table of string coord (reperesenting neighbors)
	 */
	public static int[][] stringToCoord(String chaineCoordonnees) {
		// Vérifier si la chaîne de caractères est vide
		if (chaineCoordonnees == null || chaineCoordonnees.trim().isEmpty()) {
			return GAMEOFLIFE;
		}
	
		// Vérifier si la chaîne de caractères est au bon format
		String regex = "\\((-?\\d+),(-?\\d+)\\)(;\\((-?\\d+),(-?\\d+)\\))*";
		if (!chaineCoordonnees.matches(regex)) {
			return GAMEOFLIFE;
		}
	
		// Convertir la chaîne de caractères en un tableau 2D d'entiers
		String[] pairesCoordonnees = chaineCoordonnees.split(";");
		int[][] tableauCoordonnees = new int[pairesCoordonnees.length][2];
		for (int i = 0; i < pairesCoordonnees.length; i++) {
			String[] coordonnees = pairesCoordonnees[i].replaceAll("\\(|\\)", "").split(",");
			tableauCoordonnees[i][0] = Integer.parseInt(coordonnees[0]);
			tableauCoordonnees[i][1] = Integer.parseInt(coordonnees[1]);
		}
		
		// System.out.println(Arrays.deepToString(tableauCoordonnees));
		
		return tableauCoordonnees;
	}
}
