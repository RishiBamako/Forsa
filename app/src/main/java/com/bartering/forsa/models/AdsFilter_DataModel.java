package com.bartering.forsa.models;

public class AdsFilter_DataModel {
    private String filterName;
    private boolean isSelected;

    public AdsFilter_DataModel(String filterName, boolean isSelected) {
        this.filterName = filterName;
        this.isSelected = isSelected;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
