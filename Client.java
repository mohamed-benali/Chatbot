
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Client {
    
    // service base URL.
    // La IP es la que posa el docker per defecte, pero potser es diferent segons on s'executi
    static final String url = "http://172.17.0.2:60006";

    public static void main(String[] args) throws Exception {

        // Crear un client per connectar-se al servidor.
        HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

        // Text a analitzar 
        System.out.print("Text to analyze: ");
        String text = System.console().readLine();

        // JSON amb la informacio a enviar al servidor
        // Seria bona idea assegurar-se que el 'text' esta en UTF8 o podem tenir problemes.
        String requestBody = "{\"text\":\"" + text + "\"," +
                              "\"language\":\"en\", " +
                              "\"OutputLevel\":\"shallow\"}";

        // Crear peticio pel servidor
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url))
                .setHeader("Content-Type", "application/json; charset=utf-8")
                .build();

        // enviar peticio i obtenir resposar
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code (si es 200, es que es OK)
        if (response.statusCode() != 200) 
            System.out.println("HTTP ERROR "+response.statusCode());
        
        // Get response body (JSON amb la info necessaria)
        System.out.println(response.body());

    }
}
