package networking.request;

// Java Imports
import core.Player;
import java.io.IOException;

// Custom Imports
//import core.GameServer;
import networking.response.ResponseCreateCharacter;
import networking.response.ResponseMove;
import utility.DataReader;

/**
 *
 * @author adrien
 */
public class RequestCreateCharacter extends GameRequest {

	// Data
	//private String playerId;
    private float x;
    private float y;
    private float z;
    private short type;
        
        
	// Responses
	private ResponseCreateCharacter responceCrea;

	public RequestCreateCharacter() {
		responses.add(responceCrea = new ResponseCreateCharacter());
                
	}
        

	@Override
	public void parse() throws IOException {   
			playerId = DataReader.readString(dataInput);
            type = DataReader.readShort(dataInput);
            x = DataReader.readFloat(dataInput);
            y = DataReader.readFloat(dataInput);
            z = DataReader.readFloat(dataInput);

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
            //Add the created characterin the list in memory of the server
            Player player = this.client.getServer().getActivePlayer(this.playerId);
            
            player.setX(x);
            player.setY(y);
            player.setZ(z);
            player.setType(type);
            
		responceCrea.setData(playerId, type,x,y,z);

	}
    
}
