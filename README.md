# Video-Converter
A simple Java application to convert between .avi .mp4 and .mkv files made for the 2021 May JavaJam. If you never heard about JavaJam, JavaJam is a simple coding competition where praticipants create Java programs on the given topic. It happens on the Java Discord. Visit https://javadiscord.com for invite link and more information.
## Features
Converts a video file into a target file format.
## How to run
At first you want to clone this repo by doing<br>`git clone https://github.com/AttiliaTheHun/Video-Converter.git`
The application was created as IntelliJ IDEA project, so the easiest way to run it is to open the directory as a project in IDEA, select _MainFrame.java_ and run _MainFrame.main()_.<br>
For other IDEs it should work very similarly, you just need to make sure the code can find files under _/src/main/resources/lib/_, they are crucial for the conversion.<br>
The application should work when bundled into _.jar_, too, however running .jars often creates a lot of weird errors (some of them triggered by incorrect .jar creation proccess) which are not nice to fix, so I am not going to give a .jar bundling guide here :)
# Shhh
You should probably try the program before reading further
## How does it work
Right, so first get familiar with the JavaJam topic<br>
> A program that looks legit and then rick-rolls you<br>
Now I can confess this application does not perform any onversion at all. You select a video of yours you want to select, but all the program does is that he copies Rick Astley's _Never Gonna Give You Up_, in the output format you chose, from /resources/lib/ and drops it into the place where the converted video should have appeared. When you open the converted video to check if it works, the quality, etc., guess what you get... Rickrolled, lmao!<br>
Just for clarification files under /resources/lib/ are video files of  Rick Astley's _Never Gonna Give You Up_ in the formats of conversion, renamed to look like a converting library and shortened for their extension so they are not so obvious (the extension is important only when the file is about to be opened, and the user is not suppossed to know about these)
