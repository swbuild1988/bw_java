package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private double value;

    private String stringValue;

    private int row;

    private int column;


    public Item(String stringValue, int row, int column) {
        this.stringValue = stringValue;
        this.row = row;
        this.column = column;
    }

    public Item(double value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
