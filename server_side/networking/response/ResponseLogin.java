package networking.response;

import java.util.ArrayList;




import metadata.Constants;
import utility.GamePacket;
import core.Character;

public class ResponseLogin extends GameResponse {
	private int flag;
	private int errorType;
	
	public ResponseLogin() {
		// TODO Auto-generated constructor stub
		// Set the response id 
		responseCode = Constants.SMSG_AUTH;
	}

	/**
	 * Construct the packet using the flag, errorType,
	 * 
	 */
    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(flag);
        if (flag == Constants.LOGIN_FAIL){
        	// Login fail: what kind of error?
        	packet.addInt32(errorType);
        }
        return packet.getBytes();
    }
    
    /**
     * Set login fail. Flag is auto set to fail.
     * @param errorType which error to be send to the client
     * @return void
     */
    public void setLoginFail(int error){
    	// set the flag to fail
    	flag = Constants.LOGIN_FAIL;
    	
    	// set the error type
    	errorType = error;
    	if (Constants.DEBUG)
			System.out.printf("Login fail. error =%d\n", errorType);
    }
    
    /**
     * Set log in success. Flag is auto set to success.
     * 
     */
    public void setLoginSuccess(){
    	// set the flag to success
    	flag = Constants.LOGIN_SUCCESS;
    	if (Constants.DEBUG)
			System.out.println("Login success");
    }
    
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

    
}
