package com.dstudios.pokermon;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {
    Boolean statusPaused;
    private DatabaseReference mFirebaseDatabaseReference;
    MyList<Level> mLevelsList;
    Level currentLevel, nextLevel;
    private int levelNumber = 1, lvlCount = 1, millisLeft = 0;
    private TextView mTxtNivel, mTxtBlinds, mTextContador, mTxtNextBlinds;
    private int blindInterval;
    private Button mBtSkip, mBtReset, mBtPlayPause;
    private CountDownTimer mCountDown;
    ViewGroup mViewGroup;

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
        mViewGroup = (ViewGroup) findViewById(R.id.coordinator_layout_game);
        mTextContador = (TextView) findViewById(R.id.txtContador);
        mTxtNivel = (TextView) findViewById(R.id.txtNivel);
        mTxtBlinds = (TextView) findViewById(R.id.txtBlinds);
        mTxtNextBlinds = (TextView) findViewById(R.id.nextBlinds);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child("structure").child(getFirebaseUserUid()).child("Fast").orderByChild("sum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot levelSnapShot : dataSnapshot.getChildren()) {
                    Level lvl = levelSnapShot.getValue(Level.class);
                    lvl.setNumber(lvlCount);
                    lvlCount++;
                    mLevelsList.add(lvl);

                }
                switchToLevel(1);
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
                skipLevel();
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
                    if (mCountDown == null) {
                        mCountDown = new CountDownTimer(1000000000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                if (!statusPaused) {
                                    if (millisLeft != 0)
                                        millisLeft -= 1000;
                                    else
                                        skipLevel();

                                }
                                mTextContador.setText("" + formataTempo(millisLeft));
                            }
                            public void onFinish() {
                                mTextContador.setText("");
                            }
                        }.start();
                    }
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mViewGroup.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    private void skipLevel() {
        switchToLevel(levelNumber);
        levelNumber++;
    }
    private void switchToLevel(int lvl) {
        try {
            currentLevel = nextLevel = mLevelsList.get(lvl-1);
            if (lvl < lvlCount)
                nextLevel = mLevelsList.get(lvl);
            else
                nextLevel = currentLevel;
        } catch (IndexOutOfBoundsException e) {
            Snackbar mySnackBar = Snackbar.make(mViewGroup, "Level info not found", Snackbar.LENGTH_SHORT);
            mySnackBar.show();
        }
        updateInfo();
    }

    private void updateInfo() {
        mTxtNivel.setText("Level " + currentLevel.getNumber().toString());
        mTxtBlinds.setText("Blinds "+ currentLevel.getSmall_blind()+"/" + currentLevel.getBig_blind());
        mTxtNextBlinds.setText("Next level "+ nextLevel.getSmall_blind()+"/" + nextLevel.getBig_blind());
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