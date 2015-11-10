/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.request;

// Java Imports
import core.Player;

import java.io.IOException;
import mysqldb.DBHandler;


// Custom Imports
//import core.GameServer;
import networking.response.ResponseLogin;
import networking.response.ResponseCreateCharacter;
import utility.DataReader;
/**
 *
 * @author adrien
 */
public class RequestLogin extends GameRequest {

    // Data
    private String username;
    private String password;

// Responses
    private ResponseLogin responseLogin;

    public RequestLogin() {
        responses.add(responseLogin = new ResponseLogin());
    }

    @Override
    public void parse() throws IOException {
        setUsername(DataReader.readString(dataInput));
        setPassword(DataReader.readString(dataInput));

    }

    // Changes Made -- Ansab
    @Override
    public void doBusiness() throws Exception {
      //TODO: Un-comment next two lines
    	 DBHandler dbHandler = new DBHandler();
    	 Boolean validate = dbHandler.processLogin(username, password);

      //TODO: Take out the next line
      //Boolean validate = true;

    	this.client.setPlayerId(username);
    	this.setPlayerId(username);
        //If the user successfully validated,
        if (validate == true) {
        	Player newPlayer = dbHandler.getPlayer(username);
        	this.client.getServer().addActivePlayer(username, newPlayer);
            //He enters the world, we have to send him the informations about the other players

            for (Player  player : this.client.getServer().getActivePlayers().values()) {
                if(!player.getId().equalsIgnoreCase(this.getUsername()))
                {
                    ResponseCreateCharacter newChar = new ResponseCreateCharacter();
                    newChar.setData(player.getId(), player.getType(),player.getX(), player.getY(), player.getZ());
                    responses.add(newChar);
                }
            }
            short activePlayerType = this.client.getServer().getActivePlayer(username).getType();
            float activePlayerX = this.client.getServer().getActivePlayer(username).getX();
            float activePlayerY = this.client.getServer().getActivePlayer(username).getY();
            float activePlayerH = this.client.getServer().getActivePlayer(username).getHeading();
            responseLogin.setData(validate, playerId, activePlayerType, activePlayerX, activePlayerY, activePlayerH);
    	}
        else{
        	responseLogin.setData(validate, playerId, (short) 0, 0, 0, 0);
    	}


    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
