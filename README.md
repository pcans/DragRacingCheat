## DragRacingCheat ##


I play a nice Android game yesterday, but was frustrated by the lack of in game money.  
Instead of playing a lot in order to gain in game money I decided to better invest my time in cheating.  

The game (really nice one) is [DragRacing Bike Edition](https://play.google.com/store/apps/details?id=com.creativemobile.dragracingbe)  
If you like motorbike and got an Android phone, you must try it, it is a must!

### Reverse engineering ###
The game database is located in:  /data/data/com.creativemobile.dragracingbe/files/gsd2.svf. It is just a serialized java byte array.  
But the byte array data is encoded. By looking at the pattern in the data, you can see that there is a kind of xor at work here.  
So i decompiled APK and found out that even with obfuscation at work they forgotted the encryption java class. Too bad for them, it will be easier for me.  
Found the plain text key in class `com/creativemobile/dragracingbe/Game.smalli`, and 2 methods encode/decode in class `com/creativemobile/dragracingbe/JavaSettingsCrypt.smalli`. I converted thoses methods to java and then found out that once decoded, the binary blob is a serialized java class, and starting at offset 30 the integer containing my money.

I build this tool to read the database, decode it, patch it to a new amount of money, re-encode it.

The tool work flawlessly here. :)  
The version I messed with was the v1.0.4. If they update their file format or their encryption mechanism, this cheat will break.

### How to use the cheat ###
Just call the `main` method in `DragRacingCheat` class with the following parameters:  

    Usage: DragRacingCheat input value output  
      input: Svf input file to read from.  
      value: The new value for the money.  
      output: Svf output file to write to.  

You should then see the following output:

    Open and extract data from input file...
    Decode gameData...
     -> Actual money: 2431
    Patch gameData...
     -> New money: 1000000
    Encode gameData...
    Save data to binary out file...
     -> file /Volumes/Data/Perso/DragRacing/gsd2-patch.svf written

Please do check that the old amount of displayed money is the correct one before moving the database back to your phone.

### Disclamer ###
Use at your own risk. It is your phone, your data, your choice. I cannot be responsible to what you do with it.

