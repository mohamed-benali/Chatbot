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
        result += "\n";
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

    @Override
    public Word getSubWord(int index, int sizeSubset) {
        return new SingleWord(word);
    }
}
