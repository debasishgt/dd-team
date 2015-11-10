/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

/**
 *
 * @author adrien
 */
public class ResponseCreateCharacter extends GameResponse{
    
    
        private float x;
        private float y;
        private float z;
        private short type;
        private String playerId;
        
        public ResponseCreateCharacter() {
		responseCode = Constants.SMSG_CREATE_CHARACTER;
	}

	@Override
	public byte[] constructResponseInBytes() {
		GamePacket packet = new GamePacket(responseCode);  
				packet.addString(playerId);
                packet.addShort16(type);
                packet.addFloat(x);
                packet.addFloat(y);
                packet.addFloat(z);
                
            
		return packet.getBytes();
	}

	public String getMessage() {
		return "CSMG_MOVE";
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public void setData(String playerId, short type, float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
        this.type = type;
		this.playerId = playerId;
                
	}
    
}
