package com.dstudios.pokermon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;
// TODO checar utilização dos metodos de login na MainActiviy
public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usr = firebaseAuth.getCurrentUser();

                if (usr != null) {
                    Log.d("AUTH", "User signed in correctly: " + usr);
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    //signed out
                    Log.d("AUTH", "User is not signed in");
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER
                            ).build(), RC_SIGN_IN);
                }

            }
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.d("AUTH", "RESULT_OK");
                // user is signed in!
                FirebaseUser usr = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase.getInstance().getReference().child("users").child(usr.getUid()).setValue(usr);
                Log.d("LOGIN", usr.getDisplayName());
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Log.d("AUTH", "AUTH RESULT FAILED");
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (auth != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }
}
