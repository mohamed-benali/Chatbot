package main.Entity.Intent.Intent.QueryIntent;

import main.Entity.Intent.Intent.myIntent;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WhoSubjectQueryTaskIntent extends QueryTaskIntent {
    public WhoSubjectQueryTaskIntent(String id, String subject, String task) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        super(id+"_WHO_SUBJECT", subject, task);

        // TODO: Create here the training Sentences? For now here
        String whoSubjectSentence = this.getSentenceBuilder().buildWhoSubjectSentence(this.getTask());
        this.getTrainingPhrases().addTrainingPhrase(whoSubjectSentence);
    }

    @Override
    protected myIntent createBasicIntentInstance(String name, String subject, String task) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        return new WhoSubjectQueryTaskIntent(name, subject, task);
    }

    @Override
    public List<String> makeResponse() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException { // TODO: Better responses, X is done by Subject
        List<String> responses = new ArrayList<>();

        String subject = this.getSubject();
        String response = this.getSentenceBuilder().buildSentence(this.getTask(), subject);
        this.getTrainingPhrases().addTrainingPhrase(response);

        responses.add(response);

        return responses;
    }


    @Override //TODO: Create here the training Sentence? For now, in the constructor
    protected Sentences buildTrainingPhrasesToParaphrase() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        Sentences sentences = new Sentences();
        sentences.addSentences(this.getTrainingPhrases().getTrainingPhrasesList());
        return sentences;
    }

    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
        this.getTrainingPhrases().updateTrainingPhrases(paraphrasedSentences);
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {
        this.setDefaultNullTrainingPhrase();
        return this.getTrainingPhrases().getBuildedTrainingPhrases();
    }

    @Override
    protected void setDefaultNullTrainingPhrase() {
        getTrainingPhrases().setHasNullTrainingPhrase(false);
        getTrainingPhrases().setDefaultTrainingPhraseType(null);
    }
}
