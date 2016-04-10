package com.example.androidstudio.kalkulaatoriii;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by AndroidStudio on 03.04.2016.
 */
public class ExpressionItem {
    public List<OperationItem> operationItemList;
    private String time;
    private String data;
    private List<OperationItem> operationItems;

    public ExpressionItem() {
        operationItemList = new ArrayList<OperationItem>();
        int a = new Random().nextInt(30);
        for (int i = 0; i < a; i++) {
            operationItemList.add(new OperationItem());
        }
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setOperationItems(List<OperationItem> operationItems) {
        this.operationItems = operationItems;
    }

    public List<OperationItem> getOperationItems() {
        return operationItems;
    }
}
