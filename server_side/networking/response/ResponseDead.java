package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseDead extends GameResponse {

    public ResponseDead() {
        responseCode = Constants.SMSG_DEAD;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
      /*Construct packet*/
        return packet.getBytes();
    }
    
}
