package networking.request;

import java.io.IOException;

import networking.response.ResponseCollision;

public class RequestCollision extends GameRequest {

	// Data
	// Responses
	private ResponseCollision responseCollision;

	public RequestCollision() {
		responses.add(responseCollision = new ResponseCollision());
	}

	@Override
	public void parse() throws IOException {

	}

	@Override
	public void doBusiness() throws Exception {

		/*
		 * The client sends the player that it hit and the damage calculated
		 * through the damage modifier to the server, and responses the damage
		 * to the user that was hit with ResponseCollision
		 * Damage=(base_damage×weight_modifier×speed
		 * modifier×inflicting_part)/(receiving_part)
		 */

	}
}