package main.SentenceGeneration.SentenceEntities.SentenceAnalysis;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.MyComplement.MyComplement;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.MyObject.MyObject;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.MyPredicate.MyPredicate;

import java.util.Objects;

public class SentenceAnalysis {
    protected MyPredicate predicate;
    protected MyObject object;
    protected MyComplement complement;


    public SentenceAnalysis() {
        this.predicate = new MyPredicate();
        this.object = new MyObject();
        this.complement = new MyComplement();
    }

    public SentenceAnalysis(String predicate, String object, String complement) {
        this.predicate = new MyPredicate();
        this.object = new MyObject();
        this.complement = new MyComplement();

        this.setPredicate(predicate);
        this.setObject(object);
        this.setComplement(complement);
    }

    public SentenceAnalysis(String predicate, String predicateForm,
                            String object, String objectNumber,
                            String complement, String complementNumber) {
        this.predicate = new MyPredicate();
        this.object = new MyObject();
        this.complement = new MyComplement();

        this.setPredicate(predicate);
        this.setPredicateForm(predicateForm);

        this.setObject(object);
        this.setObjectNumber(objectNumber);

        this.setComplement(complement);
        this.setComplementNumber(complementNumber);
    }

    public SentenceAnalysis(String predicate, String predicateType, String predicateForm, String predicateNumber,
                            String object, String objectNumber, String complement, String complementNumber) {
        this.predicate = new MyPredicate();
        this.object = new MyObject();
        this.complement = new MyComplement();

        this.setPredicate(predicate);
        this.setPredicateType(predicateType);
        this.setPredicateForm(predicateForm);
        this.setPredicateNumber(predicateNumber);

        this.setObject(object);
        this.setObjectNumber(objectNumber);

        this.setComplement(complement);
        this.setComplementNumber(complementNumber);
    }

    /*
     *  Predicate
     */
    public String getPredicate() { return predicate.getPredicate(); }
    public void setPredicate(String predicate) { this.predicate.setPredicate(predicate); }
    public String getPredicateForm() { return predicate.getPredicateForm(); }
    public void setPredicateForm(String predicateForm) { predicate.setPredicateForm(predicateForm); }

    public String getPredicateType() { return predicate.getType(); }
    public void setPredicateType(String type) { predicate.setType(type); }
    public String getPredicateNumber() { return predicate.getPredicateNumber(); }
    public void setPredicateNumber(String predicateNumber) { predicate.setPredicateNumber(predicateNumber); }

    /*
     *  Object
     */
    public String getObject() { return object.getObject(); }
    public void setObject(String object) { this.object.setObject(object); }
    public String getObjectNumber() { return object.getObjectNumber(); }
    public void setObjectNumber(String objectNumber) { this.object.setObjectNumber(objectNumber); }

    /*
     *  Complement
     */
    public String getComplement() { return complement.getComplement(); }
    public void setComplement(String complement) { this.complement.setComplement(complement); }
    public String getComplementNumber() { return complement.getComplementNumber(); }
    public void setComplementNumber(String complementNumber) { this.complement.setComplementNumber(complementNumber); }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SentenceAnalysis) {
            SentenceAnalysis otherSentenceAnalysis = (SentenceAnalysis) obj;
            return  Objects.equals(this.getPredicate(),         otherSentenceAnalysis.getPredicate()) &&
                    Objects.equals(this.getObject(),            otherSentenceAnalysis.getObject()) &&
                    Objects.equals(this.getComplement(),        otherSentenceAnalysis.getComplement())&&
                    Objects.equals(this.getPredicateType(),     otherSentenceAnalysis.getPredicateType()) &&
                    Objects.equals(this.getPredicateForm(),     otherSentenceAnalysis.getPredicateForm()) &&
                    Objects.equals(this.getPredicateNumber(),   otherSentenceAnalysis.getPredicateNumber()) &&
                    Objects.equals(this.getObjectNumber(),      otherSentenceAnalysis.getObjectNumber()) &&
                    Objects.equals(this.getComplementNumber(),  otherSentenceAnalysis.getComplementNumber()) ;
        }
        return false;
    }

    @Override
    public String toString() {
        String result = "";

        result += "Predicate: "  + this.getPredicate()   + "\n";
        result += "Object: "     + this.getObject()      + "\n";
        result += "Complement: " + this.getComplement()  + "\n";
        result += "\n";
        result += "Predicate Type: "  + this.getPredicateType()   + "\n";
        result += "Predicate Form: "  + this.getPredicateForm()   + "\n";
        result += "Predicate Number: "  + this.getPredicateNumber()   + "\n";
        result += "Object Number: "     + this.getObjectNumber()      + "\n";
        result += "Complement Number: " + this.getComplementNumber()  + "\n";
        result += "\n";

        return result;
    }


}
