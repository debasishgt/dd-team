package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseChatAll extends GameResponse {

	private String message;
	private String senderId;

	public ResponseChatAll() {
		responseCode = Constants.CMSG_CHAT_ALL;
		System.out.println("response set");
	}

	@Override
	public byte[] constructResponseInBytes() {
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(senderId);
		packet.addString(message);
		return packet.getBytes();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
}
