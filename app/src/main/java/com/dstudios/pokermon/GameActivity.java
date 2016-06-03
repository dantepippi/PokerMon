package com.dstudios.pokermon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GameActivity extends AppCompatActivity {
    Boolean statusPaused;
    private DatabaseReference mFirebaseDatabaseReference;
    MyList<Level> mLevelsList;
    Level currentLevel;
    private int levelNumber = 1, lvlCount = 1, millisLeft = 0;
    private TextView mTxtNivel, mTxtBlinds, mTextContador;
    private int blindInterval;
    private Button mBtSkip, mBtReset, mBtPlayPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        statusPaused = true;

        blindInterval = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("BLIND_INTERVAL", 10);
        mLevelsList = new MyList<Level>();
        mBtPlayPause = (Button) findViewById(R.id.start);
        mBtSkip = (Button) findViewById(R.id.skip);
        mBtReset = (Button) findViewById(R.id.reset);

        mTextContador = (TextView) findViewById(R.id.txtContador);
        mTxtNivel = (TextView) findViewById(R.id.txtNivel);
        mTxtBlinds = (TextView) findViewById(R.id.txtBlinds);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child("structure").child(getFirebaseUserUid()).orderByChild("small_blind").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot levelSnapShot : dataSnapshot.getChildren()) {
                    Level lvl = levelSnapShot.getValue(Level.class);
                    lvl.setNumber(lvlCount);
                    if (mLevelsList.getSize() == 0) {
                        currentLevel = lvl;
                        updateInfo();
                    }
                    lvlCount++;
                    mLevelsList.add(lvl);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBtReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMillisLeftWithBlindInterval();
            }
        });
        mBtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLevel();
            }
        });

        mBtPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusPaused = !statusPaused;
                if (statusPaused) {
                    mBtPlayPause.setText("PLAY");
                }
                else {
                    mBtPlayPause.setText("PAUSE");
                    new CountDownTimer(1000000000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            if (!statusPaused) {
                                if (millisLeft != 0)
                                    millisLeft -= 1000;
                                else
                                    nextLevel();

                            }
                            mTextContador.setText("" + formataTempo(millisLeft));
                        }

                        public void onFinish() {
                            mTextContador.setText("done!");
                        }
                    }.start();
                }
            }
        });
    }

    private void nextLevel() {
        currentLevel = mLevelsList.get(levelNumber);
        levelNumber++;
        updateInfo();
    }

    private void updateInfo() {
        mTxtNivel.setText(currentLevel.getNumber().toString());
        mTxtBlinds.setText("Blinds: "+ currentLevel.getSmall_blind()+"/" + currentLevel.getBig_blind());
        mTextContador.setText(blindInterval + ":00");
        setMillisLeftWithBlindInterval();
    }

    private void setMillisLeftWithBlindInterval() {
        millisLeft = blindInterval * 60 * 1000;
    }

    public String formataTempo(long millis) {
        String output;
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;
        String secondsD = String.valueOf(seconds);
        String minutesD = String.valueOf(minutes);
        if (seconds < 10)
            secondsD = "0" + seconds;
        if (minutes < 10)
            minutesD = "0" + minutes;
        output = minutesD + ":" + secondsD;
        return output;
    }

    private String getFirebaseUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}