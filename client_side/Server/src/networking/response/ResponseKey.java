package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseKey extends GameResponse {

        private String moveKey;
        private float moveVal;
        private String playerId;
        
        public ResponseKey() {
		responseCode = Constants.SMSG_KEY;
	}

	@Override
	public byte[] constructResponseInBytes() {
		GamePacket packet = new GamePacket(responseCode);
                packet.addString(playerId);
                packet.addString(moveKey);
                packet.addFloat(moveVal);
            
		return packet.getBytes();
	}

	public String getMessage() {
		return "CSMG_MOVE";
	}

	public void setData(String x, float y, String playerId) {
		this.moveKey = x;
		this.moveVal = y;
		this.playerId = playerId;                
	}
}
