package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities;

public abstract class Word {


    public abstract int size();
    public abstract String get(int i);

    public abstract int numCharacters();

    public abstract String getWord();

    /**
     *
     * @param index // Points to the begining of the words that will be used to generate a subset<br>
     *             Example: {"hi", "hello", "good morning", "how are you"}
     *             If index = 0 ---> index points to "hi".
     *             If index = 1 ---> index points to "hello".
     *             and so on
     * @param sizeSubset >=1
     * @return Returns the subset of the word. <br>
     *     If {@code index + sizeSubset >= this.size()}, then returns the first word, plus the (sizeSubset-1) last words
     */
    public abstract Word getSubWord(int index, int sizeSubset);

    public abstract boolean isBlankSpace();
}
