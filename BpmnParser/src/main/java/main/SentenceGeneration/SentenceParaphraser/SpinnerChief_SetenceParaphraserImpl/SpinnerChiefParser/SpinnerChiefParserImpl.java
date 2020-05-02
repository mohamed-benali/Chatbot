package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.*;

public class SpinnerChiefParserImpl implements SpinnerChiefParser {
    @Override
    public Sentences parseSentence(String sentenceToParse) {
        Words words = new Words();

        int i = 0;
        while (i < sentenceToParse.length()) {
            Word word = readWord(sentenceToParse, i);

            words.add(word);

            i+=word.numCharacters();
        }

        Sentences similarSentences = new Sentences();
        return generateSentencesBacktracking(words, similarSentences);
    }



    //region REGION: Read(parse) word
    private Word readWord(String sentenceToParse, int i) {
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

    private Word readSingleWorld(String sentenceToParse, int i) {
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

    private Word readMultipleWord(String sentenceToParse, int i) {
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

    private boolean endsMultipleWord(char char_j) {
        return char_j == '}';
    }

    private boolean startsMultipleWord(char char_i) {
        return char_i == '{';
    }
    //endregion

    //region REGION: BACKTRACKING(generate combinations of sentences)
    /**
     * Generates all the combinations for words form position 0 to words.size()
     * @param words The words to use for the backtracking
     * @param outSimilarSentences The result of all generated sentences
     */
    private Sentences generateSentencesBacktracking(Words words, Sentences outSimilarSentences) {
        generateSentencesBacktracking_i(0, words, outSimilarSentences);
        return outSimilarSentences;
    }


    // TODO: Segurament sigui posible millora el rendiment(ja que una un cop tinc totes les combinacions,
    // TODO: en el interval [index+1, words.size()-1), nomes cal que cada Word en las posicio index,
    // TODO: es coloqui en la posicio index, d'aquetsa manera no cal fer el recalul de totes les combinacions per les altres
    // TODO: paraules. Notar que aixo es repetiria recursivament

    /**
     * Generates all the combinations for words form {@code index} to words.size()
     * @param index 0 <= index <= words.size(),  indicates the word to proces
     * @param words The words to use for the backtracking
     * @param outSimilarSentences The result of all generated sentences
     */
    private void generateSentencesBacktracking_i(int index, Words words, Sentences outSimilarSentences) {
        if(index == words.size()) {
            Sentence sentence = words.buildSentece();
            outSimilarSentences.addSentence(sentence);
        }
        else {
            Word word_index = words.get(index);
            for(int i = 0; i < word_index.size(); ++i) {
                String word_i = word_index.get(i);
                Word word = new SingleWord(word_i);
                //Words copyWords = new Words(words); // copy(i may preserve the word and put it back after backtracking)
                //copyWords.set(index, word);
                words.set(index, word);

                Sentences outSimilarSentences_inmersive = new Sentences();
                generateSentencesBacktracking_i(index+1, words/*copyWords*/, outSimilarSentences_inmersive);
                outSimilarSentences.addSentences(outSimilarSentences_inmersive);
            }
            words.set(index, word_index); // Recover the value for next iteration

        }
    }
    //endregion
}
