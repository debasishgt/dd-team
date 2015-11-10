package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseMove extends GameResponse {

        private float x;
        private float y;
        private float z;
        private float h;

        private String playerId;

        public ResponseMove() {
		responseCode = Constants.SMSG_MOVE;
	}

	@Override
	public byte[] constructResponseInBytes() {
		GamePacket packet = new GamePacket(responseCode);
                packet.addString( playerId);
                packet.addFloat(x);
                packet.addFloat(y);
                packet.addFloat(z);
                packet.addFloat(h);


		return packet.getBytes();
	}

	public String getMessage() {
		return "CSMG_MOVE";
	}

	public void setData(float x, float y, float z, float h,String playerId) {
		this.x = x;
		this.y = y;
		this.z = z;
    this.h = h;
		this.playerId = playerId;

	}
}
