import java.util.ArrayList;

public class Tower {
	
	protected String towerPic;
	protected String towerPortrait;
	protected String towerName;
	private int x;
	private int y;
	protected int cost = 1;
	protected int attackPower = 0;
	private ArrayList<Enemy> enemiesToAttack = new ArrayList<Enemy>();
	
	public Tower() {
		this.x = 0;
		this.y = 0;
		this.attackPower = 1;
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
	
	public int getCost() {
		return this.cost;
	}
	
	public int getAttackPower() {
		return this.attackPower;
	}
	
	public void addEnemy(Enemy e) {
		enemiesToAttack.add(e);
	}
	
	public void clearEnemies() {
		enemiesToAttack.clear();
	}
	
	public ArrayList<Enemy> getEnemiesToAttack() {
		return enemiesToAttack;
	}
}
