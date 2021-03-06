package networking.request;

// Java Imports
import java.io.IOException;

import networking.response.GameResponse;
// Custom Imports
//import core.GameServer;
import networking.response.ResponseHeartbeat;

public class RequestHeartbeat extends GameRequest {
	public RequestHeartbeat() {

	

	}

	@Override
	public void parse() throws IOException {
	}

	@Override
	public void doBusiness() throws Exception {
		
		for (GameResponse reponse : client.getUpdates()) {
			responses.add(reponse);	
		}
		responses.add(new ResponseHeartbeat());
		client.clearUpdateBuffer();

	}
}
