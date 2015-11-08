package core;

import java.util.HashMap;

public class Character {
	/**
	 * Character class hold data for one character in the game
	 * It can be a player character or NPC
	 */
	// Class members
	int id;
	int typeId;	// determine which model to use
	String name;
	int health;
	float x, y, z, h, p, r;	// xyz is for position. hpr is for hpr
	HashMap<Integer, Integer> powerMap = new HashMap<Integer, Integer>();
	
	// Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	public float getP() {
		return p;
	}
	public void setP(float p) {
		this.p = p;
	}
	public float getR() {
		return r;
	}
	public void setR(float r) {
		this.r = r;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	/**
	 * set position x y z at the same time
	 */
	public void setPos(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * set hpr
	 */
	public void setHpr(float h, float p, float r) {
		this.h = h;
		this.p = p;
		this.r = r;
	}
	
	/** set position and hpr at the same time
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param h
	 * @param p
	 * @param r
	 */
	public void setPosHpr(float x, float y, float z, float h, float p, float r){
		setPos(x, y, z);
		setHpr(h, p, r);
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	/** calculate the remain health by decreasing x amount 
	 * 
	 * @param damage
	 * @return remaining health
	 */
	public int takeDamage(int damage){
		return health = ((health - damage) > 0) ? health - damage : 0;
	}
	
	/** check if character is dead or not
	 * 
	 * @return true if health > 0. false otherwise
	 */
	public boolean isDead(){
		return health <= 0;
	}
	
	/** pick up power up. either add new one or increase count
	 * 
	 */
	public void pickPowerUp(int powerId, int count){
		if (!powerMap.containsKey(powerId)){
			powerMap.put(powerId, count);
		}else {
			count += powerMap.get(powerId);
			powerMap.put(powerId, count);
		}
	}
	
	/** use power up
	 * 
	 */
	public void usePowerUp(int powerId) throws Exception{
		if (!powerMap.containsKey(powerId))
			throw new Exception("Power map doesn't have power item [" + powerId + "]");
		if (powerMap.get(powerId) > 0)
			powerMap.put(powerId, powerMap.get(powerId) - 1);
	}
	
	public HashMap<Integer, Integer> getPowerMap(){
		return powerMap;
	}
	
	/** check if character has a power up item or not
	 * 
	 * @param powerId
	 * @return
	 */
	public boolean hasPowerUp(int powerId){
		return powerMap.containsKey(powerId) && powerMap.get(powerId) > 0;
	}
}
