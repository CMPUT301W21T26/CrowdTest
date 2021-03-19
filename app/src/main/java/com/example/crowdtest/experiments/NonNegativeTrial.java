package com.example.crowdtest.experiments;

public class NonNegativeTrial extends Trial {

    private int count;

    /**
     * Empty constructor for NonNegativeTrial object
     * Necessary for converting Firestore documents to NonNegativeTrial objects
     */
    public NonNegativeTrial(){

        //Empty constructor required

    }

    /**
     * Constructor for non-negative trials
     *
     * @param inputCount
     *  Input count
     */
    public NonNegativeTrial(int inputCount) {
        if (inputCount > 0) {
            count = inputCount;
        } else {
            throw new IllegalArgumentException("Input value must be greater than 0");
        }
    }

    public int getCount() {
        return count;
    }
}

