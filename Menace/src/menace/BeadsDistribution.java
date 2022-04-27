package menace;

import java.util.Arrays;

public class BeadsDistribution {
	public int[] beads;
	public int sum;
	
	
	public BeadsDistribution() {
		beads = new int[9];
		Arrays.fill(beads, Menace.alpha);
		sum=beads.length*Menace.alpha;
	}
	
	public BeadsDistribution(String situation) {
		beads = new int[9];
		Arrays.fill(beads, Menace.alpha);
		sum=beads.length*Menace.alpha;
		for(int i=0; i<beads.length;i++) {
			if (situation.charAt(i)=='x' || situation.charAt(i)=='X' || situation.charAt(i)=='o' || situation.charAt(i)=='O') {
				sum-=beads[i];
				beads[i]=0;
			}
		}
	}
	
	public BeadsDistribution(int index, int first) {
		beads = new int[9];
		Arrays.fill(beads, Menace.alpha);
		sum=(beads.length*Menace.alpha)+first;
		beads[index]+=first;
	}
	
	public void updateBeads(int index, int update) {
		if((beads[index]+update)<0) {
			sum = sum - beads[index];
			beads[index]=0;
		}
		else {
			beads[index]+=update;
			sum+=update;
		}
		
	}
	
	
	public int getByProbability(int random) {
		int tempSum=0;
		for(int i=0; i<9; i++) {
			//System.out.println("at " + Integer.toString(i) + ", number of beads " + Integer.toString(beads[i]));
			tempSum+=beads[i];
			if (random<=tempSum) return i;
		}
		return -1;
	}
}
