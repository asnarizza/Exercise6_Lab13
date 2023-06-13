import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	
    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private Map<String, Map<String, String>> translations;

    public Server(int port) {
        this.port = port;
        initializeTranslations();
    }

    private void initializeTranslations() {
        translations = new HashMap<>();

        Map<String, String> englishTranslations = new HashMap<>();
        englishTranslations.put("Good morning", "Selamat pagi");
        englishTranslations.put("Good night", "Selamat malam");
        englishTranslations.put("How are you?", "Apa khabar?");
        englishTranslations.put("Thank you", "Terima kasih");
        englishTranslations.put("Goodbye", "Selamat tinggal");
        englishTranslations.put("What's up?", "Ada apa?");
        translations.put("English", englishTranslations);

        // Add translations for other languages
        // ...

    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port " + port);
            waitForClientConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForClientConnection() {
        try {
            clientSocket = serverSocket.accept();
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Client connected.");
            handleClientRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequests() {
        try {
            String text = input.readLine();
            String targetLanguage = input.readLine();
            String translatedText = translateText(text, targetLanguage);
            sendTranslation(translatedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String translateText(String text, String targetLanguage) {
        Map<String, String> languageTranslations = translations.get(targetLanguage);
        if (languageTranslations != null) {
            String translatedText = languageTranslations.get(text);
            if (translatedText != null) {
                return translatedText;
            }
        }
        return "Translation not available";
    }

    private void sendTranslation(String translatedText) {
        output.println(translatedText);
        System.out.println("Translation sent to client: " + translatedText);
    }

    public void closeServer() {
        try {
            input.close();
            output.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("Server closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
