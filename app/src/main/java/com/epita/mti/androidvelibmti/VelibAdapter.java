package com.epita.mti.androidvelibmti;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by William on 12/05/2017.
 */

public class VelibAdapter extends RecyclerView.Adapter<VelibAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView2);
        }
    }

    public VelibAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }
    @Override
    public VelibAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));

    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
