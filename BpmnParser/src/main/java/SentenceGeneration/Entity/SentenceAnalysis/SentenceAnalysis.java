package SentenceGeneration.Entity.SentenceAnalysis;

public class SentenceAnalysis {
    protected String predicate;
    protected String object;
    protected String complement;

    public SentenceAnalysis() {}

    public SentenceAnalysis(String predicate, String object, String complement) {
        this.setPredicate(predicate);
        this.setObject(object);
        this.setComplement(complement);
    }


    public String getPredicate() { return predicate; }
    public void setPredicate(String predicate) { this.predicate = predicate; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public String getComplement() { return complement; }
    public void setComplement(String complement) { this.complement = complement; }

    /**
     *
     * @param s1
     * @param s2
     * @return Returns true if both are null or they have the same value
     */
    private boolean equalStringsOrBothNull(String s1, String s2)  {
        // s1 is null && s2 is null
        if(s1 != null && ! s1.equals(s2)) return false;
        else return s2 == null || s2.equals(s1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SentenceAnalysis) {
            SentenceAnalysis otherSentenceAnalysis = (SentenceAnalysis) obj;
            return  equalStringsOrBothNull(this.getPredicate(), otherSentenceAnalysis.getPredicate()) &&
                    equalStringsOrBothNull(this.getObject(), otherSentenceAnalysis.getObject()) &&
                    equalStringsOrBothNull(this.getComplement(), otherSentenceAnalysis.getComplement());
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

        return result;
    }
}
