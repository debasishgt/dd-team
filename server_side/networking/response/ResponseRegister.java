package networking.response;


import metadata.Constants;
import utility.GamePacket;

public class ResponseRegister extends GameResponse {
	private int flag;
	private int errorType;
	
	/**
	 * Must have for each response class because responseCode must be set
	 */
	public ResponseRegister() {
		// TODO Auto-generated constructor stub
		responseCode = Constants.SMSG_REGISTER;
	}
	
	/**
	 * Generate packet for registration
	 */
    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(flag);
        if (flag == Constants.REGISTRATION_FAIL){
        	// registration fail. what kind of error?
        	packet.addInt32(errorType);
        }
        // registration success doesn't require any more information
        return packet.getBytes();
    }
    
    /**
     * Set registration fail. Flag is auto set to fail
     * @param errorType which errror to be send to the client
     */
    public void setRegistrationFail(int error){
    	// set the flag to fail
    	flag = Constants.REGISTRATION_FAIL;
    	
    	// set error type
    	errorType = error;
    	if (Constants.DEBUG)
    		System.out.printf("Registration fail. Error = %d\n", errorType);
    }
    
    /**
     * Set registration success. Flag is set to success
     */
    public void setRegistrationSuccess(){
    	// set the flag to success
    	flag = Constants.REGISTRATION_SUCCESS;
    	if (Constants.DEBUG)
    		System.out.println("Registration success. Account created.");
    }
}