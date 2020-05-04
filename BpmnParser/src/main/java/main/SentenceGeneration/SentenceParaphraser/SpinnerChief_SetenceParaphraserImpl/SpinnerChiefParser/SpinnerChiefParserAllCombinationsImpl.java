package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.SingleWord;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Word;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Words;

public class SpinnerChiefParserAllCombinationsImpl implements SpinnerChiefParser {


    @Override
    public Sentences generateSentences(Words words, Sentences similarSentences) {
        return generateSentencesBacktracking(words, similarSentences);
    }



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
