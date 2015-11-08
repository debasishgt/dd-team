package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseMove extends GameResponse {
	int characterID;
	float xPos, yPos, zPos, hVal, pVal, rVal;
	/**
	 * constructor. set response code
	 */
	public ResponseMove() {
		// TODO Auto-generated constructor stub
		responseCode = Constants.SMSG_MOVE;
	}
	
	/**
	 * generate the packet
	 * info: player_id, x, y, z
	 */
    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(characterID);
        packet.addFloat(xPos);
        packet.addFloat(yPos);
        packet.addFloat(zPos);
        packet.addFloat(hVal);
        packet.addFloat(pVal);
        packet.addFloat(rVal);
        return packet.getBytes();
    }
    
    /** 
     * Set the player and the position
     * @param id
     * @param x
     * @param y
     * @param z
     * @param h
     * @param p
     * @param r
     */
    public void setMove(int id, float x, float y, float z,
    		float h, float p, float r){
    	// sets the info
    	characterID = id;
    	xPos = x;
    	yPos = y;
    	zPos = z;
    	hVal = h;
    	pVal = p;
    	rVal = r;
    }
}