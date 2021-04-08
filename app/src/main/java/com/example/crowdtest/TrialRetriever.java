package com.example.crowdtest;

import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegativeTrial;

public interface TrialRetriever {
    void getBinomialTrials (BinomialTrial binomialTrial);
    void getCountTrials (CountTrial countTrial);
    void getNonNegativeTrials(NonNegativeTrial nonnegativeTrial);
    void getMeasurementTrials (MeasurementTrial measurementTrial);
}
