package main.Entity.Intent.Intent.CollaborationManager;

public interface CollaborationManager {

    String getTargetNodeID();
    String getSourceNodeID();

    String getTargetSubject();

    String getSourceSubject();

    boolean isAsCollaborationSource();
    boolean isAsCollaborationTarget();

    void markAsCollaborationSource(String targetNodeID, String targetSubject);
    void markAsCollaborationTarget(String sourceNodeID, String sourceSubject);

    CollaborationManager copy();
}
