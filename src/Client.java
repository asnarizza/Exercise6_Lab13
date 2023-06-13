import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connectToServer() {
        try {
            socket = new Socket(serverAddress, serverPort);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTranslationRequest(String text, String targetLanguage) {
        try {
            output.println(text);
            output.println(targetLanguage);
            System.out.println("Translation request sent to server.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveTranslation() {
        try {
            String translatedText = input.readLine();
            System.out.println("Received translation from server: " + translatedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            input.close();
            output.close();
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
