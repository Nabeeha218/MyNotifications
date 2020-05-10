package com.example.mynotifications;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public class Player {

        private static Player sInstance;
        private Context mContext;
        private MediaPlayer mMediaPlayer;
        public Player(Context context) {
            mContext = context;
        }

        public static Player getInstance(Context context) {
            if (sInstance == null) {
                sInstance = new Player(context);
            }
            return sInstance;
        }

        public void playMusic() {
            Uri tune = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            mMediaPlayer = MediaPlayer.create(mContext, tune);
            mMediaPlayer.start();
        }

        public void stopMusic() {
            if(mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.seekTo(0);
            }
        }
    }

