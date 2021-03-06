package com.april.fourth.dto;

/**
 * Created by daipengfei
 * on 2017/3/30.
 */
public class RuleContext {
    private String name;

    private int num;

    private boolean isPassed;

    private boolean filtered;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    @Override
    public String toString() {
        return "RuleContext{" +
                "name='" + name + '\'' +
                ", num=" + num +
                ", isPassed=" + isPassed +
                ", filtered=" + filtered +
                '}';
    }
}
