package menace;

public class Players {
	/*
	 * Data class for Players
	 */
	private String name;
	private char mark;
	private boolean isComputer;
	private boolean isMenace;
	
	public void setName(String name) {
		this.name=name;
	}
	
	public Players(String name, char mark, boolean isComputer, boolean isMenace) {
		this.name=name;
		this.mark=mark;
		this.isComputer=isComputer;
		this.isMenace = isMenace;
	}
	
	public boolean isComputer() {
		return this.isComputer;
	}
	
	public boolean isMenace() {
		return this.isMenace;
	}
	
	 public String getName() {
		 return this.name;
	 }
	 
	 public char getMark() {
		 return this.mark;
	 }

}
