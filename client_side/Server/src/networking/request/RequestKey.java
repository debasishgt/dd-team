package networking.request;

// Java Imports
import java.io.IOException;
import java.util.Random;

import networking.response.ResponseKey;
// Custom Imports
//import core.GameServer;
import networking.response.ResponseString;
import utility.DataReader;

public class RequestKey extends GameRequest {

	// Data
		private String message;
        private String moveKey;
        private float moveVal;
        
        
	// Responses
	private ResponseKey responseKey;

	public RequestKey() {
		responses.add(setResponseKey(new ResponseKey()));
                
	}
        

	@Override
	public void parse() throws IOException {   
            moveKey = DataReader.readString(dataInput);
            moveVal = DataReader.readFloat(dataInput);

	}

	@Override
	public void doBusiness() throws Exception {
		//String[] split = message.split(" ");
		//System.out.println(message);
//		message = split[0]
//				+ " "
//				+ (Integer.parseInt(split[1]) + 10)
//				+ " "
//				+ (Integer.parseInt(split[2]) + 10) + " " + split[3] + " " + split[4] +" ";
//		//System.out.println(message);
//		Random id = new Random();
//		message += "\nClient ID: " + (id.nextInt(10) + 1);
        //this.client.activePlayers.get(playerId).setXYZ(moveKey, moveVal, z);
		responseKey.setData(moveKey,moveVal,playerId);

	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public ResponseKey getResponseKey() {
		return responseKey;
	}


	public ResponseKey setResponseKey(ResponseKey responseKey) {
		this.responseKey = responseKey;
		return responseKey;
	}
}
