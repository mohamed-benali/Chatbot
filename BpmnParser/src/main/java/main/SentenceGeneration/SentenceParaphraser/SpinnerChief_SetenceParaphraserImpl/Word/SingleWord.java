package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.Word;

public class SingleWord extends Word {

    private String word;

    public SingleWord(String word) {
        this.word = word;
    }

    @Override
    public int size() { return 1; }

    @Override
    /**
     * i = 0
     */
    public String get(int i) {
        return word;
    }

    @Override
    public int numCharacters() {
        return word.length();
    }

    @Override
    public String getWord() {
        return word;
    }
}
