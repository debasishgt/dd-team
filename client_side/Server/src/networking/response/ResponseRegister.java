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
public class ResponseRegister extends GameResponse {

    private String username;
    private String password;
// 
    private String playerId;
    private boolean validate;
    
    public ResponseRegister() {
        responseCode = Constants.SMSG_REGISTER;
    }

    @Override
    public byte[] constructResponseInBytes() {
        
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(playerId);
        
        if(validate == true)
            packet.addShort16((short) 1);
        else 
        	packet.addShort16((short) 0);
        
        return packet.getBytes();
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
        return "SMSG_REGISTER";
    }

    public void setData(boolean validate, String playerId){
        this.validate = validate;
        this.playerId = playerId;

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

}
