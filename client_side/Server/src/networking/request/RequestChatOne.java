package networking.request;

// Java Imports
import java.io.IOException;

import networking.response.ResponseChatOne;
// Custom Imports
//import core.GameServer;
import networking.response.ResponseString;
import utility.DataReader;

public class RequestChatOne extends GameRequest {

	// Data
	private String message;
	private String receiverId;
	private String senderId;
	// Responses
	private ResponseChatOne responseString;

	public RequestChatOne() {
		responses.add(responseString = new ResponseChatOne());
		// System.out.println("req chat one init");
	}

	@Override
	public void parse() throws IOException {
		senderId = DataReader.readString(dataInput);
		receiverId = DataReader.readString(dataInput);
		message = DataReader.readString(dataInput);
		// System.out.println("data parsed");

	}

	@Override
	public void doBusiness() throws Exception {
		responseString.setSenderId(senderId);
		responseString.setReceiverId(receiverId);
		responseString.setMessage(message);
		// System.out.println("business done");
		/*
		 * if(message == "hello") responseString.setMessage("Hi");
		 */
	}
}
