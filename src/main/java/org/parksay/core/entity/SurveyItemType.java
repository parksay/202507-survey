package org.parksay.core.entity;

public enum SurveyItemType {
    SHORT_TEXT("SHORT"),
    LONG_TEXT("LONG"),
    SINGLE_CHOICE("SINGLE"),
    MULTIPLE_CHOICE("MULTI");

    private final String label;

    SurveyItemType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
