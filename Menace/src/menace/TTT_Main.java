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
		/*
		 * Constructor
		 */
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
			gameEnds = RunThePlayer(p1, sc);
			drawBoard();
			if(gameEnds) break;			
			
			gameEnds = RunThePlayer(p2, sc);
			drawBoard();
			if(gameEnds) break;
			
			
		}
		if (!gameEnds) System.out.println("The game drawed out. No one won");
		sc.close();
	}
	
	private boolean RunThePlayer(Players p, Scanner sc) {
		/*
		 * Get the command, mark it and check if the player won
		 * @param p - The player
		 * @param sc - The scanner context
		 * @return if the move won the player the game
		 */
		boolean mark = false;
		boolean gameEnds = false;
		while(!mark) {
			if (total> 0) {
				int[] co = getCommand(p,sc);
				mark = Mark(co[0], co[1], p);
				if(mark) {
					gameEnds = CheckifWin(p,co[0],co[1]);
					if(gameEnds) {
						System.out.println("Game has ended. " + p.getName() + " with " + p.getMark() + " has won");
					}
				}
			}
			else break;
		}
		return gameEnds;
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
		if(x>2 || x<0 || y>2 || y<0) {
			System.out.println("Invalid input, try again");
			return false;
		}
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
