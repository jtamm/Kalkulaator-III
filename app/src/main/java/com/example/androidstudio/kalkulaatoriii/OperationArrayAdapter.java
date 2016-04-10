package com.example.androidstudio.kalkulaatoriii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by AndroidStudio on 04.04.2016.
 */
public class OperationArrayAdapter extends ArrayAdapter<OperationItem> {

    private Context context;
    private List<OperationItem> operationItems;

    public OperationArrayAdapter(Context context, List<OperationItem> operationItems){
        super(context, R.layout.operation_item, operationItems);
        this.context = context;
        this.operationItems = operationItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate( R.layout.operation_item, null );
            AutoResizeTextView autoResizeTextView = (AutoResizeTextView) convertView.findViewById(R.id.text_data);
            autoResizeTextView.setText(String.format("%s.) %s", position + 1, operationItems.get(position)));
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
