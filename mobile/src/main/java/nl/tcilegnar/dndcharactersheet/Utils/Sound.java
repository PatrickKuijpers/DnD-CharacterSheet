package nl.tcilegnar.dndcharactersheet.Utils;

import android.media.MediaPlayer;

import java.util.Random;

import nl.tcilegnar.dndcharactersheet.App;

/** Origin of sounds: http://www.freesfx.co.uk/sfx */
public class Sound {
    public static void play(int resId) {
        (MediaPlayer.create(App.getContext(), resId)).start();
    }

    public static void playRandom(int... resIds) {
        if (resIds.length > 0) {
            int randomIndex = new Random().nextInt(resIds.length);
            int randomResId = resIds[randomIndex];
            play(randomResId);
        }
    }
}
