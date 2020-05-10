package main.SentenceGeneration.SentenceGeneratorServiceAdapter;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;

import main.Exceptions.NoFreelingKeyException;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class SentenceGenerator_SimpleNLG_AdapterImpl implements SentenceGeneratorServiceAdapter {

    // TODO: Make them singleton, so they are only once created
    // TODO: Reason: It is said in SimpleNLG docs that they only need to be created once
    // TODO: So they might be costly to create
    private Lexicon lexicon;
    private NLGFactory nlgFactory;
    private Realiser realiser;

    public SentenceGenerator_SimpleNLG_AdapterImpl() {
        lexicon = Lexicon.getDefaultLexicon();
        nlgFactory = new NLGFactory(lexicon);
        realiser = new Realiser(lexicon);
    }

    private void buildComplement(String complement, SPhraseSpec phraseSpec) {
        if(complement != null)  {
            phraseSpec.addComplement(complement);
        }
    }

    private void buildObject(String object, String objectNumber, SPhraseSpec phraseSpec) throws NoFreelingKeyException {
        if(object != null)      {
            NPPhraseSpec objectPhrase = nlgFactory.createNounPhrase(object);
            NumberAgreement numberAgreement = this.getNumberAgreement(objectNumber);
            objectPhrase.setFeature(Feature.NUMBER, numberAgreement);
            phraseSpec.setObject(objectPhrase);
        }
    }

    private void buildPredicate(String predicate, String predicateType, String predicateNumber, SPhraseSpec phraseSpec) throws NoFreelingKeyException {
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
    }

    private void buildSubject(String subject, SPhraseSpec phraseSpec) {
        if(subject != null)     {
            NPPhraseSpec subjectPhrase = nlgFactory.createNounPhrase(subject);
            subjectPhrase.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR); // Its the default value, anyway
            phraseSpec.setSubject(subjectPhrase);
        }
    }


    @Override
    public String generateSimpleSentence(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException {
        String predicate    = sentenceAnalysis.getPredicate();
        String object       = sentenceAnalysis.getObject();
        String complement   = sentenceAnalysis.getComplement();

        String predicateType    = sentenceAnalysis.getPredicateType();
        String predicateForm    = sentenceAnalysis.getPredicateForm();
        String predicateNumber  = sentenceAnalysis.getPredicateNumber();

        String objectNumber     = sentenceAnalysis.getObjectNumber();
        String complementNumber = sentenceAnalysis.getComplementNumber();

        SPhraseSpec phraseSpec = nlgFactory.createClause();
        this.buildSubject(subject, phraseSpec);
        this.buildPredicate(predicate, predicateType, predicateNumber, phraseSpec);
        this.buildObject(object, objectNumber, phraseSpec);
        this.buildComplement(complement, phraseSpec);

        return realiser.realiseSentence(phraseSpec);
    }



    @Override
    public String generateWhoSubjectSentence(SentenceAnalysis sentenceAnalysis) throws NoFreelingKeyException { // TODO: Remove the subject?
        String predicate    = sentenceAnalysis.getPredicate();
        String object       = sentenceAnalysis.getObject();
        String complement   = sentenceAnalysis.getComplement();

        String predicateType    = sentenceAnalysis.getPredicateType();
        String predicateForm    = sentenceAnalysis.getPredicateForm();
        String predicateNumber  = sentenceAnalysis.getPredicateNumber();

        String objectNumber     = sentenceAnalysis.getObjectNumber();
        String complementNumber = sentenceAnalysis.getComplementNumber();

        SPhraseSpec phraseSpec = nlgFactory.createClause();
        //this.buildSubject(subject, phraseSpec);
        this.buildPredicate(predicate, predicateType, predicateNumber, phraseSpec);
        this.buildObject(object, objectNumber, phraseSpec);
        this.buildComplement(complement, phraseSpec);

        phraseSpec.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHO_SUBJECT);

        String sentenceGenerated = realiser.realiseSentence(phraseSpec);

        return sentenceGenerated;
    }

    @Override
    public String generateWhatObjectSentence(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException {
        String predicate    = sentenceAnalysis.getPredicate();
        String object       = sentenceAnalysis.getObject();
        String complement   = sentenceAnalysis.getComplement();

        String predicateType    = sentenceAnalysis.getPredicateType();
        String predicateForm    = sentenceAnalysis.getPredicateForm();
        String predicateNumber  = sentenceAnalysis.getPredicateNumber();

        String objectNumber     = sentenceAnalysis.getObjectNumber();
        String complementNumber = sentenceAnalysis.getComplementNumber();

        SPhraseSpec phraseSpec = nlgFactory.createClause();
        this.buildSubject(subject, phraseSpec);
        this.buildPredicate(predicate, predicateType, predicateNumber, phraseSpec);
        this.buildObject(object, objectNumber, phraseSpec);
        this.buildComplement(complement, phraseSpec);

        phraseSpec.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHAT_OBJECT);
        String sentenceGenerated = realiser.realiseSentence(phraseSpec);

        return sentenceGenerated;
    }

    @Override
    public String generateWhatObjectSentence_WorkAround_Without_Does(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException {
        String predicate    = sentenceAnalysis.getPredicate();
        String object       = sentenceAnalysis.getObject();
        String complement   = sentenceAnalysis.getComplement();

        String predicateType    = sentenceAnalysis.getPredicateType();
        String predicateForm    = sentenceAnalysis.getPredicateForm();
        String predicateNumber  = sentenceAnalysis.getPredicateNumber();

        String objectNumber     = sentenceAnalysis.getObjectNumber();
        String complementNumber = sentenceAnalysis.getComplementNumber();

        SPhraseSpec phraseSpec = nlgFactory.createClause();
        // TODO: Workaround to create the desired question
        this.buildSubject(object, phraseSpec);
        this.buildObject(subject, "singular", phraseSpec);

        //this.buildSubject(subject, phraseSpec);
        this.buildPredicate(predicate, predicateType, predicateNumber, phraseSpec);
        //this.buildObject(object, objectNumber, phraseSpec);
        this.buildComplement(complement, phraseSpec);

        phraseSpec.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHAT_SUBJECT);

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
