package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseMove extends GameResponse {

    public ResponseMove() {
        responseCode = Constants.SMSG_MOVE;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
     
        /*
         * add content to packet eg : packet.addString("xxx");
         */
        return packet.getBytes();
    }
   
}
