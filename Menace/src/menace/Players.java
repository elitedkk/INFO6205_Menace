package menace;

public class Players {
	private String name;
	private char mark;
	private boolean isComputer;
	
	public void setName(String name) {
		this.name=name;
	}
	
	public Players(String name, char mark, boolean isComputer) {
		this.name=name;
		this.mark=mark;
		this.isComputer=isComputer;
	}
	
	public boolean isComputer() {
		return this.isComputer;
	}
	
	 public String getName() {
		 return this.name;
	 }
	 
	 public char getMark() {
		 return this.mark;
	 }

}
