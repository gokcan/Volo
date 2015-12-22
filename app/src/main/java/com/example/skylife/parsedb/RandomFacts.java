package com.example.skylife.parsedb;
import java.util.Random;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

public class RandomFacts {

    // Member variables (properties about the object)
    private String[] mFacts = {
            "Facebook, Skype and Twitter are all banned in China.",
            "An apple, potato, and onion all taste the same if you eat them with your nose plugged..",
            "In England, in the 1880’s, “Pants” was considered a dirty word.",
            "You are born with 300 bones; by the time you are an adult you will have 206.",
            "It takes about 8 minutes for light from the Sun to reach Earth.",
            "The first alarm clock could only ring at 4am.",
            "The Titanic was the first ship to use the SOS signal.",
            "Some penguins can leap 2-3 meters out of the water.",
            "Bob Dylan’s real name is Robert Zimmerman.",
            "Bilkent is really precious because of the Ugur Hoca :) ",
            "Mammoths still walked the earth when the Great Pyramid was being built.",
            "Recycling one glass jar saves enough energy to watch TV for 3 hours.",
            "Öznur Hoca is the best teacher at Bilkent University :) "

    };


    public String getFact() {
        String fact = "";

        // Randomly select a fact with help of the rGenerator.
        Random rGenerator = new Random(); // Construct a new Random number generator
        int rNumber = rGenerator.nextInt(mFacts.length);

        fact = mFacts[rNumber];

        return fact;
    }
}