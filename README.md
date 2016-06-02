
# Cowbell
An Android alarm clock app that helps you learn and train your brain when you wake up.

##Preview
<img src="http://i.imgur.com/CWWkfNn.png" width="45%"></img>
<img src="http://i.imgur.com/rLW6rEd.png" width="45%"></img>
<img src="http://i.imgur.com/OcKN46S.png" width="45%"></img>
<img src="http://i.imgur.com/UkjW0pS.png" width="45%"></img>
<img src="http://i.imgur.com/GwgvwOk.png" width="45%"></img>
<img src="http://i.imgur.com/tOI1juC.png" width="45%"></img>
<img src="http://i.imgur.com/MC4H24n.png" width="45%"></img>
<img src="http://i.imgur.com/vnAXVlk.png" width="45%"></img>
<img src="http://i.imgur.com/xjAxaHs.png" width="45%"></img>
<img src="http://i.imgur.com/iwhmDHU.png" width="45%"></img>

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


* Ringtone needs to be fully implemented
* On the Card creation screens, when the user presses back, it takes the user to the Alarm list screen, instead of the Card list screen
* On the main alarm list screen, the user cannot press the back button to exit the app and go to the phone's main screen. However, they can still use the home button to do this. 


##How to use the app
When you open the app a brief set of instructions will appear in the front screen until you've set an alarm. On the bottom of the screen there are 3 buttons. Starting from the right, the first one allows you to set-up and add an alarm, the second one to create a set of fill-in the blank cards, and the third one create a set of multiple choice cards. When creating an alarm instance, you can customize the alarm name, time, and game mode. For the fill and in the blank and multiple choice modes, you can create a set of fill-in the blank cards or multiple choice questions before of after setting up an alarm. When you are setting up an alarm you will have the option to select one of three ways to stop the alarm. You can choose to play a reaction game, complete a fill-in the blank card, or answer a multiple choice question. If none of this options is selected, the reaction game will be set as the DEFAULT way of stopping the alarm. The multiple choice game will remain unselectable until the user creates at least four multiple choice flashcards and the fill in the blank will be unselectable until three flashcards are created.  

When you create a set of fill-in the blank, you'll be able to select only one of them to be the one that shows up at the time of shutting down the alarm. When the game is launched, the system will pick a random fill in the blank card from the entire list to be displayed. 

After you've created and selected which card you are gonna use to stop the alarm you are set and ready to wake up and learn! Enjoy!



