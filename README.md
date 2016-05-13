
# Cowbell
An Android alarm clock app that helps you learn and train your brain when you wake up.

##Preview
<img src="https://cloud.githubusercontent.com/assets/16602140/14996743/56824bee-1130-11e6-8282-98bfa220c32a.png" width="45%"></img> <img src="https://cloud.githubusercontent.com/assets/16602140/14996761/684d77e0-1130-11e6-8bed-dbc0e86f6c47.png" width="45%"></img> <img src="https://cloud.githubusercontent.com/assets/16602140/14996762/684db336-1130-11e6-8b8e-643525c76c2c.png" width="45%"></img> <img src="https://cloud.githubusercontent.com/assets/16602140/14996763/684e7abe-1130-11e6-8484-d2cff5e84014.png" width="45%"></img>


##Getting Started

###Prerequisites
1.  Download Android studio here:
http://developer.android.com/sdk/installing/studio.html

2. Open Android Studio and go to Android Studio > Preferences > Appereance & Behavior > System Settings > Android SDK

3. Click on "Launch Standalone SDK Manager"

4. Make sure you select:
    *Android SDK Build tools v 23.0.3 
    *Android 6.0 (API 23) - "SDK Platform" and "Sources for Android SDK"

5. You are set!

###How to Run the App
1. Pull from this repo and open Android Studio.

2. When Android Studio is started, choose import project and pick the build.gradle file inside the Cowbell folder.
    
4. If Android Studio asks about what gradle to use, choose "gradle wrapper".
    
5. Let Android Studio sync the project

6. Press on the play button and the project and the Deploymant Target will appear.

7. If you haven't created a new Emulator click on "Create a new Emulator" and choose a device with API 23+, otherwise select one of the available emulators.

8. Press ok and wait for the emulator to run the app.

9. Enjoy the app!

###Known Bugs & Issues
The app works without problem and there are no events known till the moment that would make the app crash. In the other hand there are some issues that need to be solved:

* If no fill-in the blank is selected, there needs to be a warning at the time of setting up an alarm and trying to choose the Fill-in the blank option
* If more than two cards are checked and the user unchecks one, nothing will appear at the time of playing the game.
* When the app is completely closed, the states of checkboxs need to be retrieved.

##How to use the app
When you open the app a brief set of instructions will appear in the front screen until you've set an alarm. On the bottom of the screen there are 3 buttons. Starting from the right, the first one allows you to set-up and add an alarm, the second one to create a set of fill-in the blank cards, and the third one create a set of multiple choice cards. You can create a set of fill-in the blank cards or multiple choice questions before of after setting up an alarm. When you are setting up an alarm you will have the option to select one of three ways to stop the alarm. You can choose to play a reaction game, complete a fill-in the blank card, or answer a multiple choice question. If none of this options is selected, the reaction game will be set as the DEFAULT way of stopping the alarm. 

When you create a set of fill-in the blank, you'll be able to select only one of them to be the one that shows up at the time of shutting down the alarm. Only your last selection will count. That means that if you check/uncheck more than one, only the last check/uncheck will be taken into account.

After you've created and selected which card you are gonna use to stop the alarm you are set and ready to wake up and learn! Enjoy!



