package com.dstudios.pokermon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.FirebaseUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Map;

import static com.dstudios.pokermon.R.string.level;
import static com.dstudios.pokermon.StructureActivity.STRUCTURE_CHILD;

public class TournamentActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        Button btAdd = (Button) findViewById(R.id.addButton);
        final EditText buyin = (EditText) findViewById(R.id.buyin);
        final EditText rebuy = (EditText) findViewById(R.id.rebuy);
        final EditText addOn = (EditText) findViewById(R.id.addon);
        final EditText lastRebuyLevel = (EditText) findViewById(R.id.last_rebuy_level);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tournament tournament = new Tournament();
                String firebaseUserUid = getFirebaseUserUid();
                Map<String, String> timestamp = ServerValue.TIMESTAMP;
                tournament.setTimestamp_created(timestamp);
                tournament.setOwner_uid(firebaseUserUid);
                tournament.setLast_rebuy_level(new Integer(lastRebuyLevel.getText().toString()));
                tournament.setBuy_in(buyin.getText().toString());
                tournament.setAdd_on(addOn.getText().toString());
                tournament.setRebuy(rebuy.getText().toString());
                tournament.setLast_rebuy_level(new Integer(lastRebuyLevel.getText().toString()));
                tournament.setBlind_interval(mSharedPreferences.getInt("BLIND_INTERVAL", 10));

                DatabaseReference tournChild = mDatabaseRef.child("tournaments").child(firebaseUserUid);
                String key = tournChild.push().getKey();
                Map<String, Object> stringObjectMap = tournament.toMap();
                tournChild.child(key).updateChildren(stringObjectMap);
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
                finish();
                return;
            }
        });


    }

    private String getFirebaseUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
