package menace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class TTT_Main {
	private static Logger logger = LoggerFactory.getLogger(TTT_Main.class);
	private String x;
	private String o;
	private int total;
	String[][] field;
	
	public TTT_Main() {
		this.x = "X";
		this.o = "O";
		this.total=9;
		field = new String[3][3];
		//this.runtictactoe();
	}
	private void runtictactoe() {
		/*
		 * Start this game
		 */
		Players p1 = new Players("Player 1",x);
		Players p2 = new Players("Player 2",o);
		Scanner sc= new Scanner(System.in);
		boolean gameEnds = false;
		while(total>0) {
			boolean mark = false;
			int[] co;
			while(!mark) {
				if (total> 0) {
					co = getCommand(p1,sc);
					mark = Mark(co[0], co[1], p1);
					if(mark) {
						gameEnds = CheckifWin(p1,co[0],co[1]);
						if(gameEnds) {
							System.out.println("Game has ended. " + p1.getName() + " with " + p1.getMark() + " has won");
						}
					}
				}
			}
			//System.out.println("Total squares remain: " + Integer.toString(this.total));
			drawBoard();
			if(gameEnds) break;			
			
			mark = false;
			while(!mark) {
				if (total> 0) {
					co = getCommand(p2,sc);
					mark = Mark(co[0], co[1], p2);
					if(mark) {
						gameEnds = CheckifWin(p2,co[0],co[1]);
						if(gameEnds) {
							System.out.println("Game has ended. " + p2.getName() + " with " + p2.getMark() + " has won");
						}
					}
				}
				else break;
			}
			//System.out.println("Total squares remain: " + Integer.toString(this.total));
			drawBoard();
			if(gameEnds) break;
			
			
		}
		if (!gameEnds) System.out.println("The game drawed out. No one won");
		sc.close();
	}
	
	private boolean CheckifWin(Players player, int x, int y) {
		/*
		 * Check it the player who just played has won
		 */
		if(WinCondition_Row(player,x,y) || WinCondition_Column(player,x,y) || WinCondition_Diag(player)) return true;
		else return false;
	}
	
	private boolean WinCondition_Row(Players player, int x, int y) {
		/*
		 * Check if the player by the row condition
		 */
		for(int i=0; i<=2; i++) {
			if(field[i][y] != player.getMark()) return false;
		}
		return true;
	}
	
	private boolean WinCondition_Column(Players player, int x, int y) {
		/*
		 * Check if the player by the column condition. Almost same as row condition but written seperately for the sake of readability
		 */
		for(int i=0; i<=2; i++) {
			if(field[x][i] != player.getMark()) return false;
		}
		return true;
	}
	
	private boolean WinCondition_Diag(Players player) {
		/*
		 * Check if the player by the diagonal condition
		 */
		if (field[1][1] == player.getMark()) {
			if(field[0][0]==player.getMark() && field[2][2] == player.getMark()) return true;
			if(field[0][2]==player.getMark() && field[2][0] == player.getMark()) return true;
		}
		return false;
	}
	
	
	private void drawBoard() {
		/*
		 * Function to print the board. Only needed if human wants to see the status
		 */
		for (int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(field[i][j] == null) {
					System.out.print(" ");
				}
				else System.out.print(field[i][j]);
				if(j!=2) System.out.print("|");
			}
			System.out.println();
			System.out.println("-+-+-");
		}
	}
	private boolean Mark(int x, int y, Players player) {
		/*
		 * Get the command for the mentioned player
		 * @param x - The player who will be playing this turn
		 * @param y - The scanner object
		 * @param player - The player who has just marked
		 * @return - integer array, 0th element is x co-ordinate, 1st element is y co-ordinate
		 */
		if (field[x][y] != this.x && field[x][y] != this.o) {
			field[x][y] = player.getMark();
		}
		else {
			System.out.println("Already taken. Input again");
			return false;
		}
		//You now have one less available square to play on
		this.total--;
		return true;
	}
	
	private int[] getCommand(Players player, Scanner sc) {
		/*
		 * Get the command for the mentioned player
		 * @param player - The player who will be playing this turn
		 * @param sc - The scanner object
		 * @return - integer array, 0th element is x co-ordinate, 1st element is y co-ordinate
		 */
		System.out.print("Enter the command for " + player.getName() + "-- ");
		String input;
		String[] command;
		input = sc.nextLine();
		command = new String[2];
		command = input.split(" ");
		int[] co = {Integer.parseInt(command[0]),Integer.parseInt(command[1])};
		System.out.println("You input " + Integer.toString(co[0]) + " " + Integer.toString(co[1]));
		return co;
	}
	
	
	
	public static void main(String[] args) {
		TTT_Main tttM = new TTT_Main();
		logger.info("Starting the program");
		tttM.runtictactoe();
	}

}
