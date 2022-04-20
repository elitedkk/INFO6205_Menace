package menace;

public class Players {
	private String name;
	private String mark;
	
	public void setName(String name) {
		this.name=name;
	}
	
	public Players(String name, String mark) {
		this.name=name;
		this.mark=mark;
	}
	 public String getName() {
		 return this.name;
	 }
	 
	 public String getMark() {
		 return this.mark;
	 }

}
