package org.parksay.core.entity;

public enum SurveyItemType {
    SHORT_TEXT("짧은 주관식"),
    LONG_TEXT("긴 주관식"),
    SINGLE_CHOICE("단일 객관식"),
    MULTIPLE_CHOICE("다중 객관식");

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
