import java.io.IOException;

public class MainExercise6 {
	
    public static void main(String[] args) throws IOException {
        // Start the server
        Server server = new Server(4321);
        server.startServer();

        // Connect the client
        Client client = new Client("localhost", 4321);
        client.connectToServer();

        // Send translation requests from client to server
        client.sendTranslationRequest("Good morning", "Bahasa Malaysia");
        client.receiveTranslation();

        client.sendTranslationRequest("Good night", "Arabic");
        client.receiveTranslation();

        client.sendTranslationRequest("How are you?", "Korean");
        client.receiveTranslation();

        // Close the connection and server
        client.closeConnection();
        server.closeServer();
    }
}