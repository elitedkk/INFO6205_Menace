package menace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Menace {
	private static Logger logger = LoggerFactory.getLogger(TTT_Main.class);
	
	public static int trainingGames = 100000000;
	public static int totalwin=0;
	public static int totallose=0;
	public static int totaldraw=0;
	public static int runninggame=0;
	
	public static int win = 1;
	public static int lose = -1;
	public static int draw = 0;
	
	private int runwin=0;
	private int runlose=0;
	private int rundraw=0;
	
	private String sits;
	private int xs;
	private int ys;
	private int loopThreshold = 10;
	
	public static int alpha=10000; //Starting number of beads
	public static int beta=20; //Adding beads after winning
	public static int gamma=-1; //Removing beads after losing
	public static int delta=5; //Adding beads after drawing
	public static int prob = 70; //Probability of human to take optimum decision
	public TTT_Main ttt;
	//public Stack<SitAndChoice> keys;
	private List<SitAndChoice> keys;
	
	char[][] field = new char[3][3];
	Map<String, BeadsDistribution> memory;
	
	
	public Menace(TTT_Main ttt) {
		memory = new HashMap<String, BeadsDistribution>();
		this.ttt = ttt;
		keys = new ArrayList<SitAndChoice>();
		
	}
	
	public void updateMapAfterCondition(String situation, int index, int points) {
		if(memory.containsKey(situation)) {
			BeadsDistribution bd = memory.get(situation);
			bd.updateBeads(index,points);
			logger.debug("Updating beads at " + situation + " at index= " + Integer.toString(index) + " by " + Integer.toString(points));
		}
		else {
			BeadsDistribution bd = new BeadsDistribution(index,points);
			memory.put(situation, bd);
			logger.debug("Adding starting beads at " + situation + " at index= " + Integer.toString(index) + " by " + Integer.toString(points));
		}
	}
	
	public int [] getAMove(char[][] field) {
		//System.out.println("Getting a move");
		String situation = createStringFromField(field);
		
		int[] co = checkMapForMove(situation);
		
		//System.out.println("number of el in stack= " + Integer.toString(keys.size()));
		xs = co[0];
		ys = co[1];
		sits = situation;
		
		//System.out.println("Got a move? " + Integer.toString(xs) + " " + Integer.toString(ys));
		//keys.push(new SitAndChoice(situation, co[0], co[1]));
		return co;
	}
	
	public void markConfirmed() {
		//System.out.println("List size = " + Integer.toString(keys.size()));
		//keys.push(new SitAndChoice(sits, xs, ys));
		keys.add(new SitAndChoice(sits, xs, ys));
		loopThreshold=10;
	}
	
	private int[] checkMapForMove(String situation) {
		BeadsDistribution bd;
		loopThreshold--;
		if(memory.containsKey(situation)) {
			bd = memory.get(situation);
		}
		else {
			bd = new BeadsDistribution();
			memory.put(situation, bd);
		}
		int getSum = bd.sum;
		if (loopThreshold<0) {
			
			int index = getRandomNum(0,8);
			return getIandJ(index);
		}
		//System.out.println("Sum= " + Integer.toString(getSum));
		int rand = getRandomNum(0,getSum);
		//System.out.println("Random number = " + Integer.toString(rand));
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
	
	public void UpdateAfterCondition(char[][] field, int condition) {
		String f = createStringFromField(field);
		int points = 0;
		runninggame++;
		if (condition == 0) {
			//draw
			logger.debug("Draw at " + f);
			//System.out.println("Draw at " + f);
			totaldraw++;
			points = Menace.delta;
		}
		else if(condition == 1) {
			//win
			points = Menace.beta;
			totalwin++;
			logger.debug("Win at " + f);
			//System.out.println("Win at " + f);
		}
		else {
			//lose
			points = Menace.gamma;
			totallose++;
			logger.debug("Loss at " + f);
			//System.out.println("Loss at " + f);
		}
		int index;
		String situation;
		if(runninggame%10000==0) {
			logger.info("For hundred games: -> wins= " + Integer.toString(totalwin-runwin) + " losses= " + Integer.toString(totallose-runlose) + " draws= " + Integer.toString(totaldraw-rundraw));
			rundraw=totaldraw;
			runwin=totalwin;
			runlose=totallose;
		}
		while(!keys.isEmpty()) {
			//System.out.println("Emptying list, size = " + Integer.toString(keys.size()));
			SitAndChoice sac = keys.get(0);
			index = getIndex(sac.x,sac.y);
			situation = sac.situation;
			updateMapAfterCondition(situation, index, points);
			keys.remove(sac);
			//System.out.println("Emptying stack, size = " + Integer.toString(keys.size()));
		}
		
	}
	
	public int getRandomNum(int min, int max) {
	    int randomNum = (new Random()).nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public int[] getIandJ(int index) {
		return new int[] {index/3, index%3};
	}
	
	private int getIndex(int x, int y) {
		return (x*3) + y;
	}
}
