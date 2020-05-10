package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities;

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



    //region REGION: Override(toString, equals)
    @Override
    public String toString() {
        String result = "";
        result += "\n";
        result += getWords().toString();
        result += "\n";
        result += "\n";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Words) {
            Words otherWords = (Words) o;
            if (getWords().equals(otherWords.getWords()) )
            {
                return true;
            }
        }
        return false;
    }
    //endregion


    /**
     *
     * @return Returns the number of elements(words)
     */
    public int size() { return words.size(); }


    public void add(Word word) {
        words.add(word);
    }
    public void add(String word) {
        Word newWord = new SingleWord(word);
        words.add(newWord);
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

    public int maxSize() {
        int maxSize = 0;
        for(Word word : words) {
            int wordSize = word.size();
            if(wordSize > maxSize) maxSize = wordSize;
        }
        return maxSize;
    }
}
