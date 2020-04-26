package main.SentenceGeneration.SentenceAnalyzer;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Adapter of the sentence analyzer
 */
public interface SentenceAnalyzer {

    //static final String url_default_60006 = "http://172.17.0.2:60006";
    //static final String url_default_50005 = "http://172.17.0.2:50005";

    //static final String url_192_168_99_100_50005 = "http://192.168.99.100:50005";
    static final String url_192_168_99_100_60006 = "http://192.168.99.100:60006"; // Endpoint del analitzador
    static final String url = url_192_168_99_100_60006;


    default JSONArray makeHTTPCall(String sentence) throws IOException, InterruptedException, SentenceAnalyzerException {
        // Crear un client per connectar-se al servidor.
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        // JSON amb la informacio a enviar al servidor
        // Seria bona idea assegurar-se que el 'text' esta en UTF8 o podem tenir problemes.
        String requestBody = "{\"text\":\"" + sentence + "\"," +
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
        if (response.statusCode() != 200) {
            System.out.println("HTTP ERROR " + response.statusCode());
            throw new SentenceAnalyzerException("Sentence Analyzer Exception: Status code is " + response.statusCode());
        }
        else { // Everything OK
            String responseBodyString = response.body();
            JSONArray jsonArray = new JSONArray(responseBodyString);
            //System.out.println(jsonArray.toString(4));
            return jsonArray;
        }
    }


    /**
     *
     * @param sentence The sentence to analyze
     * @return Returns the analysis of the sentence
     * @throws IOException
     * @throws InterruptedException
     */
    SentenceAnalysis analyzeSentence(String sentence) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException;
}
