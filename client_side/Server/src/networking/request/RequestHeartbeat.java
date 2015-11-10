/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.request;

// Java Imports
import java.io.IOException;
import java.util.Random;

// Custom Imports
//import core.GameServer;
import networking.response.ResponseString;
import utility.DataReader;

/**
 *
 * @author adrien
 */
public class RequestHeartbeat extends GameRequest{
    // Data
    private String message;
    // Responses
    private ResponseString responseString;

    public RequestHeartbeat() {
        responses.add(responseString = new ResponseString());
    }

    
    @Override
    public void parse() throws IOException {
            message = DataReader.readString(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
            
            message = "heartbeat received";
               //System.out.println(message);
            Random id = new Random();
            message += "\nClient ID: " + (id.nextInt(10) + 1);
            responseString.setMessage(String.valueOf(this.client.getPlayerId()));

    }
}
