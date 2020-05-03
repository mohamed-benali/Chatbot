package main.Entity.Intent;

import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
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

        this.addOutputIntentID(BEGIN_CONTEXT);
        this.addOutputContextID(BEGIN_CONTEXT);

        this.addTrainingPhrase(TRAINING_PHRASE);
    }

    public List<String> getParticipantsName() { return participantsName; }
    public void setParticipantNames(List<String> participantNames) { this.participantsName = participantNames; }


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
    protected Intents buildExtraIntents() {
        return null;
    }

    @Override
    protected Sentences buildTrainingPhrases() {
        return null;
    }

    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {
        return getTrainingPhrases().getTrainingPhrasesList();
    }

}
