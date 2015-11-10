package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseChatOne extends GameResponse {

	private String message;
	private String senderId;
	private String receiverId;

	public ResponseChatOne() {
		responseCode = Constants.CMSG_CHAT_ONE;
		// System.out.println("response created");
	}

	@Override
	public byte[] constructResponseInBytes() {
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(senderId);
		packet.addString(receiverId);
		packet.addString(message);
		// System.out.println("packet sent");
		return packet.getBytes();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverId() {
		return this.receiverId;
	}
}
