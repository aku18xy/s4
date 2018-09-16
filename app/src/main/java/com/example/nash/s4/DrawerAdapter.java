package com.example.nash.s4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nash.s4.DataBank.ConnectionData;

import java.util.List;

public class DrawerAdapter extends ArrayAdapter<ConnectionData> {

    private Context context;
    private int resourceId;
    private List<ConnectionData> data;


    public DrawerAdapter(@NonNull Context context, int resource, @NonNull List<ConnectionData> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourceId=resource;
        this.data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        ConnectionData connectionData = data.get(position);

        if (convertView==null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
            viewHolder.tvItem = convertView.findViewById(R.id.tvDrawer_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvItem.setText(connectionData.getInfo());

        return convertView;
    }

    private class ViewHolder {
        TextView tvItem;
    }
}
