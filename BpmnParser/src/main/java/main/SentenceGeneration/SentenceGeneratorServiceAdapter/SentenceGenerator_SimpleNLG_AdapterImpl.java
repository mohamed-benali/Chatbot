package main.SentenceGeneration.SentenceGeneratorServiceAdapter;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;

import main.Exceptions.NoFreelingKeyException;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class SentenceGenerator_SimpleNLG_AdapterImpl implements SentenceGeneratorServiceAdapter {

    @Override
    public String generateSimpleSentence(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException {
        // TODO: Make them singleton, so they are only once created
        // TODO: Reason: It is said in SimpleNLG docs that they only need to be created once
        // TODO: So they might be costly to create

        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory nlgFactory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);


        String predicate    = sentenceAnalysis.getPredicate();
        String object       = sentenceAnalysis.getObject();
        String complement   = sentenceAnalysis.getComplement();

        String predicateType    = sentenceAnalysis.getPredicateType();
        String predicateForm    = sentenceAnalysis.getPredicateForm();
        String predicateNumber  = sentenceAnalysis.getPredicateNumber();


        String objectNumber     = sentenceAnalysis.getObjectNumber();
        String complementNumber = sentenceAnalysis.getComplementNumber();


        SPhraseSpec phraseSpec = nlgFactory.createClause();
        if(subject != null)     {
            NPPhraseSpec subjectPhrase = nlgFactory.createNounPhrase(subject);
            subjectPhrase.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR); // Its the default value, anyway
            phraseSpec.setSubject(subjectPhrase);
        }
        if(predicate != null)   {
            VPPhraseSpec predicatePhrase = nlgFactory.createVerbPhrase(predicate);

            if(predicateType.equals("verb")) { // Verb Should be a singleton or Enum
                //Form verbForm = getVerbForm(predicateForm); // If i tell that its infinitive doesnt work
                //predicatePhrase.setFeature(Feature.FORM, verbForm);
            }
            else if(predicateType.equals("noun")) {
                predicatePhrase.setFeature(Feature.TENSE, Tense.PRESENT); // Its the default value, anyway

                NumberAgreement numberAgreement = this.getNumberAgreement(predicateNumber);
                predicatePhrase.setFeature(Feature.NUMBER, numberAgreement);
            }

            phraseSpec.setVerb(predicatePhrase);
        }
        if(object != null)      {
            NPPhraseSpec objectPhrase = nlgFactory.createNounPhrase(object);
            NumberAgreement numberAgreement = this.getNumberAgreement(objectNumber);
            objectPhrase.setFeature(Feature.NUMBER, numberAgreement);
            phraseSpec.setObject(objectPhrase);
        }
        if(complement != null)  {
            phraseSpec.addComplement(complement);
        }

        String sentenceGenerated = realiser.realiseSentence(phraseSpec);

        return sentenceGenerated;
    }

    private Form getVerbForm(String predicateForm) {
        switch (predicateForm) {
            case "infinitive":
                return Form.INFINITIVE;
            case "":
                return null;
            default:
                return Form.INFINITIVE;
        }
    }

    private NumberAgreement getNumberAgreement(String objectNumber) throws NoFreelingKeyException {
        if(objectNumber == null) throw new NoFreelingKeyException("The object or complement don't has a number(singular or plural)");
        else return objectNumber.equals("singular") ? NumberAgreement.SINGULAR : NumberAgreement.PLURAL;
    }
}
