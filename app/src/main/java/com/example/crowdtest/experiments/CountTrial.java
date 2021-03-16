package com.example.crowdtest.experiments;

public class CountTrial extends Trial {
    private int count;

    /**
     * Constructor for count trials
     *
     * @param inputCount Input count
     */
    public CountTrial(int inputCount) {
        count = inputCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
