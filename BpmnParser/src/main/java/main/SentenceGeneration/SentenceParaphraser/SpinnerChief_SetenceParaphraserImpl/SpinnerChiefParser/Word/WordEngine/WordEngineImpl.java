package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine;

import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.*;

public class WordEngineImpl implements WordEngine {
    @Override
    public Words readWords(String sentenceToParse) {
        Words words = new Words();

        int i = 0;
        while (i < sentenceToParse.length()) {
            Word word = readWord(sentenceToParse, i);

            words.add(word);

            i+=word.numCharacters();
        }

        return words;
    }



    //region REGION: Read(parse) word
    Word readWord(String sentenceToParse, int i) {
        Word word;

        char char_i = sentenceToParse.charAt(i);
        if(startsMultipleWord(char_i)) {
            word = readMultipleWord(sentenceToParse, i);
        }
        else { // SingleWord
            word = readSingleWorld(sentenceToParse, i);
        }

        return word;
    }

    Word readSingleWorld(String sentenceToParse, int i) {
        StringBuilder wordStringBuilder = new StringBuilder();

        boolean isSingleWord = true;
        while(i < sentenceToParse.length() && isSingleWord ) {
            char char_i = sentenceToParse.charAt(i);
            if(startsMultipleWord(char_i) ) isSingleWord = false;
            else {
                wordStringBuilder.append(char_i);
                ++i;
            }
        }

        String wordString = wordStringBuilder.toString();

        return new SingleWord(wordString);
    }

    Word readMultipleWord(String sentenceToParse, int i) {
        StringBuilder wordsStringBuilder = new StringBuilder();
        ++i; // Skip initial '{'

        char char_i = sentenceToParse.charAt(i);
        while(i < sentenceToParse.length() && ! endsMultipleWord(char_i)) {
            wordsStringBuilder.append(char_i);
            ++i;
            char_i = sentenceToParse.charAt(i);
        }

        String wordsString = wordsStringBuilder.toString();
        String[] words = wordsString.split("\\|");

        return new MultipleWord(words);
    }

    boolean endsMultipleWord(char char_j) {
        return char_j == '}';
    }

    boolean startsMultipleWord(char char_i) {
        return char_i == '{';
    }
    //endregion



    @Override
    public Cjt_Words separateWordsIntoSubSets(Words words, int sizeSubset) {
        Cjt_Words cjt_words = new Cjt_Words();

        /* Example: {"hi", "hello", "good morning", "how are you"}
            If index = 0 ---> index points to "hi".
            If index = 1 ---> index points to "hello".
            and so on
         */
        int index = 0; // Points to the begining of the words that will be used to generate a subset

        int maxSize = words.maxSize();
        while(index < maxSize) {
            Words subWords = new Words();
            for(int i = 0; i < words.size(); ++i){ // For each word
                Word word_i = words.get(i);
                Word subWord = word_i.getSubWord(index, sizeSubset);
                subWords.add(subWord);
            }
            cjt_words.add(subWords);
            index += sizeSubset;
        }

        return cjt_words;
    }


}
