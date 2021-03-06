package com.dstudios.pokermon;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by pcf-dante on 06/06/2016.
 */
public class Utils {
    public static final String TOURNAMENTS = "tournaments";
    public static final String TOURNAMENT_KEY = "com.dstudios.pokermon.TOURNAMENT_KEY";
    public static final String STRUCTURE = "structure";
    public static final String GAMES = "games";
    public static final String GAME_KEY = "com.dstudios.pokermon.GAME_KEY";
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseDatabase mDatabase;
    public static DatabaseReference mDatabaseRef;
}
