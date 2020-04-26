package main.SentenceGeneration;

import org.json.JSONArray;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    
    // service base URL.
    // La IP es la que posa el docker per defecte, pero potser es diferent segons on s'executi

    // S'ha d'estar executant el docker

    // Veure els ports i el id:
    //      docker ps

    // Veure la ip(lo ultim es el id) NO USAR, dona la intera del docker, no a la que jo haig de accedir
    //      docker inspect -f "{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}" 9147be898162

    // Para el docker, ip es el id del docker que s'esta executant
    // docker stop [id]

    static final String url_default_60006 = "http://172.17.0.2:60006";
    static final String url_default_50005 = "http://172.17.0.2:50005";

    static final String url_192_168_99_100_50005 = "http://192.168.99.100:50005";
    static final String url_192_168_99_100_60006 = "http://192.168.99.100:60006";


    static final String url = url_192_168_99_100_60006;

    // docker run --rm -p 60006:60006 -p 50005:50005 label-parser
    // docker ps
    // docker stop [id]

    public static void main(String[] args) throws Exception {

        // Crear un client per connectar-se al servidor.
        HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

        // Text a analitzar TODO: See what happens to Mary(subject)
        String text = "Send finished documents to Marketing Department.";
        //text= "Eat fish.";
        System.out.println("Text to analyze: " + text);

        // JSON amb la informacio a enviar al servidor
        // Seria bona idea assegurar-se que el 'text' esta en UTF8 o podem tenir problemes.
        String requestBody = "{\"text\":\"" + text + "\"," +
                              "\"language\":\"en\", " +
                              "\"OutputLevel\":\"shallow\"}";


        System.out.println("Requested Body: " + requestBody);


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

        String responseBodyString = response.body();

        // Get response body (JSON amb la info necessaria)
        System.out.println("Response Body: " + responseBodyString);

        JSONArray jsonArray = new JSONArray(responseBodyString);
        System.out.println(jsonArray.toString(4));



    }
}
