package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textTime;
    SeekBar seekBar;
    Boolean counterActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        textTime.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("Go");
        counterActive = false;

    }

    public void timerStart(View view) {
        if (counterActive) {
            resetTimer();

        } else {
            counterActive = true;
            seekBar.setEnabled(false);
            goButton.setText("STOP");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimerFunction((int) l / 1000);
                }

                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }


    }

    public void updateTimerFunction(int secondsLeft) {

        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);
        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        textTime.setText(Integer.toString(minutes) + " : " + secondString);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textTime = findViewById(R.id.textTime);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        goButton = findViewById(R.id.button);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimerFunction(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}