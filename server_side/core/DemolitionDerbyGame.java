package core;

import java.util.Timer;
import java.util.TimerTask;

import metadata.Constants;

public class DemolitionDerbyGame extends GameMode {

	/** Demolition Derby Game Mode
	 * 
	 */
	public DemolitionDerbyGame(GameServer server){
		this.server = server;
		gamestate = Constants.GAMEMODE_STATE_LOBBY;
	}
	
	public void startGame(){
		// get all client to load game
		setAllClientGamestate(Constants.GAMESTATE_GAME_COUNTDOWN);
		gamestate = Constants.GAMEMODE_STATE_COUNTDOWN;
		Timer timer = new Timer();
		// schedule to start
		timer.schedule(new TimerTask() {			
			@Override
			public void run() {
				start();
			}
		}, 5000);
	}
	public void endGame(){
		try {
			server.removeGame(getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		// check if all game ready before countdown
		long startTime = System.currentTimeMillis();
		long currentTime = 0;
		isRunning = true;
		gamestate = Constants.GAMEMODE_STATE_PLAY;
		try{
			setAllClientGamestate(Constants.GAMESTATE_GAME_PLAYING);
			System.out.println(this + " is in process");
			while (isRunning){
				// testing purpose. use time out. should be 1 man alive or time out
				currentTime = System.currentTimeMillis();
				if (gamestate != Constants.GAMEMODE_STATE_ENDED){
					if (((currentTime - startTime) / 1000) > Constants.SIMULATION_GAME_TIME_OUT){
						gamestate = Constants.GAMEMODE_STATE_ENDED;
						// set all client gamestate to finished
						setAllClientGamestate(Constants.GAMESTATE_GAME_FINISHED);	
						System.out.println(this + " is finished");
					}
				}else{
					// game finish. wait till all client get back to lobby then destroy game
					if (isEmpty()){
						isRunning = false;
						endGame();
					}
				}
				Thread.sleep(1);
			}
			System.out.println("Destroying : "+ this);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * create fake client to simulate
	 */
//	public void createFakeClient() throws Exception{
//		if (clientList.size() >= MAX_PLAYER_COUNT)
//			throw new Exception("Game is full.");
//		FakeGameClient fgc = new FakeGameClient(this.server);
//		clientList.put(fgc.getId(), fgc);
//	}
}
