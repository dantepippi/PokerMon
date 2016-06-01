package com.dstudios.pokermon;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StructureActivity extends AppCompatActivity {
    private RecyclerView mStructureRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Level, StructureViewHolder> mFirebaseAdapter;
    private ProgressBar mProgressBar;
    private Button mSendButton;
    public static final String STRUCTURE_CHILD = "structure";
    private EditText mEditSB;
    private EditText mEditBB;
    private EditText mEditAnte;

    public static class StructureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textSB;
        public TextView textBB;
        public TextView textAnte;
        public TextView textLevel;


        public StructureViewHolder(View v) {
            super(v);
            textSB = (TextView) itemView.findViewById(R.id.textSB);
            textBB = (TextView) itemView.findViewById(R.id.textBB);
            textAnte = (TextView) itemView.findViewById(R.id.textAnte);
            textLevel = (TextView) itemView.findViewById(R.id.text_level);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure);

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mStructureRecyclerView = (RecyclerView) findViewById(R.id.structureRecyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Level, StructureViewHolder>(
                Level.class,
                R.layout.item_structure,
                StructureViewHolder.class,
                mFirebaseDatabaseReference.child(STRUCTURE_CHILD).child(mFirebaseUser.getUid()).orderByChild("small_blind")) {

            @Override
            protected void populateViewHolder(StructureViewHolder viewHolder, Level level, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.textSB.setText(Integer.toString(level.getSmall_blind()));
                viewHolder.textBB.setText(Integer.toString(level.getBig_blind()));
                viewHolder.textAnte.setText(Integer.toString(level.getAnte() != null? level.getAnte() : null));
                viewHolder.textLevel.setText(" " + Integer.toString(position+1) + " ");
            }
        };

        mFirebaseAdapter.setHasStableIds(true);
        mStructureRecyclerView.setLayoutManager(mLinearLayoutManager);
        mStructureRecyclerView.setAdapter(mFirebaseAdapter);
        mEditSB = (EditText) findViewById(R.id.edit_sb);
        mEditBB = (EditText) findViewById(R.id.edit_bb);
        mEditAnte = (EditText) findViewById(R.id.edit_ante);
        mSendButton = (Button) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Level level = new Level();
                level.setSmall_blind(new Integer(mEditSB.getText().toString()));
                level.setBig_blind(new Integer(mEditBB.getText().toString()));
                level.setAnte(!"".equals(mEditAnte.getText().toString())  ? new Integer(mEditAnte.getText().toString()) : 0);
                mFirebaseDatabaseReference.child(STRUCTURE_CHILD).child(mFirebaseUser.getUid()).push().setValue(level);
            }
        });
        mStructureRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        mFirebaseAdapter.notifyItemRemoved(position);
                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.removeValue();
                    }

                })
        );
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int levelCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (levelCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mStructureRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

    }
}
