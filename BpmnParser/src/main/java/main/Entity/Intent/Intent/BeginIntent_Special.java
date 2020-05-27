package main.Entity.Intent.Intent;

import main.Entity.Intent.Intent.myIntent;
import main.Entity.Intent.Intents;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Special because is created 100% manually
public class BeginIntent_Special extends myIntent {
    public static String BEGIN_CONTEXT = "BEGIN_CONTEXT";
    public static String TRAINING_PHRASE = "BEGIN";


    private List<String> participantsName;

    public BeginIntent_Special() throws IOException {
        super(BEGIN_CONTEXT);

        //this.addOutputIntentID(BEGIN_CONTEXT);
        this.addOutputContextID(BEGIN_CONTEXT);

        this.addTrainingPhrase(TRAINING_PHRASE);
    }

    public List<String> getParticipantsName() { return participantsName; }
    public void setParticipantNames(List<String> participantNames) { this.participantsName = participantNames; }

    @Override
    public String createCode() {
        char quote = '"';
        String varName = "beginiIntentSpecial" + this.getName();
        StringBuilder response = new StringBuilder();
        response.append("BeginIntent_Special ").append(varName).append(" = new BeginIntent_Special(")
                .append(quote).append(getName()).append(quote).append(");").append("\n");

        response.append("List<String> participantNames = new ArrayList<>();" + "\n");
        for(String participantName : this.getParticipantsName()) {
            response.append("participantNames.add(").
                    append(quote).append(participantName).append(quote).append(");").append("\n");
        }

        response.append(varName).append(".setParticipantNames(participantNames);");

        return response.toString();
    }


    // PRE: I supose that there will be always at least one participant --> participantsName.size() >= 1
    protected List<String> makeResponse() {
        List<String> responses = new ArrayList<String>();
        String response = "Which role do you want to know about? \n";

        response += participantsName.get(0);
        for(int i = 1; i < this.participantsName.size() -1; ++i) {
            response +=  ", " + participantsName.get(i);
        }
        if(participantsName.size() != 1) response += " or " + participantsName.get(participantsName.size()-1 );

        responses.add(response);

        return responses;
    }




    @Override
    public Intents buildExtraIntents() {
        return new Intents();
    }

    @Override
    protected Sentences buildTrainingPhrasesToParaphrase() {
        return new Sentences();
    }

    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {
        return getTrainingPhrases().getTrainingPhrasesList();
    }

    @Override
    protected void setDefaultNullTrainingPhrase() {
        trainingPhrases.setDefaultTrainingPhraseType(null);
        trainingPhrases.setHasNullTrainingPhrase(false);
    }
}
