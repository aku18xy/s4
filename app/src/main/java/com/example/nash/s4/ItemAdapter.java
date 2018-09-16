package com.example.nash.s4;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nash.s4.DataBank.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> items;
    private Context context;

    public ItemAdapter() {
    }

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    public ItemAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        final Item item = items.get(position);
        String label = item.getLabel();
        List<String> value = item.getValue();
        int type = item.getValueType();
        switch (type) {
            case 0:
                // display
                holder.et.setEnabled(false);
                holder.et.setKeyListener(null);
                holder.et.setFocusable(false);
                holder.et.setClickable(false);
                holder.tvLabel.setText(label);
                if (value != null)
                    holder.et.setText(value.get(0));
                break;
            case 1:
                // alpha-numeric
                holder.et.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.et.setFocusable(true);
                holder.et.setFocusableInTouchMode(true);
                holder.tvLabel.setText(label);
                if (value != null)
                    holder.et.setText(value.get(0));
                break;
            case 2:
                // numerical
                holder.et.setInputType(InputType.TYPE_CLASS_NUMBER);
                holder.et.setFocusable(true);
                holder.et.setFocusableInTouchMode(true);
                holder.tvLabel.setText(label);
                if (value != null)
                    holder.et.setText(value.get(0));
                break;
            case 3:

                break;
            case 4:
                // yes or no
                holder.et.setVisibility(View.GONE);
                holder.tvLabel.setText(label);
                holder.spinner.setVisibility(View.VISIBLE);

                if (value!=null) {

                    value.add(0,"<value>");

                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, value){
                        @Override
                        public boolean isEnabled(int position) {
                            return position != 0;
                        }

                        @Override
                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) v;
                            if (position==0) {
                                tv.setTextColor(Color.GRAY);
                            }

                            return v;

                        }

                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            TextView tv = (TextView) v;
                            if (position==0)
                                tv.setTextColor(Color.GRAY);

                            return v;
                        }

                    };

                    // android.R.layout.simple_spinner_item

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

                    // attaching data adapter to spinner
                    holder.spinner.setAdapter(dataAdapter);

                    holder.spinner.setPrompt(label);

                    holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String txt = holder.spinner.getSelectedItem().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }


                break;
            case 5:
                holder.layoutLabel.setVisibility(View.GONE);
                holder.layoutHeader.setVisibility(View.VISIBLE);
                holder.tvHeader.setText(label);
                holder.layoutItem.setCardElevation(0.0f);
                break;

        }



        // ref : https://stackoverflow.com/questions/10627137/how-can-i-know-when-an-edittext-loses-focus
        if (type==1 || type==2) {
            holder.et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String txt = holder.et.getText().toString();
                        List<String> lTxt = new ArrayList<>();
                        lTxt.add(txt);
                        Log.d("tagLine", txt);
                        if (!txt.equals("")) {
                            item.setValue(lTxt);
                        }
                    }
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return items!=null ? items.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tvLabel, tvHeader;
        public EditText et;
        public ViewGroup layoutLabel, layoutHeader;
        public CardView layoutItem;
        public Spinner spinner;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvLabel = itemView.findViewById(R.id.tvLabel_SI);
            et = itemView.findViewById(R.id.etValue_SI);
            tvHeader = itemView.findViewById(R.id.tvHeader_SI);
            layoutHeader = itemView.findViewById(R.id.layoutHeader);
            layoutLabel = itemView.findViewById(R.id.layoutLabel);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            spinner = itemView.findViewById(R.id.spinnerValue_SI);

        }
    }

    public void updateItemAdapter(List<Item> items) {
        this.items = items;
        Log.d("s3", "updateItemAdapter");
    }
}
