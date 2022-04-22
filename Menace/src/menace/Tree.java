package menace;


public class Tree {
	public static int win=0;
	char[][] field = new char[3][3];
	Tree[][] children = new Tree[3][3];

	int value;
	boolean isMin;
	TTT_Main ttt;

	public Tree(char[][] field, boolean isMin, TTT_Main ttt) {
		this.field = field;
		this.isMin=isMin;
		this.ttt = ttt;
	}

	public char[][] clone() {
		char[][] clone = new char[3][3];
		for (int i = 0; i < clone.length; i++) {
			for (int j = 0; j < clone[i].length; j++) {
				clone[i][j] = field[i][j];
			}
		}
		return clone;
	}

	public int createTree() {
		if (ttt.CheckIfWin(field, this.ttt)) {
			value = isMin ? 1 : -1;
			win++;
			return value;
		} else if (ttt.getTotal(field)) {
			value = 0;
			return value;
		}

		value = isMin ? 1 : -1;

		for (int i = 0; i < children.length; i++) {
			for (int j = 0; j < children[i].length; j++) {

				if (field[i][j] == 0) {
					char[][] childField = clone();
					
					char input;
					if (isMin) input = ttt.x;
					else input = ttt.o;
					childField[i][j] = input;
					children[i][j] = new Tree(childField, !isMin, ttt);
					
					
					if(isMin) {
						value = Math.min(value, children[i][j].createTree());
					}
					else {
						value = Math.max(value, children[i][j].createTree());
					}

				}
			}
		}

		return value;
	}

	
	public int[] getChildWithValue() {
		for (int i = 0; i < children.length; i++) {
			for (int j = 0; j < children[i].length; j++) {
				if (children[i][j] != null && children[i][j].value == value) {
					return new int[] {i,j};
				}
			}
		}
		return null;
	}

}
