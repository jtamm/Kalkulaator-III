package com.example.androidstudio.kalkulaatoriii;

import java.util.List;

/**
 * Created by AndroidStudio on 10.04.2016.
 */
public class DayStatistic {
    private String date;
    private List<Operand> operands;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setOperands(List<Operand> operands) {
        this.operands = operands;
    }

    public List<Operand> getOperands() {
        return operands;
    }

    public int getCount(String op) {
        if (operands == null) return 0;
        for (Operand operand : operands) {
            if (operand.getOp().equals(op)) {
                return operand.getCount();
            }
        }
        return 0;
    }

    public DayStatistic() {
    }

    public DayStatistic(String date) {
        this.date = date;
    }

    public static class Operand {
        private String op;
        private int count;

        public Operand(String op, int count) {
            this.op = op;
            this.count = count;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public String getOp() {
            return op;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }
    }
}
