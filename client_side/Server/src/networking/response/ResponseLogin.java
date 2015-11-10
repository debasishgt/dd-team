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
public class ResponseLogin extends GameResponse {

    private String username;
    private String password;
//
    private short playerType;
    private String playerId;
    private boolean validate;
    private float x;
    private float y;
    private float h;

	public ResponseLogin() {
        responseCode = Constants.SMSG_AUTH;
    }

    @Override
    public byte[] constructResponseInBytes() {

        GamePacket packet = new GamePacket(responseCode);
        packet.addString(playerId);

        if(validate == true)
                packet.addShort16((short) 1);
        else packet.addShort16((short) 0);
        packet.addShort16(this.playerType);
        packet.addFloat(this.x);
        packet.addFloat(this.y);
        packet.addFloat(this.h);
        return packet.getBytes();
    }

    public short getPlayerType() {
		return playerType;
	}

	public void setPlayerType(short playerType) {
		this.playerType = playerType;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getMessage() {
        return "SMSG_AUTH";
    }

    public void setData(boolean validate, String playerId, short playerType, float x, float y, float h){
        this.validate = validate;
        this.playerType = playerType;
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.h = h;

    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

}
