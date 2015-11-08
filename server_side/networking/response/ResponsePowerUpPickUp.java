package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponsePowerUpPickUp extends GameResponse {

    public ResponsePowerUpPickUp() {
        responseCode = Constants.SMSG_POWER_PICKUP;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
      /*Construct packet*/
        return packet.getBytes();
    }
    
}
