package com.example.androidstudio.kalkulaatoriii.parcelable;

import java.util.Comparator;

/**
 * Created by AndroidStudio on 05.04.2016.
 */
public class ExpressionParcelableComparatorByDate implements Comparator<ExpressionParcelable> {
    @Override
    public int compare(ExpressionParcelable o1, ExpressionParcelable o2) {
        return o2.created.compareTo(o1.created);
    }
}