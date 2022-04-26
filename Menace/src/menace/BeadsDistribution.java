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
	
	public BeadsDistribution(int index, int first) {
		beads = new int[9];
		Arrays.fill(beads, Menace.alpha);
		sum=(beads.length*Menace.alpha)+first;
		beads[index]+=first;
	}
	
	public void updateBeads(int index, int update) {
		int diff = -1;
		if((beads[index]+update)<0) {
			diff = beads[index];
			beads[index]=0;
		}
		else beads[index]+=update;
		
		if (diff!=-1) {
			sum-=diff;
		}
		else sum+=update;
	}
	
	public int getByProbability(int random) {
		int tempSum=0;
		for(int i=0; i<9; i++) {
			tempSum+=beads[i];
			if (random<=tempSum) return i;
		}
		return -1;
	}
}
