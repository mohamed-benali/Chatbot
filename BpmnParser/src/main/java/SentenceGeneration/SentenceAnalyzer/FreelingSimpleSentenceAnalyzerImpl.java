package SentenceGeneration.SentenceAnalyzer;

import SentenceGeneration.Entity.SentenceAnalysis.SentenceAnalysis;
import SentenceGeneration.Entity.SentenceAnalysis.SimpleSentenceAnalysis;
import main.Exceptions.SentenceAnalyzerException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class FreelingSimpleSentenceAnalyzerImpl implements SentenceAnalyzer {

    @Override
    public SentenceAnalysis analyzeSentence(String sentence) throws IOException, InterruptedException, SentenceAnalyzerException {
        JSONArray jsonArray = this.makeHTTPCall(sentence);
        System.out.println(jsonArray.toString(4));

        JSONObject sentenceAnalysisObject = jsonArray.getJSONObject(0);
        JSONArray actionsArray = sentenceAnalysisObject.getJSONArray("actions");

        // TODO: Consider if the array has more than one sentence(more than one action)
        JSONObject firstAction = actionsArray.getJSONObject(0);

        String predicate    = null;
        String object       = null;
        String complement   = null;

        if(firstAction.has("predW") )   predicate    = firstAction.getString("predW");
        if(firstAction.has("objW") )    object       = firstAction.getString("objW");
        if(firstAction.has("compW") )   complement   = firstAction.getString("compW");

        SentenceAnalysis simpleSentenceAnalysis = new SimpleSentenceAnalysis(predicate, object, complement);

        return simpleSentenceAnalysis;
    }
}
