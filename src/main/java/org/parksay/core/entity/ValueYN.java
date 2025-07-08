package org.parksay.core.entity;

public enum ValueYN {

    Y("Y"),
    N("N");

    private final String val;

    ValueYN(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val;
    }
}
