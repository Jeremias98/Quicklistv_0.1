package com.quicklistv_01.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.quicklistv_01.R;

import java.util.ArrayList;

/**
 * Created by Aaron on 20/12/2016.
 */

public class SemanalAdapter extends BaseAdapter{
    ArrayList<String>nombres = new ArrayList<>();

    public SemanalAdapter(Context context, ArrayList<String>nombres) {
        this.context = context;
        this.nombres = nombres;
    }

    private Context context;

    @Override
    public int getCount() {
        return nombres.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        gridView = inflater.inflate(R.layout.item, null);
        TextView textView = (TextView) gridView
                .findViewById(R.id.grid_item_label);
        textView.setText(nombres.get(i));
        return textView;

    }
}
