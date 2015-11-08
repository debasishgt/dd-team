package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponsePrizes extends GameResponse {

    public ResponsePrizes() {
        responseCode = Constants.SMSG_PRIZES;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
      /*Construct packet*/
        return packet.getBytes();
    }
    
}
