package main.SentenceGeneration.SentenceAnalyzer;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SimpleSentenceAnalysis;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class FreelingSimpleSentenceAnalyzerImpl implements SentenceAnalyzer {

    /*
     * Helpers
     */


    /**
     *
     * @param key Key to find its value inside MSD, example: predMSD, objMSD or compMSD
     * @param MSD Is the value of the key "somethingMSD" of the analysis done by freeling
     * @return If {@code MSD} is "pos=verb|vform=infinitive", and {@code key} is "vform" returns "infinitive".
     */
    private String getMSDValueByKey(String key, String MSD) throws NoFreelingKeyException {
        String[] result = MSD.split("\\|");

        for(int i = 0; i < result.length; ++i) {
            String token_i = result[i];

            if(token_i.startsWith(key) ) {
                String vform = result[i];
                String[] vformValue = vform.split("=");
                String value = vformValue[1];
                return value;
            }
        }
        throw new NoFreelingKeyException("There is no key for the MSD: " + MSD);
    }



    /*
     * Sentence analysis
     */

    @Override
    public SentenceAnalysis analyzeSentence(String sentence) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        JSONArray jsonArray = this.makeHTTPCall(sentence);
        System.out.println(jsonArray.toString(4));

        JSONObject sentenceAnalysisObject = jsonArray.getJSONObject(0);
        JSONArray actionsArray = sentenceAnalysisObject.getJSONArray("actions");

        // TODO: Consider if the array has more than one sentence(more than one action)
        JSONObject firstAction = actionsArray.getJSONObject(0);

        String predicate        = null;
        String predicateForm    = null;
        String predicateType    = null;
        String predicateNumber    = null;

        String object       = null;
        String objectNumber = null; // Plural or singular

        String complement       = null;
        String complementNumber = null; // Plural or singular



        if(firstAction.has("predW") )   {
            predicate    = firstAction.getString("predW");
            predicateType = this.getMSDValueByKey("pos", firstAction.getString("predMSD"));
            if(predicateType.equals("noun")) {
                predicateNumber = this.getMSDValueByKey("num", firstAction.getString("predMSD"));
            }
            else if(predicateType.equals("verb")) { //
                predicateForm = this.getMSDValueByKey("vform", firstAction.getString("predMSD"));
            }
            else throw new SentenceAnalyzerException("Predicate is not a noun, not a verb");

        }
        if(firstAction.has("objW") )    {
            object      = firstAction.getString("objW");
            String objectType = this.getMSDValueByKey("pos", firstAction.getString("objMSD"));
            if(objectType.equals("conjunction")) {
                //"objMSD": "pos=noun|type=common|num=plural",
                objectNumber   = "singular";
            }
            else objectNumber   = this.getMSDValueByKey("num", firstAction.getString("objMSD"));
        }
        if(firstAction.has("compW") )   {
            complement   = firstAction.getString("compW");
            complementNumber   = this.getMSDValueByKey("num", firstAction.getString("compMSD"));
        }

        SentenceAnalysis simpleSentenceAnalysis = new SimpleSentenceAnalysis(   predicate, predicateType,
                                                                                predicateForm, predicateNumber,
                                                                                object, objectNumber,
                                                                                complement, complementNumber);


        return simpleSentenceAnalysis;
    }




}
