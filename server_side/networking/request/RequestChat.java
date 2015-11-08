package networking.request;

import java.io.IOException;

import core.GameClient;
import networking.response.ResponseChat;
import metadata.Constants;
import utility.DataReader;

public class RequestChat extends GameRequest {
	int flag;
	String chatMsg;
	String receiverName;
	
	/**
	 * Constructor
	 */
	public RequestChat() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	/**
	 * Get data from socket
	 * Expect: flag [character name] msg 
	 * get character name only if it is private msg
	 */
    @Override
    public void parse() throws IOException {
    	try {
    		// get the flag
    		flag = DataReader.readInt(dataInput);
    		
    		// read character name only if it is private chat
    		if (flag == Constants.CHAT_PRIVATE){
    			// it is private. grab character name
    			receiverName = DataReader.readString(dataInput);
    		}
    		// read the chat msg
    		chatMsg = DataReader.readString(dataInput);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }

    /**
     * Generate response
     * if it is private chat. add response only for the target
     * if it is global, add response to every online player
     * if there is no character for private chat, response fail to 
     * the one sending message
     */
    @Override
    public void doBusiness() throws Exception {
    	ResponseChat otherResponse = new ResponseChat();
    	// check for global or private chat
    	if (flag == Constants.CHAT_GLOBAL){
    		// global chat. set the response with sendername and chat msg
    		otherResponse.setGlobalChat(client.getPlayer().getUsername(), chatMsg);
    		// add response for all online character except the one sending
    		client.getServer().addResponseForAllOnlinePlayers(client.getId(), otherResponse);
    		// add response for the one sending
    		//responses.add(otherResponse);
    	} else {
    		// private chat. have to look for the character
    		GameClient targetGameClient = client.getServer().getThreadByPlayerUserName(receiverName);
			ResponseChat response = new ResponseChat();    		
    		if (targetGameClient != null && targetGameClient.getGamestate() == Constants.GAMESTATE_GAME_PLAYING){
    			// target character is online. add chat response to him/her
    			response.setPrivateChat(client.getPlayer().getUsername(), receiverName, chatMsg);
    			targetGameClient.addResponseForUpdate(response);
    		} else {
    			// no target character found. send back chat failure to the sender
    			response.setPrivateChatFail();
    			// add response to the sender queue
    			responses.add(response);
    		}
    	}
    }
}
