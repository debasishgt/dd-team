package networking.response;

import utility.GamePacket;

public class ResponseCreateCharacter extends GameResponse {
	
	// Must override abstract class' method
    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        return packet.getBytes();
    }
}