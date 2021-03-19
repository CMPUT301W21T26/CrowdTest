package com.example.crowdtest.experiments;

/**
 * Class to represent a BinomialTrial result
 */
public class BinomialTrial extends Trial {

    // BinomialTrial attributes
    private boolean success;

    /**
     * Empty BinomialTrial constructor
     */
    public BinomialTrial(){

        //Required for firestore access
    }

    /**
     * BinomialTrial constructor
     * @param inputSuccess
     *  Input success/failure
     */
    public BinomialTrial(boolean inputSuccess) {
        success = inputSuccess;
    }

    /**
     * Function to check whether a binomial trial is a success or failure
     * @return
     *  True if success, false if failure
     */
    public boolean isSuccess() {
        return success;
    }



}
