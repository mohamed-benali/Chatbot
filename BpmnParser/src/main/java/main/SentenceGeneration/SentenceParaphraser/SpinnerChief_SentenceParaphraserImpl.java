package main.SentenceGeneration.SentenceParaphraser;

import com.google.common.net.MediaType;
import main.Exceptions.SentenceAnalyzerException;
import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerChief_SentenceParaphraserImpl implements SentenceParaphraser {

    private String developerApiKey = "aae7ae1d253c4dd89"; // Developer

    private String userUsername = "estudiantFIB"; // User
    private String userApiKey = "api6cbc370443e045df8"; // User

    //private String userUsername2 = ""; // User
    //private String userApiKey2 = ""; // User

    private String userPassword = "12345678"; // Same password for all users

    private String apikey = developerApiKey;
    private String username = userUsername;
    private String password = userPassword;

    /**
     * ***DEFAULT***
     * When spintype=0, SpinnerChief will return the spun article in {} (Spyntax) format.
     * For example, if your article is "This is a great software", the return will be "{It|This} {is|must be} a {good|nice} {software|program}".
     *
     * when spintype=1, SpinnerChief will return the spun article directly.
     * For example, if your article is "This is a great software", the return will be "It is a nice program".
     */
    private String spintype = "0";

    /**
     * ***DEFAULT*** is 4 (1/4 = 25% of the words)
     */
    private String spinfreq = "1"; // % of words spinned (percentage equals to 1/spinfreq)

    /**
     *  0 --> Removes the original word  ***DEFAULT***
     *  1--> keeps the original word
     */
    private String original = "1";

    /**
     * ***DEFAULT*** is 5
     * Wordscount means how many words to use when spintype=0.
     * For example, if the article is “hello”, Wordscount=3, the result will be {hello|hi|hey}.
     *  If Wordscount=2, the result will be {hello|hi}.
     */
    private String Wordscount = "4";

    /**
     * ***DEFAULT*** is 0
     *  wordquality=0, use Best Thesaurus to spin
     *  wordquality=1, use Better Thesaurus to spin
     *  wordquality=2, use Good Thesaurus to spin
     *  wordquality=3, use All Thesaurus to spin
     *  wordquality=9, use Everyone’s favorite to spin
     */
    private String wordquality = "0";

    /**
     * ***DEFAULT*** is 0
     *  When orderly=0, the server uses the thesaurus in its listed order to spin.
     *  When orderly=1, the server uses the thesaurus randomly to spin.
     */
    private String orderly = "0";


    /**
     * ***DEFAULT*** is nothing
     * When querytimes=1, the server returns today’s used query times of this account.
     * When querytimes=2, the server returns today’s remaining query times of this account.
     */
    private String querytimes = null;

    /**
     * This will protect the text between tags.
     * If tagprotect=null, it will not protect any tags.
     * If tagprotect=[],(),<- -> , it will protect the text between [ and ], ( and ), <- and ->.
     *
     * ***DEFAULT*** is []
     */
    private String tagprotect = "[]";

    /*
     * phrasecount =2,3,4,X：
     * When replacing the phrase, how many words define a phrase.
     * ***DEFAULT*** is 2
     */
    private String phrasecount = null;




    private String API = "http://api.spinnerchief.com:9001/";

    private String uri;


    public SpinnerChief_SentenceParaphraserImpl() {
        this.uri = buildURI();
    }

    private String buildURI() {
        String uri = API + "apikey="+ apikey + "&username=" + username + "&password=" + password;
        uri = addParameters(uri);
        return uri;
    }

    private String addParameters(String uri) {
        uri = addParameter(uri, "spintype", spintype);
        uri = addParameter(uri, "spinfreq", spinfreq);
        uri = addParameter(uri, "original", original);

        uri = addParameter(uri, "Wordscount", Wordscount);
        uri = addParameter(uri, "wordquality", wordquality);
        uri = addParameter(uri, "orderly", orderly);

        uri = addParameter(uri, "querytimes", querytimes);
        // uri = addParameter(uri, "tagprotect", tagprotect ); TODO: no pot enviar ls caracter [] en la URL, deixa el
                                                            // TODO:    valor per defecte
        uri = addParameter(uri, "phrasecount", phrasecount);


        return uri;
    }

    private String addParameter(String uri, String key, String value) {
        if(value != null) uri += "&" + key + "=" + value;
        return uri;
    }

    @Override
    public Sentences paraphraseSentence(String sentence) throws IOException, InterruptedException, SpinnerChief_SentenceParaphraserException {
        System.out.println("Sentence: " + sentence);
        System.out.println("URI: " + uri);
        System.out.println();


        String responseBody = makeHTTP_POST_Call(uri, sentence);

        // TODO: Ha de ser un Map, <int, Set<String> > or <String, Set<String> >,
        // TODO: On el value es un conjunt de frases generades, i la key es el identificador
        // TODO: int per el ordre sequencial, String per la frase original
        List<String> sentencesList = new ArrayList<>(); // TODO: Potser convertir en Map o Set i aixi
                                                        // controla els posibles repetits

        String[] responseSentencesArray = responseBody.split("\n");
        for(String responseSentence: responseSentencesArray) {
            System.out.println("Sentences: " + responseSentence);
            System.out.println("\n\n");
            sentencesList.add(responseSentence);
        }



        Sentences sentences = new Sentences();
        sentences.addSentence(responseBody);

        return sentences;
    }

    /**
     * Generate sentences for all the sentences
     * @param sentences Should have atleast one element
     * @return
     * @throws InterruptedException
     * @throws SpinnerChief_SentenceParaphraserException
     * @throws IOException
     */
    @Override
    public Sentences paraphraseSentence(List<String> sentences) throws InterruptedException, SpinnerChief_SentenceParaphraserException, IOException {
        String sentence = "";
        sentence += sentences.get(0);

        for(int i = 1; i < sentences.size(); ++i) {
            sentence += "\n" + sentences.get(i);
        }

        return this.paraphraseSentence(sentence);
    }

    public String makeHTTP_POST_Call(String url, String textBody) throws IOException, InterruptedException, SpinnerChief_SentenceParaphraserException {
        // Crear un client per connectar-se al servidor.
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        // JSON amb la informacio a enviar al servidor
        // Seria bona idea assegurar-se que el 'text' esta en UTF8 o podem tenir problemes.
        String requestBody = textBody;

        // Crear peticio pel servidor
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url))
                .setHeader("Content-Type", "text/plain; charset=utf-8")
                .build();

        // enviar peticio i obtenir resposar
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code (si es 200, es que es OK)
        if (response.statusCode() != 200) {
            System.out.println("HTTP ERROR " + response.statusCode());
            throw new SpinnerChief_SentenceParaphraserException("Status code is not 200, it is: " + response.statusCode());
        }
        else { // Everything OK
            String responseBodyString = response.body();

            System.out.println(responseBodyString);
            return responseBodyString;
        }

    }



}
