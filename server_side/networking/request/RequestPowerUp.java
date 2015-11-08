package networking.request;

import java.io.IOException;

import networking.response.ResponsePowerUpUse;

public class RequestPowerUp extends GameRequest {

	// Data
	// Responses
	private ResponsePowerUpUse responsePowerUpUse;

	public RequestPowerUp() {
		responses.add(responsePowerUpUse = new ResponsePowerUpUse());
	}

	@Override
	public void parse() throws IOException {

	}

	@Override
	public void doBusiness() throws Exception {

		/*
		 * When the client presses a button bound to using a powerup and has a
		 * specific powerup which is differentiated through powerId. The server
		 * responds by animating their attack through ResponsePowerUpUse.
		 */

	}
}