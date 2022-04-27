package menace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TTT_Main {
	private static Logger logger = LoggerFactory.getLogger(TTT_Main.class);
	public char x;
	public char o;
	private int total;
	private static Tree aifirstplay;
	private static Tree aisecondplay;
	public static boolean isFinal=false;
	Tree auto;
	Tree auto2;
	//private boolean[] remaining;
	char[][] field;
	public static Scanner sc;
	public boolean useProb;
	Menace menace;
	
	public TTT_Main() {
		/*
		 * Constructor
		 */
		this.x = 'X';
		this.o = 'O';		
		
		Tree firstPlay = new Tree(new char[3][3], false, this);
		Tree secondPlay = new Tree(new char[3][3], true, this);
		
		firstPlay.createTree();
		secondPlay.createTree();
		
		//aifirstplay = firstPlay;
		//aisecondplay = secondPlay;
		
		
		Players p = new Players("My Player",x, true, false);
		Players human  = new Players("Human", o, false, false);

		Players human_sim1 = new Players("My Player",x, true, false);
		Players menacePlayer2  = new Players("Menace", o, true, true);
		
		Players menacePlayer1  = new Players("Menace", o, true, true);
		Players human_sim2 = new Players("My Player",x, true, false);
		
		menace = new Menace(this);
		useProb = true;
		//field=new char[3][3];
		
		
		for(int i=0; i<Menace.trainingGames; i++) {
			//System.out.println("Starting game number " + Integer.toString(i+1));
			logger.info("Starting game number " + Integer.toString(i+1));
			field = new char[3][3];
			this.auto = secondPlay;
			if(i== Menace.trainingGames-1) {
				logger.info("LAST ROUND: --> MOVES");
				isFinal=true;
			}
			this.runtictactoe(menacePlayer1, human_sim2);
		}
		
		//Training Meance for playing second player
		/*for(int i=0; i<Menace.trainingGames; i++) {
			//System.out.println("Starting game number " + Integer.toString(i+1));
			logger.info("Starting game number " + Integer.toString(i+1));
			field = new char[3][3];
			this.auto = firstPlay;
			this.runtictactoe(human_sim1, menacePlayer2);
		}*/
		logger.info("Number of wins = " + Menace.totalwin);
		logger.info("Number of losses = " + Menace.totallose);
		logger.info("Number of draw = " + Menace.totaldraw);
		
		
		/*
		while(true) {
			menacePlayer1  = new Players("Menace", x, true, true);
			field = new char[3][3];
			this.auto= firstPlay;
			this.runtictactoe(menacePlayer1, human);
		}*/
		/*int turn =0;
		if(!human_sim1.isComputer() || !menacePlayer2.isComputer()) {
			while (turn != 1 && turn != 2) {
				turn=this.GetHumanWay();
				if(turn!=1 && turn!=2) System.out.println("Enter a correct value please");
			}
		}*/
		/*
		System.out.println("Starting ...");
		turn=2;
		field = new char[3][3];
		if(turn==1) {
			auto = secondPlay;
			this.runtictactoe(human, p);
		}
		else {
			auto = firstPlay;
			this.runtictactoe(p, human);
		}*/
		//this.runtictactoe(p1,human, sc);
		
	}
	private void runtictactoe(Players p1, Players p2) {
		/*
		 * Start this game
		 */
		//remaining = new boolean[9];
		//Arrays.fill(remaining, false);
		this.total=9;
		boolean gameEnds = false;
		if(!p1.isComputer()) {
			System.out.println("You will play first");
			drawBoard();
		}
		while(total>0) {
			gameEnds = RunThePlayer(p1);
			if(!p1.isComputer() || !p2.isComputer()) drawBoard();
			if(gameEnds) {
				//System.out.println("Game has ended. " + p1.getName() + " with " + p1.getMark() + " has won");
				if (p2.isMenace()) menace.UpdateAfterCondition(field, -1);
				if (p1.isMenace()) menace.UpdateAfterCondition(field, 1);
				break;			
			}
			
			
			gameEnds = RunThePlayer(p2);
			if(!p1.isComputer() || !p2.isComputer()) drawBoard();
			if(gameEnds) {
				//System.out.println("Game has ended. " + p2.getName() + " with " + p2.getMark() + " has won");
				if (p2.isMenace()) menace.UpdateAfterCondition(field, 1);
				if (p1.isMenace()) menace.UpdateAfterCondition(field, -1);
				break;
			}
			
		}
		if (!gameEnds) {
			//System.out.println("The game drawed out. No one won");
			if (p2.isMenace()) menace.UpdateAfterCondition(field, 0);
			if (p1.isMenace()) menace.UpdateAfterCondition(field, 0);
		}
	}
	
	private boolean RunThePlayer(Players p) {
		/*
		 * Get the command, mark it and check if the player won
		 * @param p - The player
		 * @param sc - The scanner context
		 * @return if the move won the player the game
		 */
		boolean mark = false;
		boolean gameEnds = false;
		//int[] co = new int[2];
		
		//probability of the human to play in the zone, probBased=true, if in the zone
		boolean probBased = getOptimal(p);
		while(!mark) {
			if (total> 0) {
				int[] co  = getCommand(p, probBased);
				if(co==null) {
					System.out.println("Null!!!!!");
				}
				mark = Mark(co[0], co[1], p);
				if(mark) {
					this.auto=this.auto.children[co[0]][co[1]];
					gameEnds = CheckifWin(p,co[0],co[1],this.field);
					if (p.isMenace()) {
						menace.markConfirmed();
					}
					if (isFinal) logger.info(p.getName() + " marked x=" + Integer.toString(co[0]) + " and y=" + Integer.toString(co[1]));
				}
			}
			else break;
			//System.out.println(Boolean.toString(mark));
		}
		//logger.info(p.getName() + " marked x=" + Integer.toString(co[0]) + " and y=" + Integer.toString(co[1]));
		return gameEnds;
	}
	
	
	public boolean CheckIfWin(char[][] field, TTT_Main ttt) {
		Players p1 = new Players("Player 1",x, true, false);
		Players p2 = new Players("Player 2",o, true, false);
		boolean isOneWon =  (CheckifWin(p1, 0, 0, field) || CheckifWin(p1, 1, 1, field) || CheckifWin(p1, 2, 2, field));
		boolean isTwoWon =  (CheckifWin(p2, 0, 0, field) || CheckifWin(p2, 1, 1, field) || CheckifWin(p2, 2, 2, field));
		return isOneWon || isTwoWon;
	}
	

	private boolean CheckifWin(Players player, int x, int y, char[][] field) {
		/*
		 * Check it the player who just played has won
		 */
		if(WinCondition_Row(player,x,y, field) || WinCondition_Column(player,x,y, field) || WinCondition_Diag(player, field)) return true;
		else return false;
	}
	
	private boolean WinCondition_Row(Players player, int x, int y, char[][] field) {
		/*
		 * Check if the player by the row condition
		 */
		for(int i=0; i<=2; i++) {
			if(field[i][y] != player.getMark()) return false;
		}
		return true;
	}
	
	private boolean WinCondition_Column(Players player, int x, int y, char[][] field) {
		/*
		 * Check if the player by the column condition. Almost same as row condition but written seperately for the sake of readability
		 */
		for(int i=0; i<=2; i++) {
			if(field[x][i] != player.getMark()) return false;
		}
		return true;
	}
	
	private boolean WinCondition_Diag(Players player, char[][] field) {
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
				if(field[i][j] == 0) {
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
	
	private int[] getCommand(Players player, boolean prob) {
		/*
		 * Get the command for the mentioned player
		 * @param player - The player who will be playing this turn
		 * @param sc - The scanner object incase a human is playing
		 * @return - integer array, 0th element is x co-ordinate, 1st element is y co-ordinate
		 */
		if(player.isComputer()) {
			if(player.isMenace()) {
				//System.out.println("Get a command");
				int[] co = menace.getAMove(this.field);
				//System.out.println("Order from Menace");
				return co;
			}
			else {
				//Probabilty based best move
				int[] co;
				if (prob) {
					int i=0,j=0;
					//int rand = new Random().nextInt(9);
					int rand = menace.getRandomNum(0, 8);
					co = menace.getIandJ(rand);
					//co = new int[] {i,j};
				}
				else {
					co= this.auto.getChildWithValue();
				}
				//System.out.println("Order from the player");
				return co;
			}
			
		}
		else {
			System.out.print("Enter the command for " + player.getName() + "-- ");
			String input = sc.nextLine();
			String[] command = new String[2];
			command = input.split(" ");
			int[] co = {Integer.parseInt(command[0]),Integer.parseInt(command[1])};
			//System.out.println("You input " + Integer.toString(co[0]) + " " + Integer.toString(co[1]));
			
			return co;
		}
	}
	
	
	private int GetHumanWay() {
		System.out.println("Enter 1 if human wants to go first, else 2");
		String input = sc.nextLine();
		int turn;
		try {
			turn = Integer.parseInt(input);
		}
		catch(Exception ex){
			turn=0;
		}
		return turn;
	}
	
	private void waitForResults() {
		System.out.println("Look at the results");
		String input = sc.nextLine();
	}
	
	public boolean getTotal(char[][] field) {
		/*
		 * Returns true if the passed field does not have any space left to mark
		 */
		boolean result = true;
		for(int i=0;i<field.length;i++) {
			for(int j=0;j<field[i].length;j++) {
				if(field[i][j]==0) return false;
			}
		}
		return result;
	}
	
	private boolean getOptimal(Players p) {
		/*
		 * Returns true if the "human" might make a mistake in this turn
		 */
		if (!useProb) return false;
		if (p.isComputer() && !p.isMenace()) {
			int rand = menace.getRandomNum(0, 100);
			if (rand>Menace.prob) return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		logger.info("Starting the program");
		sc= new Scanner(System.in);
		TTT_Main tttM = new TTT_Main();
		sc.close();
	}

}
