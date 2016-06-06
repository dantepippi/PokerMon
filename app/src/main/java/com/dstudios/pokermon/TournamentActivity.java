package com.dstudios.pokermon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TournamentActivity extends AppCompatActivity {

    SharedPreferences mSharedPreferences;
    private Spinner mClassSpinner;

    @Override
    public void onResume() {
        super.onResume();

        mClassSpinner.setSelection(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mClassSpinner = (Spinner) findViewById(R.id.spinner_structure);


        DatabaseReference structuresRef = Utils.mDatabaseRef.child(Utils.STRUCTURE).child(getFirebaseUserUid());
        structuresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayAdapter<String> adapter;
                List<String> listStructure;
                listStructure = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    listStructure.add(data.getKey());
                }

                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listStructure);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner mSpinner = (Spinner) findViewById(R.id.spinner_structure);
                mSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                tournament.setOwner_uid(firebaseUserUid);
                tournament.setLast_rebuy_level(new Integer(lastRebuyLevel.getText().toString()));
                tournament.setBuy_in(buyin.getText().toString());
                tournament.setAdd_on(addOn.getText().toString());
                tournament.setRebuy(rebuy.getText().toString());
                tournament.setLast_rebuy_level(new Integer(lastRebuyLevel.getText().toString()));
                tournament.setBlind_interval(mSharedPreferences.getInt("BLIND_INTERVAL", 10));
                tournament.setStructure(mClassSpinner.getSelectedItem().toString());
                DatabaseReference tournChild = Utils.mDatabaseRef.child(Utils.TOURNAMENTS).child(firebaseUserUid);
                String key = tournChild.push().getKey();
                Map<String, Object> stringObjectMap = tournament.toMap();
                tournChild.child(key).updateChildren(stringObjectMap);
                startActivity(new Intent(getApplicationContext(), GameActivity.class).putExtra(Utils.TOURNAMENT_KEY, key));
                finish();
            }
        });


    }

    private String getFirebaseUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
