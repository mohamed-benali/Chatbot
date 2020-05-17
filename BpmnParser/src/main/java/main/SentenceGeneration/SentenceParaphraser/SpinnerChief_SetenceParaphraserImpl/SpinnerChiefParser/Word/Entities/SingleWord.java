package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities;

public class SingleWord extends Word {

    private String word;

    public SingleWord(String word) {
        this.word = word;
    }



    //region REGION: Override(toString, equals)
    @Override
    public String toString() {
        String result = "";
        result += getWord().toString();
        result += "\n";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SingleWord) {
            SingleWord otherWords = (SingleWord) o;
            if (getWord().equals(otherWords.getWord()) )
            {
                return true;
            }
        }
        return false;
    }
    //endregion


    @Override
    public int size() { return 1; }

    /**
     * Always returns the word.
     * @param i This parameter is ignrored.
     * @return Returns the word
     */
    @Override
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

    @Override
    public Word getSubWord(int index, int sizeSubset) {
        return new SingleWord(word);
    }

    @Override
    public boolean isBlankSpace() {
        return this.getWord().equals(" ");
    }

    @Override
    public boolean isMultipleWord() {
        return false;
    }

    @Override
    public boolean isSingleWord() {
        return true;
    }
}
