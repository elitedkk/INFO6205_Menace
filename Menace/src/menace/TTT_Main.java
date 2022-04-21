package menace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TTT_Main {
	private static Logger logger = LoggerFactory.getLogger(TTT_Main.class);
	private String x;
	private String o;
	private int total;
	private boolean isTraining;
	private int trainingGames;
	char[] currState;
	//private boolean[] remaining;
	String[][] field;
	
	public TTT_Main() {
		/*
		 * Constructor
		 */
		this.x = "X";
		this.o = "O";		
		isTraining = true;
		Players p1 = new Players("Player 1",x, true);
		Players p2 = new Players("Player 2",o, true);
		Players human = new Players("Human", o, false);
		this.trainingGames = 1000;
		this.isTraining=true;
		Scanner sc= new Scanner(System.in);
		for(int i=0; i<this.trainingGames; i++) {
			System.out.println("Starting game number " + Integer.toString(i+1));
			field = new String[3][3];
			this.runtictactoe(p1, p2, sc);
		}
		this.isTraining=false;
		//this.runtictactoe(p1,human, sc);
		sc.close();
		
	}
	private void runtictactoe(Players p1, Players p2, Scanner sc) {
		/*
		 * Start this game
		 */
		//remaining = new boolean[9];
		//Arrays.fill(remaining, false);
		this.total=9;
		this.currState = new char[9];
		Arrays.fill(currState, '0');
		boolean gameEnds = false;
		while(total>0) {
			gameEnds = RunThePlayer(p1, sc);
			if(!p1.isComputer() || !p2.isComputer()) drawBoard();
			if(gameEnds) {
				break;			
			}
			
			gameEnds = RunThePlayer(p2, sc);
			if(!p1.isComputer() || !p2.isComputer()) drawBoard();
			if(gameEnds) {
				break;
			}
			
		}
		if (!gameEnds) System.out.println("The game drawed out. No one won");
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
					changeArray(p, co[0],co[1]);
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
	
	private void changeArray(Players p, int x, int y) {
		int ind = (x*3)+y;
		char enter;
		if(p.getMark()==this.x) enter='1';
		else enter = '2';
		this.currState[ind] = enter;
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
			//System.out.println("Already taken. Input again");
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
		 * @param sc - The scanner object incase a human is playing
		 * @return - integer array, 0th element is x co-ordinate, 1st element is y co-ordinate
		 */
		if(player.isComputer()) {
			if(this.isTraining) {
				int i=0,j=0;
				int rand = new Random().nextInt(9);
				i = rand/3;
				j = rand%3;
				return new int[] {i,j};
			}
			else {
				//Access the memory to see which decision to take
				return null;
			}
			
		}
		else {
			System.out.print("Enter the command for " + player.getName() + "-- ");
			String input = sc.nextLine();
			String[] command = new String[2];
			command = input.split(" ");
			int[] co = {Integer.parseInt(command[0]),Integer.parseInt(command[1])};
			System.out.println("You input " + Integer.toString(co[0]) + " " + Integer.toString(co[1]));
			return co;
		}
	}
	
	private void waitForResults(Scanner sc) {
		System.out.println("Look at the results");
		String input = sc.next();
	}
	
	public static void main(String[] args) {
		logger.info("Starting the program");
		TTT_Main tttM = new TTT_Main();
	}

}
