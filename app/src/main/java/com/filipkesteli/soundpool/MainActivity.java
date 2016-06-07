package com.filipkesteli.soundpool;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvPlay;
    private SoundPool soundPool;
    private int soundId;
    private boolean loaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSoundPool();
        initWidgets();
        setupListeners();
    }

    private void initSoundPool() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        //SVAKI sound ce dobiti svoj interni ID
        soundId = soundPool.load(this, R.raw.sound, 1); //interni ID od sound file-a
        //ova load metoda se izvrsava asinhrono -> radit ce u pozadini
        //ovdje ne trebam SERVICE, jer metoda load u sebi ima slozen mehanizam servisa koji radi u pozadini i ne koci program
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
    }

    private void initWidgets() {
        tvPlay = (TextView) findViewById(R.id.tvPlay);
    }

    private void setupListeners() {
        tvPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (loaded) {
                    soundPool.play(soundId, 1.0f, 1.0f, 1, 3, 1.0f);
                }
                return true;
            }
        });
    }
}
