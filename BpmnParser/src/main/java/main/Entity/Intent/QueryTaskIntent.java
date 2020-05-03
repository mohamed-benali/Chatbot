package main.Entity.Intent;

import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryTaskIntent extends myIntent {
    public QueryTaskIntent(String name, String subject, String task) throws IOException {
        super(name+"_query", subject, task);

        // TODO: Use the analysis of Freeling and SimpleNLG to build a better training phrase(use the verb, for example)
        this.addTrainingPhrase("Who " + task);
    }


    protected List<String> makeResponse() {
        // TODO: Use the analysis og Freeling and SimpleNLG to build a better response
        List<String> responses = new ArrayList<String>();
        String response = this.getSubject();
        responses.add(response);
        return responses;
    }




    @Override // TODO: Query
    protected Intents buildExtraIntents() {
        return null;
    }

    @Override
    protected Sentences buildTrainingPhrases() {
        Sentences sentences = new Sentences();
        sentences.addSentences(this.getTrainingPhrases().getTrainingPhrasesList());
        /*if(this.getTrainingPhrases().hasNullTrainingPhrase()) {
            Sentences nextSentences = myTrainingPhrase.getNextTrainingPhrases();
            sentences.addSentences(nextSentences);
        }*/
        return sentences;
    }

    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
        this.getTrainingPhrases().updateTrainingPhrases(paraphrasedSentences);
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {
        return this.getTrainingPhrases().getBuildedTrainingPhrases();
    }
}
