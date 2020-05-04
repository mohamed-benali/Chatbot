package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities;

import java.util.ArrayList;
import java.util.List;

public class MultipleWord extends Word {

    private List<String> words;

    public MultipleWord() {
        words = new ArrayList<>();
    }

    public MultipleWord(String[] words) {
        this.words = new ArrayList<>();
        for (String word : words) {
            this.add(word);
        }
    }

    public List<String> getWords() { return words; }

    public void add(String word) { words.add(word); }
    public void add(List<String> words) {this.words.addAll(words); }


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
        if (o instanceof MultipleWord) {
            MultipleWord otherWords = (MultipleWord) o;
            if (getWords().equals(otherWords.getWords()) )
            {
                return true;
            }
        }
        return false;
    }
    //endregion


    @Override
    public int size() {
        return words.size();
    }

    @Override
    public String get(int i) {
        return words.get(i);
    }

    @Override
    public int numCharacters() {
        int size = 0;
        for(String word : words) size += word.length();

        ++size; // "{"
        size += this.size()-1; // "|"      Si veinia de es "{w1|w2|w3|w4}", te 4 paraules i 4 "|"
        ++size; // "}"

        return size;
    }

    @Override
    /**
     * Should never be called
     */
    public String getWord() {
        return null;
    }

    @Override
    public Word getSubWord(int index, int sizeSubset) {
        MultipleWord word = new MultipleWord();

        int lastElementPointer = index + sizeSubset -1;
        if(lastElementPointer >= this.size())  {
            if(this.size() < sizeSubset) word.add(this.getWords());
            else {
                word.add(this.get(0));
                for(int i = 0; i < sizeSubset-1; ++i) word.add(this.get(size()-1-i));
            }
        }
        else for(int i = index; i <= lastElementPointer; ++i) word.add(this.get(i));


        Word returnWord = word;
        if(word.size() == 1) returnWord = new SingleWord(word.get(0));
        return returnWord;
    }
}
