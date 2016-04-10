package com.example.androidstudio.kalkulaatoriii;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndroidStudio on 03.04.2016.
 */
public class CalcHistoryItem {
    private String date;
    public List<ExpressionItem> expressionItems;

    public CalcHistoryItem(int i){
        expressionItems = new ArrayList<ExpressionItem>();
        for (int a = 0; a < i; a++){
            expressionItems.add(new ExpressionItem());
        }

    }

    public CalcHistoryItem(String date){
        expressionItems = new ArrayList<ExpressionItem>();
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setExpressionItems(List<ExpressionItem> expressionItems) {
        this.expressionItems = expressionItems;
    }

    public List<ExpressionItem> getExpressionItems() {
        return expressionItems;
    }
}

