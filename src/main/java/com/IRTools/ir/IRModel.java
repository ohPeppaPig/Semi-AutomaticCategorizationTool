package com.IRTools.ir;


import com.IRTools.document.ArtifactsCollection;
import com.IRTools.document.SimilarityMatrix;
import com.IRTools.document.TermDocumentMatrix;

public interface IRModel {
    public SimilarityMatrix Compute(ArtifactsCollection source, ArtifactsCollection target);

//    public SimilarityMatrix Compute(ArtifactsCollection source, ArtifactsCollection target);

    public TermDocumentMatrix getTermDocumentMatrixOfQueries();

    public TermDocumentMatrix getTermDocumentMatrixOfDocuments();
}
