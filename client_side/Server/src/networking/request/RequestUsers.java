package networking.request;

// Java Imports
import java.io.IOException;

// Custom Imports
//import core.GameServer;
import networking.response.ResponseString;
import utility.DataReader;

public class RequestUsers extends GameRequest {

    // Data
    private String message;
    // Responses
    private ResponseString responseString;

    public RequestUsers() {
        responses.add(responseString = new ResponseString());
    }

    @Override
    public void parse() throws IOException {
        message = DataReader.readString(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        responseString.setMessage(message);
       
        responseString.setMessage("Users");
    }
}
