package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseRankings extends GameResponse {

    public ResponseRankings() {
        responseCode = Constants.SMSG_RANKINGS;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
      /*Construct packet*/
        return packet.getBytes();
    }
    
}
