package com.example.androidstudio.kalkulaatoriii;

/**
 * Created by AndroidStudio on 04.04.2016.
 */
public class OperationItem {
    private String num1;
    private String num2;
    private String op;
    private String result;

    public OperationItem() {
    }

    ;

    public OperationItem(String num1, String num2, String op, String result) {
        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s = %s", num1, op, num2, result);
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getNum2() {
        return num2;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
