package menace;

public class Players {
	private String name;
	private String mark;
	private boolean isComputer;
	
	public void setName(String name) {
		this.name=name;
	}
	
	public Players(String name, String mark, boolean isComputer) {
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
	 
	 public String getMark() {
		 return this.mark;
	 }

}
