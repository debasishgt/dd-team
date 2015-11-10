package networking.request;

// Java Imports
import java.io.IOException;

import networking.response.ResponseChatAll;
// Custom Imports
//import core.GameServer;
import networking.response.ResponseString;
import utility.DataReader;

public class RequestChatAll extends GameRequest {

	// Data
	private String message;
	private String senderId;
	// Responses
	private ResponseChatAll responseString;

	public RequestChatAll() {
		responses.add(responseString = new ResponseChatAll());
	}

	@Override
	public void parse() throws IOException {
		senderId = DataReader.readString(dataInput);
		message = DataReader.readString(dataInput);
		// System.out.println(message);
	}

	@Override
	public void doBusiness() throws Exception {
		responseString.setSenderId(senderId);
		responseString.setMessage(message);
		/*
		 * if(message == "hello") responseString.setMessage("Hi");
		 */
	}
}
