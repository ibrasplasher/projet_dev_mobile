package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RondAdapter extends BaseAdapter {
    private Context context;
    private List<RondSpinner> rondSpinnerList;

    public RondAdapter(Context context, List<RondSpinner> rondSpinnerList) {
        this.context = context;
        this.rondSpinnerList = rondSpinnerList;
    }

    @Override
    public int getCount() {
        return rondSpinnerList != null ? rondSpinnerList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_petit_rond, viewGroup,false);
        TextView txtName = rootView.findViewById(R.id.txtName);
        ImageView imageSpinner = rootView.findViewById(R.id.imageSpinner);
        RondSpinner rondSpinner = rondSpinnerList.get(i);

        txtName.setText(rondSpinnerList.get(i).getName());
        imageSpinner.setImageResource(rondSpinnerList.get(i).getImage());
        return rootView;
    }
}
