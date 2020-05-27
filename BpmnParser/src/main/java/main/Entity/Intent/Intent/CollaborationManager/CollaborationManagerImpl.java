package main.Entity.Intent.Intent.CollaborationManager;

import java.util.Objects;

public class CollaborationManagerImpl implements CollaborationManager {

    protected boolean isCollaborationSource;
    protected String targetNodeID;
    protected String targetSubject;

    protected boolean isCollaborationTarget;
    protected String sourceNodeID;
    protected String sourceSubject;

    public CollaborationManagerImpl() {
        isCollaborationSource = false;
        isCollaborationTarget = false;
    }



    //region REGION: Override(equals) + copy
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CollaborationManager) {
            CollaborationManager collaborationManager = (CollaborationManager)obj;
            return  Objects.equals(this.isAsCollaborationSource(), collaborationManager.isAsCollaborationSource()) &&
                    Objects.equals(this.isAsCollaborationTarget(), collaborationManager.isAsCollaborationTarget()) &&
                    Objects.equals(this.getSourceNodeID(),  collaborationManager.getSourceNodeID()) &&
                    Objects.equals(this.getSourceSubject(), collaborationManager.getSourceSubject()) &&
                    Objects.equals(this.getTargetNodeID(),  collaborationManager.getTargetNodeID()) &&
                    Objects.equals(this.getTargetSubject(), collaborationManager.getTargetSubject());
        }
        else return false;
    }


    @Override
    public String toString() {
        String result = "";

        result += "isCollaborationSource:    " + this.isAsCollaborationSource() + "\n";
        result += "targetNodeID: " + this.getTargetNodeID() + "\n";
        result += "targetSubject:    " + this.getTargetSubject() + "\n";
        result += "isCollaborationTarget:    " + this.isAsCollaborationTarget() + "\n";
        result += "sourceNodeID: " + this.getSourceNodeID() + "\n";
        result += "sourceSubject:    " + this.getSourceSubject() + "\n";
        result += "\n";

        return result;
    }


    @Override
    public CollaborationManager copy() {
        CollaborationManager copy = new CollaborationManagerImpl();
        if(this.isAsCollaborationSource()) {
            copy.markAsCollaborationSource(this.getTargetNodeID(), this.getTargetSubject());
        }
        if(this.isAsCollaborationTarget()) {
            copy.markAsCollaborationTarget(this.getSourceNodeID(), this.getSourceSubject());
        }
        return copy;
    }
    //endregion




    @Override
    public String getTargetNodeID() { return targetNodeID; }

    @Override
    public String getSourceNodeID() { return sourceNodeID; }

    @Override
    public String getTargetSubject() { return targetSubject; }

    @Override
    public String getSourceSubject() { return sourceSubject; }

    @Override
    public boolean isAsCollaborationSource() { return isCollaborationSource; }

    @Override
    public boolean isAsCollaborationTarget() { return isCollaborationTarget; }

    @Override
    public void markAsCollaborationSource(String targetNodeID, String targetSubject) {
        isCollaborationSource = true;
        this.targetNodeID = targetNodeID;
        this.targetSubject = targetSubject;
    }

    @Override
    public void markAsCollaborationTarget(String sourceNodeID, String sourceSubject) {
        isCollaborationTarget = true;
        this.sourceNodeID = sourceNodeID;
        this.sourceSubject = sourceSubject;
    }




}
