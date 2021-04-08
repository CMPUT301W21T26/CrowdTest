package com.example.crowdtest;

/**
 * RetrieveExperimenterResults interface
 */
public interface RetrieveExperimenterResults {

    /**
     * Helper function to define the app's behavior when an experimenter
     * has been retrieved from the database
     * @param experimenter
     *  Experimenter to retrieve from database
     */
    void onRetrieveExperimenter(Experimenter experimenter);
}
