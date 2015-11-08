package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseDisconnect extends GameResponse {

    public ResponseDisconnect() {
        responseCode = Constants.SMSG_DISCONNECT;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);    
        /*Packet construction*/
        return packet.getBytes();
    }
   
}
