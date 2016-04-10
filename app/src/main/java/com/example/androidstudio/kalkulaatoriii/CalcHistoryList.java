package com.example.androidstudio.kalkulaatoriii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndroidStudio on 03.04.2016.
 */
public class CalcHistoryList extends ArrayAdapter<CalcHistoryItem> {
    private Context context;
    private List<CalcHistoryItem> calcHistoryItems;

    public CalcHistoryList(Context context, List<CalcHistoryItem> calcHistoryItems) {
        super(context, R.layout.calc_history_item, calcHistoryItems);
        this.context = context;
        this.calcHistoryItems = calcHistoryItems;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calc_history_item, null);
            ListView list = (ListView) convertView.findViewById(R.id.calc_history_item_listview_expression);
            ExpressionArrayAdapter adapter = new ExpressionArrayAdapter(context, calcHistoryItems.get(position).getExpressionItems(), list);
            list.setAdapter(adapter);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(calcHistoryItems.get(position).getDate());
        }
        return convertView;
    }
}
