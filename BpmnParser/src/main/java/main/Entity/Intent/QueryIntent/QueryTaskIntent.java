package main.Entity.Intent.QueryIntent;

import main.Entity.Intent.Intents;
import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
import main.Entity.Intent.myIntent;
import main.Enums.DefaultTrainingPhraseType;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Potser fer-la abstrat si es pot
public abstract class QueryTaskIntent extends myIntent {
    public QueryTaskIntent(String name, String subject, String task) throws IOException {
        super(name+"_Query", subject, task);

        // TODO: Use the analysis of Freeling and SimpleNLG to build a better training phrase(use the verb, for example)
        //this.addTrainingPhrase("Who " + task);
    }

    @Override
    protected List<String> makeResponse() {
        // TODO: Use the analysis of Freeling and SimpleNLG to build a better response
        List<String> responses = new ArrayList<String>();
        String response = this.getSubject();
        responses.add(response);
        return responses;
    }


    @Override
    public Intents buildExtraIntents() {
        return null;
    }

    @Override //TODO: Potser fer un patro plantilla
    protected abstract Sentences buildTrainingPhrasesToParaphrase() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException;

    @Override
    public abstract void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences);

    @Override
    protected abstract List<String> getBuildedTrainingPhrases();

    @Override
    protected abstract void setDefaultNullTrainingPhrase();
}
