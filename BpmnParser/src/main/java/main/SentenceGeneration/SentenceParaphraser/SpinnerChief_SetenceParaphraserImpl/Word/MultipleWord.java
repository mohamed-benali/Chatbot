package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.Word;

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

    private void add(String word) { words.add(word); }


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
}
