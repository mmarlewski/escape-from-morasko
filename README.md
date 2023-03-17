# escape-from-morasko 

![image](assets/textures/coin.png)

Open in Intellij or Android Studio

To launch on Android you need to download Android Studio
and then download via Android Studio's SDK Manager:
- some SDK platforms
- SDK tools
- (unwrap 'show package details')
- a virtual machine

Android launch option should be available once you open the project

To launch the desktop version go to desktop/src/com/efm/DesktopLauncher and run the main function

To run all the unit tests add a launch option:
- unwrap launch options
- select 'edit configuration'
- click '+' and 'gradle'
- give it a name like 'Unit Tests'
- in run select ':tests:test'

The most important files are in the 'core' module/folder

![image](assets/textures/coin.png)