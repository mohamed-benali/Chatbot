package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.Word;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.util.ArrayList;
import java.util.List;

public class Cjt_Words {
    private List<Words> cjt_words;

    public Cjt_Words() {
        setCjt_words(new ArrayList<>());
    }


    public List<Words> getCjt_words() { return cjt_words; }
    public void setCjt_words(List<Words> cjt_words) { this.cjt_words = cjt_words; }

    public void add(Words words) {
        this.cjt_words.add(words);
    }

    public void add(Cjt_Words outSimilarSentences_inmersive) {
        getCjt_words().addAll(outSimilarSentences_inmersive.getCjt_words());
    }

    public Sentences buildSentences() {
        Sentences sentences = new Sentences();

        for (Words words : cjt_words) {
            sentences.addSentence(words.buildSentece());
        }

        return sentences;
    }
}
