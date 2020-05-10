package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities;

import main.Entity.Intent.TrainingPhrases.NullTrainingPhrase;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.util.ArrayList;
import java.util.List;

public class Cjt_Words {
    private List<Words> cjt_words;

    public Cjt_Words() {
        setCjt_words(new ArrayList<>());
    }


    //region REGION: Override(toString, equals)
    @Override
    public String toString() {
        String result = "";
        result += "\n";
        result += getCjt_words().toString();
        result += "\n";
        result += "\n";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cjt_Words) {
            Cjt_Words otherCjt_Words = (Cjt_Words) o;
            if (getCjt_words().equals(otherCjt_Words.getCjt_words()) )
            {
                return true;
            }
        }
        return false;
    }
    //endregion


    /**
     * Number of words
     * @return Returns the number of words
     */
   public int size(){
        return cjt_words.size();
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
