package menace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;


public class Menace {
	public static int trainingGames = 10;
	public static int totalwin=0;
	public static int totallose=0;
	public static int totaldraw=0;
	
	public static byte win = 1;
	public static byte lose = -1;
	public static byte draw = 0;
	
	public static int alpha=10; //Starting number of beads
	public static int beta=3; //Adding beads after winning
	public static int gamma=-2; //Removing beads after losing
	public static int delta=1; //Adding beads after drawing
	public static int prob = 75; //Probability of human to take optimum decision
	public TTT_Main ttt;
	public Stack<SitAndChoice> keys;
	
	char[][] field = new char[3][3];
	Map<String, BeadsDistribution> memory;
	
	
	public Menace(TTT_Main ttt) {
		memory = new HashMap<String, BeadsDistribution>();
		this.ttt = ttt;
		keys = new Stack<SitAndChoice>();
	}
	
	public void updateMapAfterCondition(String situation, int index, int points) {
		if(memory.containsKey(situation)) {
			BeadsDistribution bd = memory.get(situation);
			bd.updateBeads(index,points);
		}
		else {
			BeadsDistribution bd = new BeadsDistribution(index,points);
			memory.put(situation, bd);
		}
	}
	
	public int [] getAMove(char[][] field) {
		String situation = createStringFromField(field);
		int[] co = checkMapForMove(situation);
		keys.push(new SitAndChoice(situation, co[0], co[1]));
		return co;
	}
	
	private int[] checkMapForMove(String situation) {
		BeadsDistribution bd;
		if(memory.containsKey(situation)) {
			bd = memory.get(situation);
		}
		else {
			bd = new BeadsDistribution();
			memory.put(situation, bd);
		}
		int getSum = bd.sum;
		int rand = getRandomNum(0,getSum);
		System.out.println("Random number = " + Integer.toString(rand));
		int index = bd.getByProbability(rand);
		return getIandJ(index);
	}
	
	public String createStringFromField(char[][] field) {
		String create="";
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(field[i][j]==0) {
					create+="0";
				}
				else if (field[i][j]==ttt.x) {
					create+="x";
				}
				else {
					create+="o";
				}
			}
		}
		return create;
	}
	
	public char[][] createFieldFromString(String key) {
		char[][] field = new char[3][3];
		for(int i=0; i<key.length(); i++) {
			field[i/3][i%3] = key.charAt(i);
		}
		return field;
	}
	
	public void UpdateAfterCondition(char[][] field, byte condition) {
		String f = createStringFromField(field);
		int points = 0;
		if (condition == 0) {
			//draw
			System.out.println("Won at " + f);
			points = Menace.delta;
		}
		else if(condition == 1) {
			//win
			points = Menace.beta;
			System.out.println("Loss at " + f);
		}
		else {
			//lose
			points = Menace.gamma;
			System.out.println("Draw at " + f);
		}
		int index;
		String situation;
		while(!keys.empty()) {
			SitAndChoice sac = keys.peek();
			index = getIndex(sac.x,sac.y);
			situation = sac.situation;
			updateMapAfterCondition(situation, index, points);
			keys.pop();
		}
		
	}
	
	private int getRandomNum(int min, int max) {
	    int randomNum = (new Random()).nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	private int[] getIandJ(int index) {
		return new int[] {index/3, index%3};
	}
	
	private int getIndex(int x, int y) {
		return (x*3) + y;
	}
}
