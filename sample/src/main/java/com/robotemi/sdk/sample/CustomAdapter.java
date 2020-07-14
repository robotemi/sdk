package com.robotemi.sdk.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    private List<String> data;

    CustomAdapter(Context context, int textViewResourceId, List<String> data) {
        super(context, textViewResourceId, data);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.location_row, null);
        }

        String loc = data.get(position);
        if (loc != null) {
            TextView tvName = convertView.findViewById(R.id.name);
            if (tvName != null) {
                tvName.setText(loc);
            }
        }
        return convertView;
    }

    public void setData(List<String> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}