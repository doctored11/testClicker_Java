package nth11.game.eggtapper.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;

public class SoundPlayer {
    private Context context;

    public SoundPlayer(Context context) {
        this.context = context;
    }

    public void playSound(String fileName) {
        int soundResourceId = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
        if (soundResourceId == 0) {
            // Файл не найден
            return;
        }

        Thread soundThread = new Thread(() -> {
            MediaPlayer mediaPlayer = null;
            try {
                mediaPlayer = MediaPlayer.create(context, soundResourceId);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(mp -> {
                    mp.release();
                });
            } catch (Exception e) {
                e.printStackTrace();
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
            }
        });
        soundThread.start();
    }
}

