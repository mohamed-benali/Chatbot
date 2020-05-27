package main.Entity.Intent.Intent.QueryIntent;

import main.Entity.Intent.Intent.StartIntent;
import main.Entity.Intent.Intents;
import main.Entity.Intent.Intent.myIntent;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceAnalyzer.FreelingSimpleSentenceAnalyzerImpl;
import main.SentenceGeneration.SentenceAnalyzer.SentenceAnalyzer;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.List;

// TODO: Potser fer-la abstrat si es pot
public abstract class QueryTaskIntent extends myIntent {

    protected SentenceAnalysis sentenceAnalysis;
    private SentenceAnalyzer sentenceAnalyzer;


    public QueryTaskIntent(String name, String subject, String task) throws IOException {
        super(name+"_Query", subject, task);
        setSentenceAnalysis(new SentenceAnalysis());
        setSentenceAnalyzer(new FreelingSimpleSentenceAnalyzerImpl());
    }

    @Override
    protected abstract myIntent createBasicIntentInstance(String name, String subject, String task) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException;

    public SentenceAnalysis getSentenceAnalysis() { return sentenceAnalysis; }
    public void setSentenceAnalysis(SentenceAnalysis sentenceAnalysis) { this.sentenceAnalysis = sentenceAnalysis; }

    public SentenceAnalyzer getSentenceAnalyzer() { return sentenceAnalyzer; }
    public void setSentenceAnalyzer(SentenceAnalyzer sentenceAnalyzer) { this.sentenceAnalyzer = sentenceAnalyzer; }


    @Override
    public abstract List<String> makeResponse() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException;


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
