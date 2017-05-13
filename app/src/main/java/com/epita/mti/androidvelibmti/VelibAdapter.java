package com.epita.mti.androidvelibmti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epita.mti.androidvelibmti.PageViewer.ViewPagerActivity;

import java.util.ArrayList;

/**
 * Created by William on 12/05/2017.
 */

public class VelibAdapter extends RecyclerView.Adapter<VelibAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private ArrayList<String> filteredData;
    private static Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            mTextView = (TextView) v.findViewById(R.id.textTitle);
        }
    }

    public VelibAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
        filteredData = myDataset;
    }

    @Override
    public VelibAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(filteredData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail();
            }
        });
    }
    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public void showDetail()
    {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        context.startActivity(intent);
    }

    public void filter(String text) {
        ArrayList<String> tmpList = new ArrayList<>();
        if(text.isEmpty()){
            tmpList.addAll(mDataset);
        } else{
            text = text.toLowerCase();
            for(String item: mDataset){
                if(item.toLowerCase().contains(text)){
                    tmpList.add(item);
                }
            }
        }
        filteredData = tmpList;
        notifyDataSetChanged();
    }
}
