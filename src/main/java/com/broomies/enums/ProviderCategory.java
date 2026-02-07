package com.broomies.enums;

public enum ProviderCategory {
    COOKING("Cooking", "fas fa-utensils", "text-warning", "bg-warning"),
    CLEANING("Cleaning", "fas fa-broom", "text-info", "bg-info"),
    NANNY("Nanny", "fas fa-baby", "text-danger", "bg-danger"),
    MAID("Maid", "fas fa-spray-can", "text-primary", "bg-primary"),
    ALL_ROUNDER("All Rounder", "fas fa-tools", "text-secondary", "bg-secondary"),
    NURSE("Nurse", "fas fa-user-nurse", "text-danger", "bg-danger"),
    ELDERLY_CARE_TAKER("Elderly Care", "fas fa-hands-helping", "text-success", "bg-success");

    private final String displayName;
    private final String iconClass;
    private final String textClass;
    private final String bgClass;

    ProviderCategory(String displayName, String iconClass, String textClass, String bgClass) {
        this.displayName = displayName;
        this.iconClass = iconClass;
        this.textClass = textClass;
        this.bgClass = bgClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIconClass() {
        return iconClass;
    }

    public String getTextClass() {
        return textClass;
    }

    public String getBgClass() {
        return bgClass;
    }
}
