package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseResults extends GameResponse {

    public ResponseResults() {
        responseCode = Constants.SMSG_RESULTS;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
      /*Construct packet*/
        return packet.getBytes();
    }
    
}
