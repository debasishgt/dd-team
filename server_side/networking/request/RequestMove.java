package networking.request;

import java.io.IOException;

import networking.response.ResponseMove;
import utility.DataReader;

public class RequestMove extends GameRequest {
	float x, y, z, h, p, r;
	
	/** constructor
	 * 
	 */
	public RequestMove() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	/**
	 * get data from socket
	 * expected: x, y, z, h, p, r
	 */
    @Override
    public void parse() throws IOException {
    	try {
    		x = DataReader.readFloat(dataInput);
    		y = DataReader.readFloat(dataInput);
    		z = DataReader.readFloat(dataInput);
    		h = DataReader.readFloat(dataInput);
    		p = DataReader.readFloat(dataInput);
    		r = DataReader.readFloat(dataInput);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * move the player. ignore collision detection for now
     * broadcast to all online players
     */
    @Override
    public void doBusiness() throws Exception {
    	ResponseMove response = new ResponseMove();
    	// set character new position
    	client.getPlayer().getCharacter().setPos(x, y, z);
    	client.getPlayer().getCharacter().setHpr(h, p, r);
    	// generate response
    	response.setMove(client.getPlayer().getCharacter().getId(), x, y, z, h, p, r);
    	// send to other players
    	client.getServer().addResponseForAllOnlinePlayers(client.getId(), response);
    }
}
