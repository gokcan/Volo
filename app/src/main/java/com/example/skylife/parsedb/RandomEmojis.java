package com.example.skylife.parsedb;

import java.util.Random;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

public class RandomEmojis {

    // Member variables (properties about the object)
    private int[] mEmojis = {
            0x1F601,
            0x1F61A,
            0x2764,
            0x2714,
            0x1F680,
            0x26C4,
            0x1F42C,
            0x1F466,
            0x1F60E,
};


    public String getEmoji() {
        String emoji = "";

        // Randomly select a emoji with help of the rGenerator.
        Random rGenerator = new Random(); // Construct a new Random number generator
        int rNumber = rGenerator.nextInt(mEmojis.length);

        emoji = new String(Character.toChars(mEmojis[rNumber]));

        return emoji;
    }
}