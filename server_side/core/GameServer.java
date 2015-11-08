package core;

// Java Imports
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Custom Imports
import configuration.GameServerConf;
import metadata.Constants;
//import dataAccessLayer.DAO;
import metadata.GameRequestTable;
//import model.Player;
import networking.response.GameResponse;
import utility.ConfFileParser;

/**
 * The GameServer class serves as the main module that runs the server. Incoming
 * connection requests are established and redirected to be managed by another
 * class called the GameClient. Several specialized methods are also stored here
 * to perform other specific needs.
 */
public class GameServer {

	private static GameServer gameServer; // References GameServer instance
	private GameServerConf configuration; // Stores server config. variables
	private boolean ready = false; // Used to keep server looping
	private HashMap<Long, GameClient> activeThreads = new HashMap<Long, GameClient>(); // Stores
																						// active
																						// threads
																						// by
																						// thread
																						// ID
	private HashMap<Integer, Player> activePlayers = new HashMap<Integer, Player>(); // Stores
																						// active
																						// players
																						// by
																						// player
																						// ID
	private HashMap<Long, GameClient> lobbyClients = new HashMap<Long, GameClient>();

	private HashMap<Long, GameMode> games = new HashMap<Long, GameMode>();

	private HashMap<String, String> accounts = new HashMap<String, String>();

	/**
	 * Initialize the GameServer by setting up the request types and creating a
	 * connection with the database.
	 */
	public GameServer() {
		configuration = new GameServerConf();

		// Initialize the table with request codes and classes for static
		// retrieval
		GameRequestTable.init();

		// Initialize database connection
		/*
		 * if (DAO.getInstance() == null) {
		 * System.err.println("Failed to connect to database.");
		 * System.exit(-1); }
		 */
	}

	/**
	 * Configure the game server by reading values from the configuration file.
	 */
	private void configure() {
		ConfFileParser confFileParser = new ConfFileParser(
				"gameServer.conf");
		configuration.setConfRecords(confFileParser.parse());
	}

	/**
	 * Search for any other possible configuration variables and mark the server
	 * ready to start.
	 */
	private void getReady() {
		configure();
		ready = true;
	}

	/**
	 * Check whether the server is prepared to run.
	 * 
	 * @return the ready status
	 */
	private boolean isReady() {
		return ready;
	}

