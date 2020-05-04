package main.Entity.Intent.QueryIntent;

import main.Entity.Intent.QueryIntent.QueryTaskIntent;
import main.Enums.DefaultTrainingPhraseType;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.List;

public class WhoSubjectQueryTaskIntent extends QueryTaskIntent {
    public WhoSubjectQueryTaskIntent(String id, String subject, String task) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        super(id+"_WHO_SUBJECT", subject, task);

        // TODO: Create here the training Sentences? For now here
        String whoSubjectSentence = this.getSentenceBuilder().buildWhoSubjectSentence(this.getTask());
        this.getTrainingPhrases().addTrainingPhrase(whoSubjectSentence);
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
