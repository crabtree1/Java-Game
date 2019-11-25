
public class Tower {
	
	protected String towerPic;
	protected String towerPortrait;
	protected String towerName;
	private int x;
	private int y;
	
	public Tower() {
		this.x = 0;
		this.y = 0;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setCords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String getTowerPic() {
		return towerPic;
	}
	
	public String getTowerPortrait() {
		return towerPortrait;
	}
	
	public String getTowerName() {
		return towerName;
	}
	
}