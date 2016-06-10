package com.dstudios.pokermon;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StructureActivity extends AppCompatActivity {
    private RecyclerView mStructureRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<Level, StructureViewHolder> mFirebaseAdapter;
    private ProgressBar mProgressBar;
    private Button mSendButton;
    private EditText mEditSB;
    private EditText mEditBB;
    private EditText mEditAnte;
    private SharedPreferences mSharedPreference;
    private MenuItem mMenuItemDelete;
    private int mCountSelected = 0;

    public static class StructureViewHolder extends RecyclerView.ViewHolder {
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_structure, menu);
        mMenuItemDelete = menu.findItem(R.id.action_delete);
        return true;
    }

    ///////////////////////////////////////////////////////


    private ActionMode mActionMode;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_structure, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(20);

    // …

    public void toggleSelection(int pos) {
        if (mSelectedItems.get(pos, false)) {
            mSelectedItems.delete(pos);
        }
        else {
            mSelectedItems.put(pos, true);
        }
    }

    public void clearSelections() {
        mSelectedItems.clear();
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }

    ////////////////////////////////////////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                List<Integer> selectedItems = getSelectedItems();
                for (int i =0; i < selectedItems.size(); i++) {
                    Integer position = selectedItems.get(i);
                    mFirebaseAdapter.getRef(position).removeValue();
                    mFirebaseAdapter.notifyItemRemoved(position);
                    mMenuItemDelete.setVisible(false);
                }
                clearSelections();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure);

        Toolbar toolbar = (Toolbar) findViewById(R.id.struc_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mStructureRecyclerView = (RecyclerView) findViewById(R.id.structureRecyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Utils.mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        setPreferences();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Level, StructureViewHolder>(
                Level.class,
                R.layout.item_structure,
                StructureViewHolder.class,
                Utils.mDatabaseRef.child(Utils.STRUCTURE).child(mFirebaseUser.getUid()).child("Fast").orderByChild("sum")) {


            @Override
            protected void populateViewHolder(StructureViewHolder viewHolder, Level level, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.textSB.setText(Integer.toString(level.getSmall_blind()));
                viewHolder.textBB.setText(Integer.toString(level.getBig_blind()));
                viewHolder.textAnte.setText(Integer.toString(level.getAnte() != null ? level.getAnte() : null));
                viewHolder.textLevel.setText(" " + Integer.toString(position + 1) + " ");
            }
        };
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
                if ("".equals(mEditSB.getText().toString()) || "".equals(mEditBB.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please fill the blinds", Toast.LENGTH_LONG).show();
                    return;
                }
                level.setSmall_blind(new Integer(mEditSB.getText().toString()));
                level.setBig_blind(new Integer(mEditBB.getText().toString()));
                level.setAnte(!"".equals(mEditAnte.getText().toString()) ? new Integer(mEditAnte.getText().toString()) : 0);
                level.setSum(new Long(level.getSmall_blind().toString() + level.getBig_blind() + level.getAnte()));
                Utils.mDatabaseRef.child(Utils.STRUCTURE).child(mFirebaseUser.getUid()).child("Fast").push().setValue(level);
            }
        });
        mStructureRecyclerView.setHasFixedSize(true);
        mStructureRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        //ref.removeValue();
                        boolean isSelected = !view.isSelected();
                        toggleSelection(position);
                        if (isSelected) {

                            mCountSelected++;
                        } else {
                            mCountSelected--;
                        }
                        view.setSelected(isSelected);
                        view.setBackgroundColor(isSelected ? Color.RED : Color.LTGRAY);
                        mMenuItemDelete.setVisible(mCountSelected > 0);
                    }

                })
        );
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
//                super.onItemRangeInserted(positionStart, itemCount);
//                int levelCount = mFirebaseAdapter.getItemCount();
//                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
//                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
//                // to the bottom of the list to show the newly added message.
//                if (lastVisiblePosition == -1 ||
//                        (positionStart >= (levelCount - 1) && lastVisiblePosition == (positionStart - 1))) {
//                    mStructureRecyclerView.scrollToPosition(positionStart);
//                }
                mStructureRecyclerView.scrollToPosition(positionStart);

            }
        });

    }

    private void setPreferences() {
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mPrefsEditor = mSharedPreference.edit();
        mPrefsEditor.putInt("BLIND_INTERVAL", 15);
        mPrefsEditor.commit();
    }

}
