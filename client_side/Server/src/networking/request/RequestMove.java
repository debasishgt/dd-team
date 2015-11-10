package networking.request;

// Java Imports
import java.io.IOException;
import java.util.Random;

import networking.response.ResponseMove;
// Custom Imports
//import core.GameServer;
import networking.response.ResponseString;
import utility.DataReader;

public class RequestMove extends GameRequest {

	// Data
	private String message;
        private float x;
        private float y;
        private float z;
        private float h;



	// Responses
	private ResponseMove responseMove;

	public RequestMove() {
		responses.add(responseMove = new ResponseMove());

	}


	@Override
	public void parse() throws IOException {
            x = DataReader.readFloat(dataInput);
            y = DataReader.readFloat(dataInput);
            z = DataReader.readFloat(dataInput);
            h = DataReader.readFloat(dataInput);


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
        this.client.getServer().setActivePlayerPos(playerId,x, y, z);
	    	responseMove.setData(x,y,z,h,playerId);

	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
}
