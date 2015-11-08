package networking.request;

import java.io.IOException;

import networking.response.ResponseRankings;

public class RequestRankings extends GameRequest {

	// Data
	// Responses
	private ResponseRankings responseRankings;

	public RequestRankings() {
		responses.add(responseRankings = new ResponseRankings());
	}

	@Override
	public void parse() throws IOException {

	}

	@Override
	public void doBusiness() throws Exception {

		/*
		 * The client requests the status for all the other players in the
		 * lobby.
		 */

	}
}