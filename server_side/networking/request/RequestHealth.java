package networking.request;

import java.io.IOException;

import networking.response.ResponseHealth;

public class RequestHealth extends GameRequest {

	// Data
	// Responses
	private ResponseHealth responseHealth;

	public RequestHealth() {
		responses.add(responseHealth = new ResponseHealth());
	}

	@Override
	public void parse() throws IOException {

	}

	@Override
	public void doBusiness() throws Exception {

		/*
		 * When the user damages a character, they report who the damage is
		 * being dealt to and how much. The server responds by updating all
		 * health bars involved through ResponseChangeHealth.
		 */

	}
}