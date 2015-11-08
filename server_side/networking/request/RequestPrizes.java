package networking.request;

import java.io.IOException;

import networking.response.ResponsePrizes;


public class RequestPrizes extends GameRequest {

	// Data
	// Responses
	private ResponsePrizes responsePrizes;

	public RequestPrizes() {
		responses.add(responsePrizes = new ResponsePrizes());
	}

	@Override
	public void parse() throws IOException {

	}

	@Override
	public void doBusiness() throws Exception {

		/*
		 * At the end of the gamemode the user requests a prize from the server
		 * and the server responses with a prize based on the user’s rank.
		 */

	}
}