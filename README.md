# CrowdTest

Application to allow users to create and contribute to crowdsourced experiments. 
The following four types of experiments can be created and contributed to:

* Counts - How many occurances of something did you see?
* Binomial - Success/Failure trials, such as a coin flip.
* Non Negative Integer Counts - Each trial results in a non negative integer
* Measurement - Repeated measurements where decimal values can be recorded.

Wiki can be found [here](https://github.com/CMPUT301W21T26/CrowdTest/wiki).

Javadocs can be accessed through the wiki.

# Using CrowdTest:

## User Profile Information:
1. A user profile will be automatically created upon installing the application.
2. To your user profile, select the "My Profile" button on the main screen
3. From this page, select the pencil icon on the top right corner
4. Update your email and username fields, and select "Ok". To exit, select "Cancel".

## Creating a New Experiment
1. Select the "CREATE NEW" button from the home screen
2. Select the type of experiment you wish to create
3. Follow prompts to enter a title, description, region and minimum required trials for the experiment.
4. If you wish your experiment to be geolocation enabled, set geolocation toggle to green color.
5. Select "Publish" to create your Experiment.
6. You can select "Cancel" to exit this process.

Note that you will automatically be subscribed to any experiments that you create.

## View Owned, Subscribed, and Unpublished Experiments
1. Select the "MY EXPERIMENTS" button
2. This will open on a screen displaying all experiments that you created that are currently published.
3. To view experiments that you are subscribed to, select the "SUBSCRIBED EXPERIMENTS" tab at the top of the screen. This wil only show subscribed experiments that are published. 
4. To view experiments that you have unpublished, select the "UNPUBLISHED EXPERIMENTS" tab at the top of the screen.

## Unpublish Experiments
1. From the "SEARCH" or "MY EXPERIMENTS" options, you can unpublish any experiment you own.
2. Long click on one of the displayed experiments that you own. This will open a context menu. 
3. Select "Unpublish".

## View Owned and Unpublished Experiments
1. Select the "MY EXPERIMENTS" button
2. Click on the "UNPUBLISHED EXPERIMENTS" tab.
3. Experiments that are both owned and unpublished are displayed.

Note: you cannot view or interact with unpublished experiments that are owned by other users

## Republish an Owned Experiment
1. Select the "MY EXPERIMENTS" button
2. Click on the "UNPUBLISHED EXPERIMENTS" tab.
3. Long click on the experiment that you want to republish.
4. A context menu will appear reading "Republish" and "Delete". Select "Republish". Experiment will no be published and viewable once more.

## Delete an Experiment
1. Follow the instructions above for unpublishing an experiment.
2. From the main page of the app, select "MY EXPERIMENTS".
3. Click on the "UNPUBLISHED EXPERIMENTS" tab.
4. Long click on the experiment that you want to delete.
5. A context menu will appear reading "Republish" and "Delete". Select "Delete". Experiment will be fully removed and can no longer be accessed by any user.

## Search for Experiments
1. Select the "SEARCH" button
2. You will be brought to a screen where all experiments are displayed
3. Enter the keyword you would like to search for at the top of the page and hit "SEARCH"
4. Now, only experiments with the keyword you entered in their titles or description will be displayed.
5. To return to seeing all experiments, clear the search input and select "SEARCH" again

Note: you can only see published expeirments in the Search page.  
Note that if you are not the owned of an experiment, the unpublish option will not be made available to you.

## Subscribe to an Experiment
1. From the "SEARCH" menu, you can view any published experiments that you may not own and may not be subscribed to.
2. To subscribe to an experiment, long click the experiment until a context menu appears.
3. Select the "Subscribe" option to subscribe to the experiment.
4. The experiment should now appear in your "SUBSCRIBED EXPERIMENTS" tab in the "MY EXPERIMENTS" menu.

## Unsubscribe from an Experiment
1. From the "SEARCH" or "MY EXPERIMENTS" menus, you can view experiments that you may already be subscribed to.
2. To unsubscribe from an experiment, long click the experiment until a context menu appears.
3. Select the "Unsubscribe" option to unsubscribe from the experiment.
4. The experiment should now disappear from the "SUBSCRIBED EXPERIMENTS" tab in the "MY EXPERIMENTS menu.

## Participate in an Experiment
1. To participate in an experiment, you mush first be subscribed to it.
2. Once subscribed, you can add trials to the experiment by first clicking on it from the "SEARCH" or "MY EXPERIMENTS" menus to view it.
3. Once the experiment's main page appears, you can add trials via the buttons located near the bottom of the screen.
    * For Binomial Experiments, the right and left buttons represent a pass and a fail, respectively.
    * For Count Experiments, one button is available to increment your overall count for the experiment.
    * For Measurement Experiments, a number entry and button are available for entering a measurement value and adding the trial, respectively.
    * For Non-Negative Experiments, a number entry and button are avaialble for entering a non-negative value and adding the trial, respectively.

## View Experiment Statistics
1. To view an experiment, simply click on a listed experiment from the "SEARCH" or "MY EXPERIMENTS" menus.
2. Within the experiment's main page, clicking the "DETAILS" option will bring up the "STATISTICS" page for the experiment.
3. Within this page, you can view the statistics of the experiment's trials, as well as two plots that detail trial information.

## View Experiment Participants
1. To view the participants for an experiment, click the icon in the top-right of an experiment's main page.
2. A scrollable list of subscribed users will appear.
3. Clicking on a given user will open their user profile page.

## Blacklist a Participant of an Experiment
1. If you are an owner of an experiment, you may blacklist any given participant whose trials you do not want considered.
2. Within the participants list for an owned experiment, long-clicking a listed user will bring up a context menu.
3. From there, selecting the "Blacklist" option will blacklist them from participating in the experiment further.

## Un-blacklist a Participant from an Experiment
1. If you are an owner of an experiment, you may un-blacklist a given participant who may have been blacklist in the past.
2. Within the participants list for an owned experiment, long-clicking a blacklisted user will bring up a context menu.
3. From there, selecting the "Un-Blacklist" option will un-blacklist them from the experiment.

# Sources:

Title: Tab Layout with Different Fragments  
Author: Master Coding  
Date: 2021-03-13  
License: Public Domain  
Availability: https://www.youtube.com/watch?v=HHd-Fa3DCng&ab_channel=MasterCoding  

Title: How to retrieve an Unique ID to identify Android devices?  
Author: Sylvain Saurel    
Date: 2021-03-15  
License: Public Domain  
Availability: https://ssaurel.medium.com/how-to-retrieve-an-unique-id-to-identify-android-devices-6f99fd5369eb

Title: firestore read is asynchronous and I want synchronous behaviour  
Author: Luca Murra, https://stackoverflow.com/users/8579556/luca-murra  
Date: 2021-03-16  
License: Public Domain  
Availability: https://stackoverflow.com/questions/53970276/firestore-read-is-asynchronous-and-i-want-synchronous-behaviour  

Title: How to get my activity context?  
Author: hasanghaforian, https://stackoverflow.com/users/1043882/hasanghaforian  
Date: 2021-03-16  
License: CC BY-SA  
Availability: https://stackoverflow.com/questions/12320857/how-to-get-my-activity-context  

Title: Identifying App Installations  
Author: Android Development Documentation  
Date: 2021-03-16  
License: Apache 2.0  
Availability: https://android-developers.googleblog.com/2011/03/identifying-app-installations.html  

Title: Perform simple and compound queries in Cloud Firestore  
Author: Firebase  
Date: 2021-03-16  
License: Apache 2.0  
Availability: https://firebase.google.com/docs/firestore/query-data/queries  

Title: AsyncTask  
Author: Android Development Documentation  
Date: 2021-03-16  
License: Apache 2.0  
Availability: https://developer.android.com/reference/android/os/AsyncTask.html  

Title: How to Structure Your Data  
Author: Firebase  
Date: 2021-03-17  
License: Public Domain  
Availability: https://www.youtube.com/watch?v=haMOUb3KVSo&ab_channel=Firebase  

Title: Better Arrays in Cloud Firestore!  
Author: Todd Kerpelman  
Date: 2021-03-17  
License: Public Domain  
Availability: https://firebase.googleblog.com/2018/08/better-arrays-in-cloud-firestore.html  

Title: Mockito - Create Mock  
Author: tutorialspoint  
Date: 2021-03-18  
License: Public Domain  
Availability: https://www.tutorialspoint.com/mockito/mockito_create_mock.htm  

Title: Unit Testing with Mockito & Firebase  
Author: Jorge Gil, https://stackoverflow.com/users/3116787/jorge-gil  
Date: 2021-03-18  
License: CC BY-SA  
Availability: https://stackoverflow.com/questions/53473006/unit-testing-with-mockito-firebase  

Title: Is there any way to make my app wait for data to be retrieved from Firebase before continuing the code?  
Author: Ban Markovic, https://stackoverflow.com/users/4039786/ban-markovic  
Date: 2021-03-19  
License: CC BY-SA  
Availability: https://stackoverflow.com/questions/57116491/is-there-any-way-to-make-my-app-wait-for-data-to-be-retrieved-from-firebase-befo  

Title: Sort ArrayList of custom Objects by property  
Author: Michael Myers, https://stackoverflow.com/users/13531/michael-myers  
Date: 2021-03-20  
License: CC BY-SA  
Availability: https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property  

Title: Creating a Simple Bar Graph for your Android Application (part 1/2)  
Author: CodingWithMitch  
Date: 2021-03-24  
License: Public Domain  
Availability: https://www.youtube.com/watch?v=pi1tq-bp7uA&ab_channel=CodingWithMitch  

Title: Java Program To Calculate Median Array | 4 Methods   
Author: javatutoring  
Date:  2021-03-24  
License: Public Domain  
Availability: https://javatutoring.com/java-calculate-median/  

Title: How to set the x-axis label with MPAndroidChart  
Author: Prabs, https://stackoverflow.com/users/2820534/prabs  
Date:  2021-03-24  
License: CC BY-SA  
Availability: https://stackoverflow.com/questions/39945375/how-to-set-the-x-axis-label-with-mpandroidchart  

Title: Getting duplicate values at xAxis MPChart  
Author: Rahul Sharma, https://stackoverflow.com/users/3058050/rahul-sharma  
Date:  2021-03-24  
License: CC BY-SA  
Availability: https://stackoverflow.com/questions/43890668/getting-duplicate-values-at-xaxis-mpchart  

Title: How to remove description from chart in MPAndroidChart?  
Author: Rahul Singh, https://stackoverflow.com/users/10521937/rahul-singh  
Date:  2021-03-25  
License: CC BY-SA  
Availability: https://stackoverflow.com/questions/27566916/how-to-remove-description-from-chart-in-mpandroidchart#:~:text=you%20can%20remove%20it%20by%20simply%20passing%20null%20into%20it.  

Title: 011 Grouped Bar Chart : MP Android Chart Tutorial  
Author: Sarthi Technology  
Date:  2021-03-25  
License: Public Domain  
Availability: https://www.youtube.com/watch?v=Bd76zMHdrDE  

Title: Round the given number to nearest multiple of 10  
Author: GeeksforGeeks  
Date:  2021-03-26  
License: Public Domain  
Availability: https://www.geeksforgeeks.org/round-the-given-number-to-nearest-multiple-of-10/  

Title: Convert java.util.Date to java.time.LocalDate  
Author: JodaStephen, https://stackoverflow.com/users/38896/jodastephen   
Date:  2021-03-27  
License: CC BY-SA  
Availability: https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate  
