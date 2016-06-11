package com.dstudios.pokermon;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {
    //private Boolean statusPaused;
    private DatabaseReference mFirebaseDatabaseReference;
    private MyList<Level> mLevelsList;
    private Level currentLevel, nextLevel;
    private int levelNumber = 1, lvlCount, millisLeft = 0;
    private TextView mTxtNivel, mTxtBlinds, mTextContador, mTxtNextBlinds;
    private int blindInterval;
    private ImageButton mBtSkip;
    private ImageButton mBtReset;
    private ImageButton mBtPlay, mBtPause;
    private CountDownTimer mCountDown;
    private ViewGroup mViewGroup;
    private Tournament mTournament;
    private DatabaseReference mTournamentRef;
    private ImageButton mBtAddPlayer;
    private Game mCurrentGame;
    private String gameKey;
    private DatabaseReference mCurrentGameRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String tournamentKey = getIntent().getStringExtra(Utils.TOURNAMENT_KEY);
        mCurrentGame = new Game();
        mCurrentGame.setTournament(tournamentKey);
        mCurrentGame.setPaused(true);
        mCurrentGameRef = Utils.mDatabaseRef.child(Utils.GAMES).child(getFirebaseUserUid()).push();
        mCurrentGameRef.setValue(mCurrentGame);
        gameKey = mCurrentGameRef.getKey();
        mTournamentRef = Utils.mDatabaseRef.child(Utils.TOURNAMENTS).child(getFirebaseUserUid()).child(tournamentKey);
        mTournamentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTournament = dataSnapshot.getValue(Tournament.class);
                if (mTournament == null) {
                    finish();
                    return;
                }
                blindInterval = mTournament.getBlind_interval();
                mLevelsList = new MyList<>();
                lvlCount = 1;
                mFirebaseDatabaseReference.child(Utils.STRUCTURE).child(getFirebaseUserUid()).child(mTournament.getStructure()).orderByChild("sum").addListenerForSingleValueEvent(new ValueEventListener() {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mBtPlay = (ImageButton) findViewById(R.id.play);
        mBtPause = (ImageButton) findViewById(R.id.pause);
        mBtSkip = (ImageButton) findViewById(R.id.skip);
        mBtReset = (ImageButton) findViewById(R.id.add_player);
        mBtAddPlayer = (ImageButton) findViewById(R.id.reset);
        mViewGroup = (ViewGroup) findViewById(R.id.coordinator_layout_game);
        mTextContador = (TextView) findViewById(R.id.txtContador);
        mTxtNivel = (TextView) findViewById(R.id.txtNivel);
        mTxtBlinds = (TextView) findViewById(R.id.txtBlinds);
        mTxtNextBlinds = (TextView) findViewById(R.id.nextBlinds);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


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

        mBtAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTournamentRef.child("players").push();
            }
        });
        mBtPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentGame.setPaused(true);
                mCurrentGameRef.child("paused").setValue(true);
                mBtPlay.setVisibility(View.VISIBLE);
                mBtPause.setVisibility(View.GONE);
            }
        });
        mBtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentGame.setPaused(false);
                mCurrentGameRef.child("paused").setValue(false);
                if (mCurrentGame.getTimestampStarted() == null) {
                    mCurrentGameRef.child("timestamp_started").setValue(ServerValue.TIMESTAMP);
                }
                mBtPlay.setVisibility(View.GONE);
                mBtPause.setVisibility(View.VISIBLE);
                if (mCountDown == null) {
                    mCountDown = new CountDownTimer(1000000000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            if (!mCurrentGame.isPaused()) {
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
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void skipLevel() {
        levelNumber++;
        switchToLevel(levelNumber);
    }

    private void switchToLevel(int lvl) {
        try {
            currentLevel = nextLevel = mLevelsList.get(lvl - 1);
            if (lvl < lvlCount)
                nextLevel = mLevelsList.get(lvl);
            else
                nextLevel = currentLevel;
        } catch (IndexOutOfBoundsException e) {
            Snackbar mySnackBar = Snackbar.make(mViewGroup, getResources().getText(R.string.level_not_found), Snackbar.LENGTH_SHORT);
            mySnackBar.show();
        }
        updateInfo();
    }

    private void updateInfo() {
        mTxtNivel.setText(getResources().getText(R.string.level) + " " + currentLevel.getNumber().toString());
        mTxtBlinds.setText(getResources().getText(R.string.str_blinds) + " " + currentLevel.getSmall_blind() + "/" + currentLevel.getBig_blind());
        mTxtNextBlinds.setText(getResources().getText(R.string.next_level) + " " + nextLevel.getSmall_blind() + "/" + nextLevel.getBig_blind());
        mTextContador.setText(blindInterval + ":00");
        setMillisLeftWithBlindInterval();
    }

    private void setMillisLeftWithBlindInterval() {
        millisLeft = blindInterval * 60 * 1000;
    }

    private String formataTempo(long millis) {
        String output;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;
        output = String.format("%02d:%02d", minutes, seconds);
        return output;
    }

    private String getFirebaseUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}