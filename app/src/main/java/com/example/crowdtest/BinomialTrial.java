package com.example.crowdtest;

public class BinomialTrial extends Trial {
    private boolean success;

    /**
     * Constructor for binomial trials
     *
     * @param inputSuccess Input Success/failure
     */
    public BinomialTrial(boolean inputSuccess) {
        success = inputSuccess;
    }

    public boolean isSuccess() {
        return success;
    }
}
