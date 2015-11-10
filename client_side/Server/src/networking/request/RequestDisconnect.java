/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.request;
// Java Imports
import java.io.IOException;
import java.util.Random;

import core.Player;
import mysqldb.DBHandler;
import networking.request.GameRequest;

import networking.response.ResponseDisconnect;
// Custom Imports
//import core.GameServer;
import utility.DataReader;
/**
 *
 * @author adrien
 */
public class RequestDisconnect extends GameRequest {

	// Data
	private String message;

	// Responses
	private ResponseDisconnect responseDisconnect;

	public RequestDisconnect() {
            responses.add(responseDisconnect = new ResponseDisconnect());

	}

	@Override
	public void parse() throws IOException {

	}

	// Made Changes -- ANSAB
	@Override
	public void doBusiness() throws Exception {
            responseDisconnect.setData(playerId);
            Player disconnectingPlayer = this.client.getServer().getActivePlayer(playerId);
            DBHandler dbHandler = new DBHandler();
            short type = disconnectingPlayer.getType();
            float x = disconnectingPlayer.getX();
            float y = disconnectingPlayer.getY();
            float h = disconnectingPlayer.getHeading();
            dbHandler.savePlayerPosition(playerId, type, x, y, h);
            this.client.getServer().removeActivePlayer(playerId);
	}
}
