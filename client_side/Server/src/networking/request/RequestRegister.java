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
import networking.response.ResponseRegister;
import networking.response.ResponseCreateCharacter;
import utility.DataReader;
/**
 *
 * @author adrien
 */
public class RequestRegister extends GameRequest {

    // Data
    private String username;
    private String password;

// Responses
    private ResponseRegister responseRegister;

    public RequestRegister() {
        responses.add(responseRegister = new ResponseRegister());
    }

    @Override
    public void parse() throws IOException {
        setUsername(DataReader.readString(dataInput));
        setPassword(DataReader.readString(dataInput));

    }

    @Override
    public void doBusiness() throws Exception {
      //TODO: Un-comment next two lines to enable DB
    	 DBHandler dbHandler = new DBHandler();
    	 int ret = dbHandler.registerUser(username, password);
    	 //int ret = 1;
    	 boolean validate = (ret > 0) ? true : false;

      //TODO: Take out the next line
      //Boolean validate = true;

    	this.client.setPlayerId(username);
    	this.setPlayerId(username);
        //If the user successfully validated,

        responseRegister.setData(validate, playerId);
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
