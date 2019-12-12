import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class TowerDefenseTests {
	
	@Test
	void testMakeModel() {
		TowerDefenseModel model = new TowerDefenseModel();
		assertFalse(model == null);
	}
	
	@Test
	void testMakeController() {
		TowerDefenseModel model = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model);
		assertFalse(con == null);
	}

	@Test
	void testChangeModel() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		TowerDefenseModel model2 = new TowerDefenseModel();
		con.setModel(model2);
		assertEquals(con.getModel(), model2);
	}
	
	@Test
	void testGettingRoad() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		assertEquals(con.getRoad(), model1.getRoad());
	}
	
	@Test
	void testGettingTowerMap() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		assertEquals(con.getTowerMap(), model1.getTowerMap());
	}
	
	
	@Test
	void testStartRound() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		con.startRound();
		assertEquals("attack", con.getGamePhase());
	}
	
	
	@Test
	void testIncreasingGameSpeed1() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		model1.increaseGameSpeed();
		assertEquals(80, model1.getGameSpeed());
	}
	
	@Test
	void testIncreasingGameSpeed2() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		model1.increaseGameSpeed();
		model1.increaseGameSpeed();
		model1.increaseGameSpeed();
		assertEquals(120, model1.getGameSpeed());
	}
	
	@Test
	void testControllerSetRoad() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		con.setRoad(road);
		assertEquals(road, model1.getRoad());
	}
	
	@Test
	void testFindNext() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		GuardRickEnemy enemy = new GuardRickEnemy();
		Road1 road = new Road1();
		model1.setRoad(road);
		int healthBefore = model1.getHealth();
		model1.findNext(enemy);
		assertNotEquals(healthBefore, model1.getHealth());
	}
	
	@Test
	void testAddTower() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		RickTower tower = new RickTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		con.addTower(tower, 0, 0);
		assertFalse(model1.getTowerAtPost(0, 0) == null);
	}
	
	@Test
	void testAddTowerNoMoney() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		model1.setMoney(0);
		RickTower tower = new RickTower();
		con.addTower(tower, 0, 0);
		assertFalse(model1.getTowerAtPost(0, 0) == tower);
	}
	
	@Test
	void testSellTower() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		RickTower tower = new RickTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		con.addTower(tower, 0, 0);
		con.sellTower(0, 0);
		assertFalse(model1.getTowerAtPost(0, 0) == tower);
	}
	
	@Test
	void testChangeGameSpeed() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		int speedNow = model1.getGameSpeed();
		con.increaseGameSpeed();
		assertFalse(model1.getGameSpeed() == speedNow);
	}
	
	@Test
	void testChangePaused() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		boolean pauseStatus = model1.getPaused();
		con.changePaused();
		assertFalse(model1.getPaused() == pauseStatus);
	}
	
	@Test
	void testFindTowerAt() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		RickTower tower = new RickTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		con.addTower(tower, 0, 0);
		con.findTowerAtPosition(0, 0);
		assertTrue(model1.towerAtPosition(0, 0));
	}
	
	@Test
	void testFindTowerAtWithNoTower() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		RickTower tower = new RickTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		con.addTower(tower, 0, 0);
		assertFalse(model1.towerAtPosition(1, 1));
	}
	
	@Test
	void testGetHealth() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		assertEquals(con.getHealth(), 100);
	}
	
	@Test
	void testDead() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.createEnemy(0);
		model1.setHealth(-1);
		con.startRound();
		assertEquals(con.getHealth(), -1);
	}
	
	@Test
	void testAddMoney() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		int moneyBefore = con.getMoney();
		con.addAttackMoney();
		assertNotEquals(con.getMoney(), moneyBefore);
	}
	
	@Test
	void testTakeDamage() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		int healthBefore = con.getHealth();
		con.takeHealth();
		assertNotEquals(con.getHealth(), healthBefore);
	}
	
	@Test
	void testAddingEnemies() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.createEnemy(1);
		model1.createEnemy(2);
		model1.createEnemy(3);
		model1.createEnemy(4);
		model1.createEnemy(5);
		assertEquals(model1.getEnemies().size(), 5);
	}
	
	@Test
	void testAddingEnemiesButDead() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		model1.setHealth(-1);
		model1.createEnemy(1);
		model1.createEnemy(2);
		model1.createEnemy(3);
		model1.createEnemy(4);
		model1.createEnemy(5);
		assertEquals(model1.getEnemies().size(), 0);
	}
	
	@Test
	void testTowerAttack() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		RickTower tower = new RickTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.addTower(tower, 0, 14);
		model1.createEnemy(1);
		model1.moveEnemies();
		ArrayList<Enemy> enemies = model1.getEnemies();
		int healthBefore = enemies.get(0).getHealth();
		con.towerAttack();
		assertNotEquals(enemies.get(0).getHealth(), healthBefore);
	}
	
	@Test
	void testTowerAttackLeft() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		RickTower tower = new RickTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.addTower(tower, 1, 14);
		model1.createEnemy(1);
		model1.moveEnemies();
		ArrayList<Enemy> enemies = model1.getEnemies();
		int healthBefore = enemies.get(0).getHealth();
		con.towerAttack();
		assertNotEquals(enemies.get(0).getHealth(), healthBefore);
	}
	
	@Test
	void testTowerAttackRight() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		RickTower tower = new RickTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.addTower(tower, 1, 12);
		model1.createEnemy(1);
		model1.moveEnemies();
		ArrayList<Enemy> enemies = model1.getEnemies();
		int healthBefore = enemies.get(0).getHealth();
		con.towerAttack();
		assertNotEquals(enemies.get(0).getHealth(), healthBefore);
	}
	
	@Test
	void testBirdPersonAttack() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		BirdPersonTower tower = new BirdPersonTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.addTower(tower, 0, 0);
		model1.createEnemy(1);
		model1.moveEnemies();
		ArrayList<Enemy> enemies = model1.getEnemies();
		int healthBefore = enemies.get(0).getHealth();
		con.towerAttack();
		assertNotEquals(enemies.get(0).getHealth(), healthBefore);
	}
	
	@Test
	void testMeeseeksAttack() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		MeeseeksTower tower = new MeeseeksTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.addTower(tower, 0, 14);
		model1.createEnemy(1);
		model1.moveEnemies();
		ArrayList<Enemy> enemies = model1.getEnemies();
		int healthBefore = enemies.get(0).getHealth();
		con.towerAttack(); // uses random, cannot fully check
		assertTrue(enemies.size() == 0 || enemies.get(0).getHealth() != healthBefore);
	}
	
	@Test
	void testSquanchyAttack() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		SquanchyTower tower = new SquanchyTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.addTower(tower, 0, 14);
		model1.createEnemy(1);
		model1.moveEnemies();
		ArrayList<Enemy> enemies = model1.getEnemies();
		int healthBefore = enemies.get(0).getHealth();
		con.towerAttack(); // uses random, cannot fully check
		assertNotEquals(enemies.get(0).getHealth(), healthBefore);
	}
	
	@Test
	void testJerryDoofusRickAttack() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		JerryTower tower = new JerryTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.addTower(tower, 0, 14);
		model1.createEnemy(3);
		model1.moveEnemies();
		ArrayList<Enemy> enemies = model1.getEnemies();
		con.towerAttack();
		assertEquals(enemies.size(), 0);
	}
	
	@Test
	void testKillEnemy() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		RickTower tower = new RickTower();
		Road1 road = new Road1();
		model1.setRoad(road);
		model1.addTower(tower, 0, 14);
		model1.createEnemy(1);
		model1.moveEnemies();
		ArrayList<Enemy> enemies = model1.getEnemies();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		con.towerAttack();
		assertEquals(enemies.size(), 0);
	}
	
	@Test
	void testDefaultEnemyPortrait() {
		Enemy enemy = new Enemy();
		assertEquals(enemy.getTowerPortrait(), null);
	}
	
	@Test
	void testDefaultEnemyPic() {
		Enemy enemy = new Enemy();
		assertEquals(enemy.getTowerPic(), null);
	}
	
	@Test
	void testDefaultEnemyName() {
		Enemy enemy = new Enemy();
		assertEquals(enemy.getTowerName(), null);
	}
	
	@Test
	void testDefaultEnemyX() {
		Enemy enemy = new Enemy();
		enemy.setX(2);
		assertEquals(enemy.getX(), 2);
	}
	
	@Test
	void testDefaultEnemyY() {
		Enemy enemy = new Enemy();
		enemy.setY(2);
		assertEquals(enemy.getY(), 2);
	}
	
	@Test
	void testToxicRickHitOnce() {
		ToxicRickEnemy tox = new ToxicRickEnemy();
		int healthBefore = tox.getHealth();
		tox.takeDamage(10);
		assertEquals(tox.getHealth(), healthBefore);
	}
	
	@Test
	void testToxicRickHitFourTimes() {
		ToxicRickEnemy tox = new ToxicRickEnemy();
		int healthBefore = tox.getHealth();
		tox.takeDamage(10);
		tox.takeDamage(10);
		tox.takeDamage(10);
		tox.takeDamage(10);
		assertNotEquals(tox.getHealth(), healthBefore);
	}
	
	@Test
	void testDefaultTowerPortrait() {
		Tower tow = new Tower();
		assertEquals(tow.getTowerPortrait(), null);
	}
	
	@Test
	void testDefaultTowerPic() {
		Tower tow = new Tower();
		assertEquals(tow.getTowerPic(), null);
	}
	
	@Test
	void testDefaultTowerName() {
		Tower tow = new Tower();
		assertEquals(tow.getTowerName(), null);
	}
	
	@Test
	void testDefaultTowerAttackPower() {
		Tower tow = new Tower();
		assertEquals(tow.getAttackPower(), 1);
	}
	
	@Test
	void testDefaultTowerSetCoords() {
		Tower tow = new Tower();
		tow.setCords(1, 1);
		assertEquals(tow.getX(), 1);
		assertEquals(tow.getY(), 1);
	}
	
	@Test
	void testDefaultTowerSetX() {
		Tower tow = new Tower();
		tow.setX(2);
		assertEquals(tow.getX(), 2);
	}
	
	@Test
	void testDefaultTowerSetY() {
		Tower tow = new Tower();
		tow.setY(2);
		assertEquals(tow.getY(), 2);
	}
	
	@Test
	void testMortyTower() {
		MortyTower tow = new MortyTower();
		assertEquals(tow.getAttackPower(), 5);
	}
	
	@Test
	void testGuardRick() {
		GuardRickEnemy rick = new GuardRickEnemy();
		rick.takeDamage(5); // uses random, call repeatedly to test
		rick.takeDamage(5);
		rick.takeDamage(5);
		rick.takeDamage(5); 

		assertNotEquals(rick, null);
	}
	
	@Test
	void testEvilRickHitOnce() {
		EvilRickEnemy rick = new EvilRickEnemy();
		int healthBefore = rick.getHealth();
		rick.takeDamage(5);
		assertEquals(rick.getHealth(), healthBefore);
	}
	
	@Test
	void testEvilRickHitTwice() {
		EvilRickEnemy rick = new EvilRickEnemy();
		int healthBefore = rick.getHealth();
		rick.takeDamage(5);
		rick.takeDamage(5);
		assertNotEquals(rick.getHealth(), healthBefore);
	}
	
	@Test
	void testGetRound() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		assertEquals(con.getRound(), 1);
	}
	
	@Test
	void testGetRoad1Val() {
		Road1 road = new Road1();
		assertEquals(road.getValAtPos(0, 0), 0);
	}
	
	@Test
	void testRoad2() {
		Road2 road = new Road2();
		assertFalse(road == null);
	}
	
	@Test
	void testGetRoad2Val() {
		Road2 road = new Road2();
		assertEquals(road.getValAtPos(0, 0), 0);
	}
	
	@Test
	void testGetGameSpeed() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		assertEquals(con.getGameSpeed(), 120);
	}
	
	@Test
	void testAddBirdPerson() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		BirdPersonTower tower = new BirdPersonTower();
		con.addTower(tower, 0, 0);
		assertTrue(model1.towerAtPosition(0, 0));
	}
	
	@Test
	void testAddJerry() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		JerryTower tower = new JerryTower();
		con.addTower(tower, 0, 0);
		assertTrue(model1.towerAtPosition(0,0));
	}
	
	@Test
	void testAddMeeseeks() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		MeeseeksTower tower = new MeeseeksTower();
		con.addTower(tower, 0, 0);
		assertTrue(model1.towerAtPosition(0,0));
	}
	
	@Test
	void testAddMorty() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		MortyTower tower = new MortyTower();
		con.addTower(tower, 0, 0);
		assertTrue(model1.towerAtPosition(0,0));
	}
	
	@Test
	void testAddRick() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		RickTower tower = new RickTower();
		con.addTower(tower, 0, 0);
		assertTrue(model1.towerAtPosition(0,0));
	}
	
	@Test
	void testAddSquanch() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		SquanchyTower tower = new SquanchyTower();
		con.addTower(tower, 0, 0);
		assertTrue(model1.towerAtPosition(0,0));
	}
	
	@Test
	void testAddEnemyToTowerAttackList() {
		Tower tower = new Tower();
		Enemy enemy = new Enemy();
		tower.addEnemy(enemy);
		assertEquals(tower.getEnemiesToAttack().size(), 1);
	}
	
	@Test
	void testClearEnemiesToTowerAttackList() {
		Tower tower = new Tower();
		Enemy enemy = new Enemy();
		tower.addEnemy(enemy);
		tower.clearEnemies();
		assertEquals(tower.getEnemiesToAttack().size(), 0);
	}
	
	@Test
	void testEnemyBelow() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		RickTower tower = new RickTower();
		model1.createEnemy(1);
		model1.addTower(tower, 0, 11);
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.towerAttack();
		assertEquals(tower.getEnemiesToAttack().size(), 1);
	}
	
	@Test
	void testEnemyAbove() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		RickTower tower = new RickTower();
		model1.createEnemy(1);
		model1.addTower(tower, 4, 13);
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.towerAttack();
		assertEquals(tower.getEnemiesToAttack().size(), 1);
	}
	
	@Test
	void testEnemyLowerRightDiagonal() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		RickTower tower = new RickTower();
		model1.createEnemy(1);
		model1.addTower(tower, 2, 12);
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.towerAttack();
		assertEquals(tower.getEnemiesToAttack().size(), 1);
	}
	
	@Test
	void testEnemyUpperLeftDiagonal() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		RickTower tower = new RickTower();
		model1.createEnemy(1);
		model1.addTower(tower, 4, 14);
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.towerAttack();
		assertEquals(tower.getEnemiesToAttack().size(), 1);
	}
	
	@Test
	void testEnemyUpperRightDiagonal() {
		TowerDefenseModel model1 = new TowerDefenseModel();
		TowerDefenseController con = new TowerDefenseController(model1);
		Road1 road = new Road1();
		model1.setRoad(road);
		RickTower tower = new RickTower();
		model1.createEnemy(1);
		model1.addTower(tower, 4, 12);
		model1.moveEnemies();
		model1.moveEnemies();
		model1.moveEnemies();
		model1.towerAttack();
		assertEquals(tower.getEnemiesToAttack().size(), 1);
	}
}

