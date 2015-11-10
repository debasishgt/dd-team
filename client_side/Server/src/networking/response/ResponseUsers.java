package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseUsers extends GameResponse {

    private String message;

    public ResponseUsers() {
        responseCode = Constants.RAND_STRING;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString("users: " +message);
        
        return packet.getBytes();
    }
    
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
