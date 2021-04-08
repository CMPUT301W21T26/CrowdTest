package com.example.crowdtest;

import android.location.Location;

import com.example.crowdtest.experiments.Trial;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test methods shared by all trial types
 */
public class TrialTest {

    private MockClassCreator mockClassCreator = new MockClassCreator();

    /**
     * Test to ensure that correct date is recorded for a trial
     */
    @Test
    public void testDate(){

        Trial trial = mockClassCreator.mockTrial();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dateStamp = new Date();

        String currentDate = sdf.format(dateStamp);

        String trialDate = sdf.format(trial.getTimestamp());

        assertEquals(currentDate, trialDate);

    }




}
