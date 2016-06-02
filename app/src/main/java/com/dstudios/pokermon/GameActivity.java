package com.dstudios.pokermon;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    TextView mTextContador;
    Button mBtPlayPause;
    Boolean statusPaused;
    private DatabaseReference mFirebaseDatabaseReference;
    MyList<Level> mLevelsList;
    int lvl_count = 0;
    int millis_left = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        statusPaused = true;

        mLevelsList = new MyList<Level>();
        mBtPlayPause = (Button) findViewById(R.id.start);
        mTextContador = (TextView) findViewById(R.id.txtContador);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child("structure").child(getFirebaseUserUid()).orderByChild("small_blind").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot levelSnapShot : dataSnapshot.getChildren()) {
                    mLevelsList.add(levelSnapShot.getValue(Level.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println(mLevelsList.get(0).getSmall_blind());

        mBtPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusPaused = !statusPaused;
                if (statusPaused)
                    mBtPlayPause.setText("PLAY");
                else
                    mBtPlayPause.setText("PAUSE");
            }
        });
    }
    private String getFirebaseUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}