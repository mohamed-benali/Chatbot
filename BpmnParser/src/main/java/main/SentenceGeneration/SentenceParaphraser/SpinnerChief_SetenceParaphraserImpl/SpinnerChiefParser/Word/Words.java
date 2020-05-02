package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;

import java.util.ArrayList;
import java.util.List;

public class Words {
    private List<Word> words;

    public Words() {
        setWords(new ArrayList<>() );
    }

    public Words(Words words) {
        setWords(words.getWords());
    }

    public List<Word> getWords() { return words; }
    public void setWords(List<Word> words) { this.words = words; }

    public Word get(int index) { return words.get(index); }
    public void set(int index, Word word) { words.set(index, word); }

    public int size() { return words.size(); }


    public void add(Word word) {
        words.add(word);
    }

    /**
     * Words should be all of type SingleWord
     * @return Returns a sentence builded with this.getWords()
     */
    public Sentence buildSentece() {
        StringBuilder sentenceStringBuilder  = new StringBuilder();
        for (int i = 0; i < words.size(); ++i) {
            Word word = words.get(i);

            if(i==0) sentenceStringBuilder.append(word.getWord());
            else sentenceStringBuilder.append(word.getWord());
        }

        Sentence sentence = new Sentence(sentenceStringBuilder .toString());

        return sentence;
    }
}
