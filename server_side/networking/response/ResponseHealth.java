package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseHealth extends GameResponse {

    public ResponseHealth() {
        responseCode = Constants.SMSG_HEALTH;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
      /*Construct packet*/
        return packet.getBytes();
    }
    
}
