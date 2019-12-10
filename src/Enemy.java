
public class Enemy {
	
	private int health;
	private int x;
	private int y;
	private boolean alive;
	
	protected String towerPic;
	protected String towerPortrait;
	protected String towerName;
	
	public Enemy() {
		this.health = 100;
		this.x = 0;
		this.y = 0;
		this.alive = true;
	}
	
	public int getHealth() {
		return this.health;
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
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void takeDamage(int damageAmount) {
		this.health -= damageAmount;
	}
	
	public void setAlive(boolean life) {
		this.alive = life;
	}
	
	public boolean getAlive() {
		return this.alive;
	}
}
