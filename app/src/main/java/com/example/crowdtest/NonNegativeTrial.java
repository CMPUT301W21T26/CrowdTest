package com.example.crowdtest;

public class NonNegativeTrial extends Trial {
    private int count;

    /**
     * Constructor for non-negative trials
     *
     * @param inputCount Input count
     * @throws Exception For when the value is not positive
     */
    public NonNegativeTrial(int inputCount) throws Exception {
        if (inputCount > 0) {
            count = inputCount;
        } else {
            throw new Exception("Input value must be greater than 0");
        }
    }

    public int getCount() {
        return count;
    }
}

