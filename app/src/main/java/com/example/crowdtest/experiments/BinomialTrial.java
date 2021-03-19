package com.example.crowdtest.experiments;

public class BinomialTrial extends Trial {
    private boolean success;


    public BinomialTrial(){

        //empty constructor required in order to fetch object
    }

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
