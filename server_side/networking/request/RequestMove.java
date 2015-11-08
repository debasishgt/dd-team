package networking.request;

// Java Imports
import java.io.IOException;

import networking.response.ResponseMove;
// Custom Imports
import utility.DataReader;

public class RequestMove extends GameRequest {

	// Data

	// Responses
	private ResponseMove responseMove;

	public RequestMove() {

		responseMove = new ResponseMove();

	}

	@Override
	public void parse() throws IOException {

	}

	@Override
	public void doBusiness() throws Exception {

		/*
		 * Client issues a change to their location, and isMovingflag. This is
		 * used when a client wishes to move or stop moving. It is followed by
		 * creating a number of ResponseMove and Server will update other users
		 * with these ResponseMove Note: This is the way we will handle
		 * respawning. When a character’s HP falls to 0 they will be sent to the
		 * nearest friendly control point, if none are found then they will be
		 * sent to base.
		 */
		
		//client.getServer().addResponseForAllOnlinePlayers(client.getId(), (GameResponse) responseMove); 

	}
}
