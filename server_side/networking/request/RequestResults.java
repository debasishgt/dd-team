package networking.request;

import java.io.IOException;

import networking.response.ResponseResults;

public class RequestResults extends GameRequest {

	// Data
	// Responses
	private ResponseResults responseResults;

	public RequestResults() {
		responses.add(responseResults = new ResponseResults());
	}

	@Override
	public void parse() throws IOException {

	}

	@Override
	public void doBusiness() throws Exception {

		/*
		 * When the client ends the race or dies they request the results to see
		 * where the placed in the gamemode. This calls the response for the
		 * user and all of the other users who have finished the race with
		 * ResponseResults
		 */

	}
}