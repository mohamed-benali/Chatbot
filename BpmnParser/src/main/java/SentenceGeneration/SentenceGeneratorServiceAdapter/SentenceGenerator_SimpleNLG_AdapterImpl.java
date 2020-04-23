package SentenceGeneration.SentenceGeneratorServiceAdapter;

import SentenceGeneration.Entity.SentenceAnalysis.SentenceAnalysis;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class SentenceGenerator_SimpleNLG_AdapterImpl implements SentenceGeneratorServiceAdapter {

    @Override
    public String generateSimpleSentence(SentenceAnalysis sentenceAnalysis, String subject) {
        // TODO: Make them singleton, so they are only once created
        // TODO: Reason: It is said in SimpleNLG docs that they only need to be created once
        // TODO: So they might be costly to create

        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory nlgFactory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);


        String predicate    = sentenceAnalysis.getPredicate();
        String object       = sentenceAnalysis.getObject();
        String complement   = sentenceAnalysis.getComplement();

        // TODO: Set the the object and complement, plural or singular, depending on the info of freeling
        // TODO: Set the predicate tense(infinitive, etc) depending on the info of freeling

        SPhraseSpec phraseSpec = nlgFactory.createClause();
        if(subject != null)     phraseSpec.setSubject(subject);
        if(predicate != null)   phraseSpec.setVerb(predicate);
        if(object != null)      phraseSpec.setObject(object);
        if(complement != null)  phraseSpec.addComplement(complement);

        String sentenceGenerated = realiser.realiseSentence(phraseSpec);

        return sentenceGenerated;
    }
}
