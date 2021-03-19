package com.example.crowdtest.experiments;

/**
 * NonNegative trial class
 */
public class NonNegativeTrial extends Trial {

    // NonNegativeTrial attributes
    private int count;

    /**
     * NonNegativeTrial constructor
     * @param inputCount
     *  Non-negative input count
     */
    public NonNegativeTrial(int inputCount) {
        if (inputCount > 0) {
            count = inputCount;
        } else {
            throw new IllegalArgumentException("Input value must be greater than 0");
        }
    }

    /**
     * Function for getting the non-negative trial count
     * @return
     *  Non-negative trial count
     */
    public int getCount() {
        return count;
    }
}

