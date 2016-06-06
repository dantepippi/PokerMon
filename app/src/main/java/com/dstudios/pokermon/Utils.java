package com.dstudios.pokermon;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by pcf-dante on 06/06/2016.
 */
public class Utils {
    public static final String TOURNAMENTS = "tournaments";
    public final static String TOURNAMENT_KEY = "com.dstudios.pokermon.TOURNAMENT_KEY";
    public static final String STRUCTURE = "structure";
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseDatabase mDatabase;
    public static DatabaseReference mDatabaseRef;
}