	/**
	 * Run the game server by waiting for incoming connection requests.
	 * Establishes each connection and stores it into a GameClient thread to
	 * manage incoming and outgoing activity.
	 */
	private void run() {
		ServerSocket listenSocket;
		int serverPort = configuration.getPortNumber();
		
		// testing 'addFakeGameClient'

		if (Constants.SIMULATION_ON){
			Timer timer = new Timer();
			for (int i = 0; i < Constants.SIMULATION_FAKE_CLIENT_COUNT; i++){
				timer.schedule(new TimerTask(){
					@Override
					public void run(){
						addFakeGameClient();				
					}
				}, (i + 1) * 5000 );
			}
		}
		try {
			// Start to listen on the given port for incoming connections
			listenSocket = new ServerSocket(serverPort);
			System.out.println("Server has started on port: "
					+ listenSocket.getLocalPort());
			System.out.println("Waiting for clients...");
			// Loop indefinitely to establish multiple connections
			while (true) {
				try {
					// A client socket will represent a connection between the
					// client and this server
					Socket clientSocket = listenSocket.accept();
					System.out.println("A Connection Established!");

					// Create a thread to represent a client that holds the
					// client socket
					GameClient client = new GameClient(clientSocket, this);
					// Run the thread
					client.start();
					activeThreads.put(client.getId(), client);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Get the GameClient thread for the player using the player ID.
	 *
	 * @param playerID
	 *            holds the player ID
	 * @return the GameClient thread
	 */
	/*
	 * public GameClient getThreadByPlayerID(int playerID) { for (GameClient
	 * aClient : activeThreads.values()) { if (aClient.getPlayer().getID() ==
	 * playerID) { return aClient; } }
	 * 
	 * return null; }
	 */

	/**
	 * Get the GameClient thread for the player using the username.
	 *
	 * @param username
	 *            holds the username
	 * @return the GameClient thread
	 */

	public GameClient getThreadByPlayerUserName(String userName) {
		for (GameClient aClient : activeThreads.values()) {
			if (aClient.getPlayer().getUsername().equals(userName)) {
				return aClient;
			}
		}
		return null;
	}

	/**
	 * Get the GameClient thread for the player using character name
	 * 
	 * @param characterName
	 *            name of the character to look for
	 * @return GameClient thread
	 */
	public GameClient getThreadByCharacterName(String characterName) {
		// loop through the list of threads and look for the character
		for (GameClient client : activeThreads.values()) {
			if (client.getPlayer().getCharacter().getName()
					.equalsIgnoreCase(characterName))
				return client;
		}
		// if cannot find the character return null
		return null;
	}

	public int getNumberOfCurrentThreads() {
		return activeThreads.size();
	}

	public void addToActiveThreads(GameClient client) {
		activeThreads.put(client.getId(), client);
	}

	public ArrayList<Player> getActivePlayers() {
		return new ArrayList<Player>(activePlayers.values());
	}

	public Player getActivePlayer(int player_id) {
		return activePlayers.get(player_id);
	}

	public void setActivePlayer(Player player) {
		activePlayers.put(player.getId(), player);
	}

	public void removeActivePlayer(int player_id) {
		activePlayers.remove(player_id);
	}

	public void deletePlayerThreadOutOfActiveThreads(Long threadID) {
		activeThreads.remove(threadID);
	}

	/**
	 * Push a pending response to a user's queue.
	 * 
	 * @param player_id
	 *            holds the player ID
	 * @param response
	 *            is the instance containing the response information
	 */
	/*
	 * public void addResponseForUser(int player_id, GameResponse response) {
	 * GameClient client = getThreadByPlayerID(player_id);
	 * 
	 * if (client != null) { client.addResponseForUpdate(response); } else {
	 * System.out.println("In addResponseForUser--client is null"); } }
	 */

	/**
	 * Push a pending response to a user's queue.
	 *
	 * @param username
	 *            holds the username
	 * @param response
	 *            is the instance containing the response information
	 */
	/*
	 * public void addResponseForUser(String username, GameResponse response) {
	 * GameClient client = getThreadByPlayerUserName(username);
	 * 
	 * if (client != null) { client.addResponseForUpdate(response); } else {
	 * System.out.println("In addResponseForUser--client is null"); } }
	 */

	/**
	 * Push a pending response to all users' queue except one user.
	 * 
	 * @param player_id
	 *            holds the excluding player ID
	 * @param response
	 *            is the instance containing the response information
	 */
	public void addResponseForAllOnlinePlayers(long player_id,
			GameResponse response) {
		for (GameClient client : activeThreads.values()) {
			if (client.getId() != player_id
					&& client.getGamestate() == Constants.GAMESTATE_GAME_PLAYING) {
				client.addResponseForUpdate(response);
			}
		}
	}

	/**
	 * create a game. can be dd or rr
	 * 
	 * @param gameMode
	 */
	public GameMode createGame(int gameMode) {
		if (gameMode == Constants.GAMEMODE_DD) {
			GameMode game = new DemolitionDerbyGame(this);
			System.out.printf("Created game [%d]\n", game.getId());
			synchronized (games) {
				games.put(game.getId(), game);
			}
			return game;
		}
		return null;
	}

	/**
	 * move client from lobby to game
	 * 
	 * @param client
	 * @param game
	 */
	public void addClientToGame(GameClient client, GameMode game) {
		try {
			game.addClient(client.getId(), client);
			client.setGame(game);
			client.setGamestate(Constants.GAMESTATE_GAME_WAITING);
			synchronized (lobbyClients) {
				lobbyClients.remove(client.getId());
			}
			System.out.printf("Added client [%s] to game [%d]\n", client.getPlayer().getUsername(), game.getId());
			System.out.println(game.toString() + ": " + game.getClientNames());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * remove client from the game
	 * 
	 * @param id
	 * @param game
	 */
	public void removeClientFromGame(long id, GameMode game) {
		try {
			game.removeClient(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** move client from game to lobby after finish the game
	 * 
	 * @param id
	 * @param game
	 */
	public void moveClientFromGameToLobby(long id, GameMode game) {
		try {
			GameClient client = game.getClient(id);
			game.removeClient(id);
			addClientToLobby(client);
			client.setGamestate(Constants.GAMESTATE_LOBBY);
			client.setGame(null); 	// remove game reference
			System.out.println("Move " + client + " from " + game + "to lobby");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * start the game with id = ?
	 * 
	 * @return
	 */

	public void startGame(long id) {
		GameMode game = null;
		if ((game = games.get(id)) != null) {
			game.startGame();
		}
	}

	/**
	 * Abort a game with id = ?
	 * 
	 * @return
	 */
	public void abortGame(long id) {
	}

	/**
	 * move client from activethreads to lobby list "logged in"
	 * 
	 * @param client
	 */
	public void addClientToLobby(GameClient client) {
		synchronized (lobbyClients) {
			lobbyClients.put(client.getId(), client);
		}
		System.out.printf("Add client [%d:%s] to lobby\n", client.getId(), client.getPlayer().getUsername());
//		synchronized (activeThreads) {
//			activeThreads.remove(client.getId());
//		}
	}

	public void removeClientFromLobby(long id) {
		synchronized (lobbyClients) {
			lobbyClients.remove(id);
		}
		System.out.printf("Remove client [%d:%s] from lobby\n", id, lobbyClients.get(id).getPlayer().getUsername());
	}

	public void generateAccounts() {
		accounts.put("test", "test");
		accounts.put("1", "1");
	}

	/**
	 * populate account list
	 * 
	 * @return
	 */
	public static GameServer getInstance() {
		return gameServer;
	}

	/**
	 * check if username & password match one of accounts
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public int checkAccount(String username, String password) {
//		String pwd = null;
//		if ((pwd = accounts.get(username)) == null) {
//			return Constants.ERROR_ACCOUNT_NOT_FOUND;
//		} else if (!pwd.equalsIgnoreCase(password)) {
//			return Constants.ERROR_WRONG_PASSWORD;
//		}
		// For testing purpose, just let the client log in
		accounts.put(username, password);
		return Constants.LOGIN_SUCCESS;
	}
	
	/** add an instance of FakeGameClient
	 * 
	 */
	public void addFakeGameClient(){
		try {
			FakeGameClient fgc = new FakeGameClient(this);
			activeThreads.put(fgc.getId(), fgc);	// add to list
			System.out.printf("Added a fake client with [%d:%s]\n", fgc.getId(), fgc.getPlayer().getUsername());
			fgc.start();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/** remove a finished game
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void removeGame(long id) throws Exception{
		if (!games.containsKey(id))
			throw new Exception("Destroying non existing game");
		if (games.get(id).isRunning())
		games.remove(id);
	}
	
	public ArrayList<GameMode> getGameList(){
		return new ArrayList<GameMode>(games.values());
	}
	
	public static void main(String args[]) throws SQLException {
		gameServer = new GameServer();

		gameServer.getReady();

		if (gameServer.isReady()) {
			gameServer.run();
		}
	}

}
