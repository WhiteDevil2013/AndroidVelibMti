package com.epita.mti.androidvelibmti.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epita.mti.androidvelibmti.DBO.Station;
import com.epita.mti.androidvelibmti.PageViewer.ViewPagerActivity;
import com.epita.mti.androidvelibmti.R;
import com.epita.mti.androidvelibmti.Wrapper.StationWrapper;

import java.util.ArrayList;

public class VelibAdapter extends RecyclerView.Adapter<VelibAdapter.ViewHolder> {
    private ArrayList<Station> mDataset;
    private ArrayList<Station> filteredData;
    private static Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
        private ViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            mTextView = (TextView) v.findViewById(R.id.textTitle);
            mImageView = (ImageView) v.findViewById(R.id.iconStatus);
        }
    }

    public VelibAdapter(ArrayList<Station> myDataset) {
        mDataset = myDataset;
        filteredData = myDataset;
    }

    @Override
    public VelibAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(filteredData.get(holder.getAdapterPosition()).getName());
        if (filteredData.get(holder.getAdapterPosition()).getStatus().equals("CLOSED")) {
            holder.mImageView.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));
            holder.mImageView.setImageResource(R.drawable.ic_close_black_24dp);
        } else {
            holder.mImageView.setColorFilter(ContextCompat.getColor(context, R.color.colorOpened));
            holder.mImageView.setImageResource(R.drawable.ic_done_black_24dp);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail(holder.getAdapterPosition());
            }
        });
    }
    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public void showDetail(int position)
    {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        intent.putExtra("POSITION", position);
        intent.putExtra("DATA", new StationWrapper(filteredData));
        context.startActivity(intent);
    }

    public void filter(String text) {
        ArrayList<Station> tmpList = new ArrayList<>();
        if(text.isEmpty()){
            tmpList.addAll(mDataset);
        } else{
            text = text.toLowerCase();
            for(Station item: mDataset){
                if(item.getName().toLowerCase().contains(text)){
                    tmpList.add(item);
                }
            }
        }
        filteredData = tmpList;
        notifyDataSetChanged();
    }
}
