package com.dstudios.pokermon;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by dante on 29/05/2016.
 */

public class TournamentRecyclerAdapter extends FirebaseRecyclerAdapter<Tournament, TournamentRecyclerAdapter.ViewHolder> {
    public TournamentRecyclerAdapter(Class modelClass, int modelLayout, Class viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Tournament model, int position) {

    }

    /**
     * Inner Class for a recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            //mTextView = (TextView) itemView.findViewById();

        }
    }

}
