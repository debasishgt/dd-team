package core;

import java.util.ArrayList;
import java.util.HashMap;

import metadata.Constants;
import networking.response.GameResponse;

public abstract class GameMode extends Thread {
	/**
	 * Abstract class for game mode
	 */
	
	protected HashMap<Long, GameClient> clients = new HashMap<Long, GameClient>();
	//protected HashMap<Long, Player> players = new HashMap<Long, Player>();
	public final static int MAX_PLAYER_COUNT = 2;
	protected boolean isRunning;
	protected GameServer server;
	protected int gamestate;
	
	/** Start the game (dd or rr)
	 * 
	 */
	public abstract void startGame();
	
	/** End the game (dd or rr)
	 * 
	 */
	public abstract void endGame();
	
	/** Add client to the list of this game object
	 * 
	 * @param id
	 * @param client
	 * @throws Exception
	 */
	public void addClient(long id, GameClient client) throws Exception {
		if (gamestate != Constants.GAMEMODE_STATE_LOBBY)
			throw new Exception("Game is already in process.");
		synchronized (clients){
			if (clients.size() >= MAX_PLAYER_COUNT)
				throw new Exception("Game is full.");
			clients.put(id, client);
			// testing purpose. start game right after full
			if (clients.size() == MAX_PLAYER_COUNT)
				startGame();
		}
	}
	
	/** RemoveClient from the list of game object
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void removeClient(long id) throws Exception {
		synchronized (clients){
			if (!clients.containsKey(id))
				throw new Exception("Client not found.");
			clients.remove(id);
		}
	}

	public HashMap<Long, GameClient> getClients() {
		return clients;
	}

	public void setClients(HashMap<Long, GameClient> clients) {
		this.clients = clients;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	/** add a response for all client in the game
	 * 
	 * @param response
	 */
	public void addResponseForAllClients(GameResponse response){
		for (GameClient client: clients.values()){
			client.addResponseForUpdate(response);
		}
	}

	public ArrayList<Player> getPlayers(){
		ArrayList<Player> list = new ArrayList<Player>();
		for (GameClient client: clients.values())
			list.add(client.getPlayer());
		return list;
	}
	
	public GameClient getClient(long id){
		return clients.get(id);
	}
	
	public boolean isFull(){
		return clients.size() >= MAX_PLAYER_COUNT;
	}
	
	public boolean isEmpty(){
		return clients.isEmpty();
	}
	
	public String getClientNames(){
		String str = "";
		for (GameClient client: clients.values()){
			str += "[" + client.getId() + ":" +client.getPlayer().getUsername() + "]" + " ";
		}
		return str.trim();
	}
	
	/** set all clients' gamestate to one state
	 * 
	 * @param gamestate
	 */
	public void setAllClientGamestate(int gamestate){
		for (GameClient client: clients.values()){
			client.setGamestate(gamestate);
		}
	}
	
	@Override
	public String toString(){
		return "Game [" + getId() + "]";
	}

	public boolean isFinished() {
		return gamestate == Constants.GAMEMODE_STATE_ENDED;
	}

	public int getGamestate() {
		return gamestate;
	}

	public void setGameState(int state) {
		this.gamestate = state;
	}
	
	
}
