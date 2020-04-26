package main.SentenceGeneration.SentenceEntities.SentenceAnalysis;

public class SimpleSentenceAnalysis extends SentenceAnalysis {

    public SimpleSentenceAnalysis() {
        super();
    }

    public SimpleSentenceAnalysis(String predicate, String object, String complement) {
        super(predicate, object, complement);
    }


    public SimpleSentenceAnalysis(String predicate, String predicateForm,
                                  String object, String objectNumber,
                                  String complement, String complementNumber) {
        super(predicate, predicateForm, object, objectNumber, complement, complementNumber);
    }

    public SimpleSentenceAnalysis(String predicate, String predicateType, String predicateForm, String predicateNumber,
                                  String object, String objectNumber,
                                  String complement, String complementNumber) {
        super(predicate, predicateType, predicateForm, predicateNumber,
                object, objectNumber,
                complement, complementNumber);
    }
}
