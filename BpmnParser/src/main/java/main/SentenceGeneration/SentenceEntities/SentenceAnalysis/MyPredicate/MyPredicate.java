package main.SentenceGeneration.SentenceEntities.SentenceAnalysis.MyPredicate;

public class MyPredicate {
    private String type; //  of the predicate(noun, verb)

    protected String predicate;
    protected String predicateForm;
    private String predicateNumber;


    public String getPredicate() { return predicate; }
    public void setPredicate(String predicate) { this.predicate = predicate; }

    public String getPredicateForm() { return predicateForm; }
    public void setPredicateForm(String predicateForm) { this.predicateForm = predicateForm; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPredicateNumber() { return predicateNumber; }
    public void setPredicateNumber(String predicateNumber) { this.predicateNumber = predicateNumber; }
}
