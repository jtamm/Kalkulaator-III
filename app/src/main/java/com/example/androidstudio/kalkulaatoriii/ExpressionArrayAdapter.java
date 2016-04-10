package com.example.androidstudio.kalkulaatoriii;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AndroidStudio on 03.04.2016.
 */
public class ExpressionArrayAdapter extends ArrayAdapter<ExpressionItem> {

    private Context context;
    private List<ExpressionItem> expressionItems;
    private RelativeLayout[] operationRelativeLayouts;
    private ListView listView;

    public ExpressionArrayAdapter(Context context, List<ExpressionItem> expressionItems, ListView listView){
        super(context, R.layout.item_expression, expressionItems);
        this.context = context;
        this.expressionItems = expressionItems;
        operationRelativeLayouts = new RelativeLayout[expressionItems.size()];
        this.listView = listView;
        listView.setAdapter(this);
        Helper.getListViewSize(listView);
    }
    private int beforePosition = -1;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate( R.layout.item_expression, null );
            OperationArrayAdapter adapter = new OperationArrayAdapter(context,expressionItems.get(position).getOperationItems());
            ListView list = (ListView)convertView.findViewById(R.id.expression_item_listView_operation);
            list.setAdapter(adapter);
            if(beforePosition != position){
                Helper.getListViewSize(list);
                beforePosition = position;
            }
            final int pos = position;
            operationRelativeLayouts[position] = (RelativeLayout) convertView.findViewById(R.id.item_expression_expand);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggle(pos);
                }
            });
            TextView time = (TextView) convertView.findViewById(R.id.item_expression_time);
            time.setText(expressionItems.get(pos).getTime());
            AutoResizeTextView data = (AutoResizeTextView) convertView.findViewById(R.id.item_expression_data);
            data.setText(expressionItems.get(pos).getData());
        }
        return convertView;
    }

    private void toggle(int position){
        if(operationRelativeLayouts[position].getVisibility() == View.GONE){
            expand(position);
        }
        else{
            collapse(position);
        }
    }

    private void expand(int position) {
        //set Visible
        operationRelativeLayouts[position].setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        operationRelativeLayouts[position].measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, operationRelativeLayouts[position].getMeasuredHeight(), position);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Helper.getListViewSize(listView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private void collapse(int position) {
        int finalHeight = operationRelativeLayouts[position].getHeight();
        final int pos = position;
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, position);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                operationRelativeLayouts[pos].setVisibility(View.GONE);
                //Helper.getListViewSize(listView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, int position) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        final int pos = position;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = operationRelativeLayouts[pos].getLayoutParams();
                layoutParams.height = value;
                operationRelativeLayouts[pos].setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}

