/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.response;
// Custom Imports

import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

/**
 *
 * @author adrien
 */
public class ResponseDisconnect extends GameResponse {

    private String playerId;

    public ResponseDisconnect() {
        responseCode = Constants.SMSG_DISCONNECT;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString( playerId);
        
        return packet.getBytes();
    }

    public String getMessage() {
        return "CSMG_MOVE";
    }

    public void setData(String playerId) {
        this.playerId = playerId;

    }
}
