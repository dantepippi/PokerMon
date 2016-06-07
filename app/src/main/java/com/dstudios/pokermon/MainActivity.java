package com.dstudios.pokermon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private FirebaseUser mFirebaseUser;
    private User mUser;
    private static boolean mInitialized = false;
    private FirebaseRecyclerAdapter<Tournament, TournamentViewHolder> mFirebaseAdapter;
    private RecyclerView mTournamentRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    public static class TournamentViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;


        public TournamentViewHolder(View v) {
            super(v);
            textName = (TextView) itemView.findViewById(R.id.item_tournament_name);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        initializeFirebaseRefs();

        if (mFirebaseUser != null) {
            final String userId = mFirebaseUser.getUid();
            mFirebaseAdapter = new FirebaseRecyclerAdapter<Tournament, TournamentViewHolder>(
                    Tournament.class,
                    R.layout.item_tournament,
                    TournamentViewHolder.class,
                    Utils.mDatabaseRef.child(Utils.TOURNAMENTS).child(mFirebaseUser.getUid())) {

                @Override
                protected void populateViewHolder(TournamentViewHolder viewHolder, Tournament tournament, int position) {
                    viewHolder.textName.setText(tournament.getName());
                }
            };
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TournamentActivity.class));
            }
        });

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTournamentRecyclerView = (RecyclerView) findViewById(R.id.tournamentRecyclerView);


        mTournamentRecyclerView.setAdapter(mFirebaseAdapter);
        mTournamentRecyclerView.setLayoutManager(mLinearLayoutManager);
        DrawerLayout mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTournamentRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.removeValue();
                    }
                })
        );
    }

    private void initializeFirebaseRefs() {
        Utils.mDatabase = FirebaseDatabase.getInstance();
        if (!mInitialized)
            Utils.mDatabase.setPersistenceEnabled(true);
        Utils.mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = Utils.mFirebaseAuth.getCurrentUser();
        Utils.mDatabaseRef = Utils.mDatabase.getReference();
        mInitialized = true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            Utils.mFirebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_players) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_structure) {
            startActivity(new Intent(this, StructureActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
