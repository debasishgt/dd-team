package core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import metadata.Constants;

import java.lang.reflect.*;

import networking.response.GameResponse;
import networking.response.ResponseLogin;

public class FakeGameClient extends GameClient{
	/** This class will be used to simulate GameClient
	 * NOTE: gamestate and player are not directly accessible from this class
	 * must use getters/setters to access
	 */
	private long lastActivity;
	// Method maps to be randomized on
	ArrayList<Method> methods = new ArrayList<Method>();
	ArrayList<Integer> lobbyMethodIndexes = new ArrayList<Integer>();
	ArrayList<Integer> gameLobbyMethodIndexes = new ArrayList<Integer>();
	ArrayList<Integer> gameCountdownMethodIndexes = new ArrayList<Integer>();
	ArrayList<Integer> gamePlayingMethodIndexes = new ArrayList<Integer>();
	ArrayList<Integer> gameFinishedMethodIndexes = new ArrayList<Integer>();
	
	Queue<GameResponse> responses = new LinkedList<GameResponse>();	// store response to broadcast
	
	/** Constructor
	 * FakeGameClient does not need the Socket like the GameClient
	 * So sending 'null' as the Socket parameter is good enough 
	 * @param server
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public FakeGameClient(GameServer server) throws IOException, NoSuchMethodException, SecurityException{
		super(null, server);
		// generate method maps
		methods.add(FakeGameClient.class.getMethod("simulateLogin", null));	//0
		methods.add(FakeGameClient.class.getMethod("simulateChat", null));	//1
		methods.add(FakeGameClient.class.getMethod("simulateJoinGame", null));	//2
		methods.add(FakeGameClient.class.getMethod("simulateLogout", null));	//3
		methods.add(FakeGameClient.class.getMethod("simulatePowerPickup", null));	//4
		methods.add(FakeGameClient.class.getMethod("simulatePowerUp", null));	//5
		methods.add(FakeGameClient.class.getMethod("simulateQuitGame", null));	//6
		methods.add(FakeGameClient.class.getMethod("simulateRandomMove", null));	//7
		methods.add(FakeGameClient.class.getMethod("simulateReady", null));	//8
		methods.add(FakeGameClient.class.getMethod("simulateDead", null));	//9
		methods.add(FakeGameClient.class.getMethod("simulateCreateGame", null));	//10
		methods.add(FakeGameClient.class.getMethod("simulateQuitGame", null));	//11
		
		//methods.add(FakeGameClient.class.getMethod("simulateScriptedMove", null));	//12
		
		// just logged in. strolling in lobby
		lobbyMethodIndexes.add(1);	//chat
		lobbyMethodIndexes.add(10);	// create game
		lobbyMethodIndexes.add(2);	// join game
		if (Constants.SIMULATION_AUTO_LOGOUT)
			lobbyMethodIndexes.add(3);	// log out

		
		// inside game lobby. waiting for
		gameLobbyMethodIndexes.add(1);	// chat
		if (Constants.SIMULATION_AUTO_LOGOUT)
			gameLobbyMethodIndexes.add(3);
		
		// finished loading game (waiting for count down)
		gameCountdownMethodIndexes.add(1);	// chat
		if (Constants.SIMULATION_AUTO_LOGOUT)
			gameCountdownMethodIndexes.add(3);
		
		// game is on. can do: chat, move, (leave game? no protocol yet), log out, power up, pick power item, dead
		gamePlayingMethodIndexes.add(1);	// chat
		gamePlayingMethodIndexes.add(7);	// random move
		gamePlayingMethodIndexes.add(4);	// power pick up
		gamePlayingMethodIndexes.add(5);	// power up
		gamePlayingMethodIndexes.add(9);	// dead
		if (Constants.SIMULATION_AUTO_LOGOUT)
			gamePlayingMethodIndexes.add(3);
		
		// game over. can do: chat, back to lobby, move, log out
		gameFinishedMethodIndexes.add(1);	// chat
		gameFinishedMethodIndexes.add(7);	// random move
		gameFinishedMethodIndexes.add(11);	// leave/quit game
		if (Constants.SIMULATION_AUTO_LOGOUT)
			gameFinishedMethodIndexes.add(3);

		getPlayer().setId(100 + (int)getId());
		getPlayer().setUsername("FakeUser" + getPlayer().getId());
	}
	
	/**
	 * Simulate player's actions
	 * socket reading/sending is not needed
	 */
	//@Override
	public void run(){
		isPlaying = true;
		lastActivity = System.currentTimeMillis();
		while (isPlaying){
			try {
				// player's simulating should go inside this scope
				if (System.currentTimeMillis() - lastActivity > Constants.SIMULATION_LOBBY_DELAY * 1000){
					simulateRandomWithoutError();
					lastActivity = System.currentTimeMillis();
				}
				//simulateRandomWithError();
				this.sleep(1);	// this should let cpu 'rest' a bit
				
				// have a response to broadcast to other real players
				// do it here
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/** dummy, do nothing
	 * 
	 * @return
	 */
	public boolean addResponseForUpdate(){
		return true;
	}
	
	/** Override GameClient's counterpart
	 * 
	 */
	public Queue<GameResponse> getUpdates(){
		return new LinkedList<GameResponse>();}
	
	/** Override GameClient's counterpart
	 * 
	 */
	public OutputStream getOutputStream(){
		return System.out;
	}
	
	/** Override GameClient's counterpart
	 * 
	 */
	public void clearUpdateBuffer(){}
	
	/** Override GameClient's counterpart
	 * 
	 */
	public void flushResponse(){}
	
	/** Override GameClient's counterpart
	 * 
	 */
	public String getIP(){
		return "Fake IP";
	}
	
	/** Simulate player login
	 * @return
	 */
	public void simulateLogin(){
		getServer().addClientToLobby(this);
		setGamestate(Constants.GAMESTATE_LOBBY);
		System.out.printf("Fake client [%d:%s] logged in\n", getId(), getPlayer().getUsername());
		lastActivity = System.currentTimeMillis();
		// need to generate player login notice for other players
	}
	/** Simulate player logout
	 * Should this be in the FakeGameClient or FakePlayer?
	 * Since we will have to remove this object from the list
	 */
	public void simulateLogout(){
		System.out.printf(this.toString() + " log out\n");
	}
	
	/** Simulate player join the game in the lobby
	 * When joining the game from the lobby, player stay there until
	 * server tell client to load the game
	 */
	public void simulateJoinGame(){
		// go through the list of games to see which one is open to join
		for (GameMode game: getServer().getGameList()){
			if (!game.isFinished() && !game.isRunning() && !game.isFull()){
				getServer().addClientToGame(this, game);
				return;
			}
		}
	}
	
	/** Simulate player quit game(demolition derby, not the actual game)
	 *  
	 */
	public void simulateQuitGame(){
		System.out.println(this + " left " + getGame());
		getServer().moveClientFromGameToLobby(getId(), getGame());
	}
	
	/** Randomly move around
	 * 
	 */
	public void simulateRandomMove(){
		Random random = new Random();
		float x = random.nextFloat() * Constants.SIMULATION_PLAYER_MAX_SPEED;
		float y = random.nextFloat() * Constants.SIMULATION_PLAYER_MAX_SPEED;
		getPlayer().getCharacter().setPos(x, y, getPlayer().getCharacter().getZ());
		System.out.println(this + " move to <" + x + ", " + y + ", " + getPlayer().getCharacter().getZ());
		// generate response here
	}

	/** Move around, but not randomly
	 * it can be any pattern
	 */
	public void simulateScriptedMove(){
		System.out.printf(this.toString() + " execute scipted move\n");
	}
	
	/** Simulate chat
	 * 
	 */
	public void simulateChat(){
		System.out.printf(this.toString() + " sent chat\n");
		// generate chat here
	}
	
	/** Simulate power up
	 * 
	 */
	public void simulatePowerUp(){
		// for now just pick up powerId 1
		if (getPlayer().getCharacter().hasPowerUp(1)) {
			try {
				getPlayer().getCharacter().usePowerUp(1);
				// generate response here
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	/** Simulate picking up power item
	 * 
	 */
	public void simulatePowerPickup(){
		System.out.printf(this.toString() + " pick up item\n");
	}
	
	/** Randomly pick one of the simulation
	 * Do not care about logical error
	 * do not care about gamestate.
	 * WARNING: only use this when the client keeps track of gamestate or 
	 * some kind of packet validation
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public void simulateRandomWithError() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Random random = new Random();
		methods.get(random.nextInt() % methods.size()).invoke(this);
	}
	
	/** Randomly pick one of the simulation
	 * Avoid logical error as much as possible
	 * use gamestate to determine the set of possible actions
	 * all methods inside should add a response to the response queue to broadcase later
	 */
	public void simulateRandomWithoutError(){
		Random random = new Random();
		if (random.nextInt(2) == 0)		// more randomness. can skip a turn
			return;
		try {
			switch (getGamestate()){
			case Constants.GAMESTATE_NOT_LOGGED_IN:
				// not logged in yet. log in now
				simulateLogin();
				break;
			case Constants.GAMESTATE_LOBBY:
				// in lobby. can do: chat, create game, join game, log out
				methods.get(lobbyMethodIndexes.get(random.nextInt(lobbyMethodIndexes.size()))).invoke(this);
				break;
			case Constants.GAMESTATE_GAME_WAITING:
				// waiting in game lobby. can do: chat, (leave game? no protocol yet), log out
				methods.get(gameLobbyMethodIndexes.get(random.nextInt(gameLobbyMethodIndexes.size()))).invoke(this);
				break;
			case Constants.GAMESTATE_GAME_COUNTDOWN:
				// finished load game and ready to start. can do: chat, (leave game? no protocol yet), log out
				methods.get(gameCountdownMethodIndexes.get(random.nextInt(gameCountdownMethodIndexes.size()))).invoke(this);
				break;
			case Constants.GAMESTATE_GAME_PLAYING:
				// game is on. can do: chat, move, (leave game? no protocol yet), log out, power up, pick power item, dead
				methods.get(gamePlayingMethodIndexes.get(random.nextInt(gamePlayingMethodIndexes.size()))).invoke(this);
				break;
			case Constants.GAMESTATE_GAME_FINISHED:
				// game over. can do: chat, back to lobby, move, log out
				methods.get(gameFinishedMethodIndexes.get(random.nextInt(gameFinishedMethodIndexes.size()))).invoke(this);
				break;
			default:
				break;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/** simulate player dead
	 * @return
	 */
	public void simulateDead(){
		if (getPlayer().getCharacter().isDead()){
			//generate response here
		}
	}
	
	/** simulate player ready for game
	 * @return
	 */
	public void simulateReady(){
		// set client to ready to count down
	}
	
	/** simulate create game lobby
	 * 
	 */
	public void simulateCreateGame(){
		GameMode gm = getServer().createGame(Constants.GAMEMODE_DD);
		getServer().addClientToGame(this, gm);
		// generate response here
	}
	
	/** simulate collision. report the damage done on another player
	 * 
	 */
	public void simulateCollision(){
		int damage = 1;
		ArrayList<Character> characterList = new ArrayList<Character>();
		for (Player player: getGame().getPlayers()){
			if (player.getCharacter().getId() != getPlayer().getCharacter().getId())
				characterList.add(player.getCharacter());
		}
		if (characterList.size() >= 1){
			// car collision should only happen when there are 2 cars or more
			Random random = new Random();
			int targetId = random.nextInt() % characterList.size();
			// generate response here
			
		}
	}
	
	@Override
	public String toString(){
		return "FakeGameClient [" + getId() + ":" + getPlayer().getUsername() + "]";
	}
}
